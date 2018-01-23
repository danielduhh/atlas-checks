package org.openstreetmap.atlas.checks.validation;

import java.util.*;
import java.util.stream.Collectors;

import org.openstreetmap.atlas.checks.base.BaseCheck;
import org.openstreetmap.atlas.checks.flag.CheckFlag;
import org.openstreetmap.atlas.geography.atlas.items.*;
import org.openstreetmap.atlas.tags.HighwayTag;
import org.openstreetmap.atlas.tags.JunctionTag;
import org.openstreetmap.atlas.tags.annotations.validation.Validators;
import org.openstreetmap.atlas.utilities.configuration.Configuration;

/**
 * This checks flags multi-featured roundabouts. According to OSM Wiki, each round
 * about should be exactly one feature (OSM Way). We can create the WrongWayRoundaboutCheck
 * based on this principle.
 *
 * @author danielbaah
 */
public class MultiFeatureRoundaboutCheck extends BaseCheck {

    // TODO You can use serialver to regenerate the serial UID.
    private static final long serialVersionUID = 1L;
    private static final List<String> FALLBACK_INSTRUCTIONS = Arrays
            .asList("This is a multi-feature roundabout. Merge roundabout edges to create one feature (OSM Way).");

    @Override
    protected List<String> getFallbackInstructions() {
        return FALLBACK_INSTRUCTIONS;
    }

    /**
     * The default constructor that must be supplied. The Atlas Checks framework will generate the
     * checks with this constructor, supplying a configuration that can be used to adjust any
     * parameters that the check uses during operation.
     *
     * @param configuration the JSON configuration for this check
     */
    public MultiFeatureRoundaboutCheck(final Configuration configuration) {
        super(configuration);
    }

    /**
     * This function will validate if the supplied atlas object is a roundabout or not.
     *
     * @param object the atlas object supplied by the Atlas-Checks framework for evaluation
     * @return {@code true} if this object should be checked
     */
    @Override
    public boolean validCheckForObject(final AtlasObject object) {

        // object must be an ed
        return object instanceof Edge
                // check if already marked flagged
                && !this.isFlagged(object.getIdentifier())
                // check junction=roundabout tag
                && JunctionTag.isRoundabout(object);
    }

    /**
     * This is the actual function that will check to see whether the object needs to be flagged.
     *
     * @param object the atlas object supplied by the Atlas-Checks framework for evaluation
     * @return an optional {@link CheckFlag} object that
     */
    @Override
    protected Optional<CheckFlag> flag(final AtlasObject object)
    {
        final Edge edge = (Edge) object;
        // array list of osm unique identifiers
        final Set<Edge> roundaboutEdges = new HashSet<>();
        final List<Long> osmIds = new ArrayList<>();

        // loop through connected edges with junction=roundabout tags
        stitchRoundaboutEdges(edge, roundaboutEdges, osmIds);

        // Rule: If edges with junction=roundabout tag have the same id, do not flag.
        // This indicates a one feature roundabout.
        if (roundaboutEdges.size() <= 1 || isSingleFeature(Long.toString(edge.getOsmIdentifier()), osmIds)) {
            return Optional.empty();
        } else {
            // mark as flagged
            this.markAsFlagged(object.getIdentifier());
            // create flag w/ instructions
            //TODO combine roundabout edges and pass in as Set
            return Optional.of(
                    this.createFlag(roundaboutEdges, this.getLocalizedInstruction(0)));
            }

        }

    /**
     * Recursively loop through connected edges & append osmIds to
     * our list of edges with junction=roundabout
     *
     * @param edge
     *      current ({@link Edge}) {@link AtlasObject}
     * @param roundaboutEdges
     *      list of osmIds in single roundabout
     */
    //TODO find a better name for this
    private void stitchRoundaboutEdges(Edge edge, Set<Edge> roundaboutEdges, List<Long> osmIds) {
        Iterator<Edge> r = edge.connectedEdges().iterator();

        // iterate through connectedEdges
        while (r.hasNext())
        {
            final Edge connectedEdge = r.next();
            final Long uniqueId = connectedEdge.getIdentifier();

            // grab all edges with roundabout tag
            if (JunctionTag.isRoundabout(connectedEdge))
            {
                // make sure our list doesn't already contain the id
                if (!osmIds.contains(uniqueId)
                        // or the AtlasObject id
                        && uniqueId != edge.getIdentifier()
                        // this flag removes duplicate challenges
                        && !this.isFlagged(uniqueId))
                {
                    // mark as flagged
                    this.markAsFlagged(uniqueId);
                    // add to list of roundabout unique ids
                    roundaboutEdges.add(connectedEdge);
                    osmIds.add(uniqueId);
                    // check for more edges
                    stitchRoundaboutEdges(connectedEdge, roundaboutEdges, osmIds);
                }
            }
        }

    }

    /***
     * Analyze List of roundabout ids. If all List values contain the full OsmIdentifier value,
     * the roundabout is one Feature (OSM way).
     *
     * For example, OSM Way id: 1270065 can be multiple Edges as [127006500001, 127006500002, 127006500003, 127006500004]
     * Our helper function stitchRoundaboutEdges will create a Roundabout, not knowing this is a single Way.
     * This function removes these roundabouts from our List of roundaboutEdgeIds.
     *
     * @param osmId
     *          the {@link AtlasObject}'s osmId
     * @param roundaboutEdgeIds
     *          the List of unique roundabout Ids
     * @return {@code true} if roundabout is a single feature, otherwise {@code false}
     *
     */
    private boolean isSingleFeature(String osmId, List<Long> roundaboutEdgeIds){
        List matches = roundaboutEdgeIds
                .stream()
                // returns true is roundabout Id contains full osmId value
                .filter(id -> Long.toString(id).contains(osmId))
                .collect(Collectors.toList());

        // if each value passes our test, this roundabout is a single feature
        return matches.size() == roundaboutEdgeIds.size();
    }
}

package org.openstreetmap.atlas.checks.validation;

import java.util.*;

import org.openstreetmap.atlas.checks.base.BaseCheck;
import org.openstreetmap.atlas.checks.flag.CheckFlag;
import org.openstreetmap.atlas.geography.atlas.items.*;
import org.openstreetmap.atlas.tags.HighwayTag;
import org.openstreetmap.atlas.tags.JunctionTag;
import org.openstreetmap.atlas.tags.annotations.validation.Validators;
import org.openstreetmap.atlas.utilities.configuration.Configuration;

/**
 * This checks flags multi-featured roundabouts. According to OSM Wiki, each round about should be exactly one feature (OSM Way).
 * We can create the WrongWayRoundaboutCheck based on this exact principle.
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
     * This function will validate if the supplied atlas object is valid for the check.
     *
     * @param object the atlas object supplied by the Atlas-Checks framework for evaluation
     * @return {@code true} if this object should be checked
     */
    @Override
    public boolean validCheckForObject(final AtlasObject object) {

        final Optional<HighwayTag> highwayTag = Validators.from(HighwayTag.class, object);

        // by default we will assume all objects as valid
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
        final Set<AtlasObject> roundaboutEdges = new HashSet<>();

        // loop through connected edges with junction=roundabout tags
        stitchRoundaboutEdges(edge, roundaboutEdges);

        // Rule: If edges with junction=roundabout tag have the same id, do not flag.
        // This indicates a one feature roundabout.
        if (roundaboutEdges.size() == 0) {
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
     * Recursively loop through connected edges & append osmIds to our list of edges with junction=roundabout
     * @param edge
     *      current ({@link Edge}) {@link AtlasObject}
     * @param roundaboutEdges
     *      list of osmIds in single roundabout
     */
    //TODO find a better name for this
    private void stitchRoundaboutEdges(Edge edge, Set<AtlasObject> roundaboutEdges) {
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
                if (!roundaboutEdges.contains(connectedEdge)
                        // or the AtlasObject id
                        && uniqueId != edge.getIdentifier()
                        // this flag removes duplicate challenges
                        && !this.isFlagged(connectedEdge.getIdentifier()))
                {
                    // mark as flagged
                    this.markAsFlagged(connectedEdge.getIdentifier());
                    // add to list of roundabout unique ids
                    roundaboutEdges.add(connectedEdge);
                    // check for more edges
                    stitchRoundaboutEdges(connectedEdge, roundaboutEdges);
                }
            }
        }

    }
}

# MutifeatureRoundaboutCheck

#### Description

This check identifies multi-featured roundabouts. As indicated by the OSM Wiki, [roundabouts](https://wiki.openstreetmap.org/wiki/Tag:junction%3Droundabout) should be created as a single feature, or “closed way.” Our goal is to flag each roundabout created with multiple Ways so an OSM Editor can easily stitch them together.

#### Live Example
Take a look at the following Way, [id:331929481](https://www.openstreetmap.org/way/331929481). It's been incorrectly drawn. Using the iD Editor, we can select each feature with the Shift key, right click, and select merge them stitch them together.

#### Code Review

In [Atlas](https://github.com/osmlab/atlas), OSM elements are represented as Edges, Points, Lines, Nodes & Relations; in our case, we’re working with [Edges](https://github.com/osmlab/atlas/blob/dev/src/main/java/org/openstreetmap/atlas/geography/atlas/items/Edge.java). In OpenStreetMap, roundabouts are [Ways](https://wiki.openstreetmap.org/wiki/Way) classified with the `junction=roundabout` tag. We’ll use this information to filter our potential flag candidates.

Our first goal is to validate the incoming Atlas Object. We know two things about roundabouts:
* Must be a valid Edge
* Must have `junction=roundabout` tag

```java
    @Override
    public boolean validCheckForObject(final AtlasObject object) {

        // object must be an Edge
        return object instanceof Edge
                // check if already marked flagged
                && !this.isFlagged(object.getIdentifier())
                // check junction=roundabout tag
                && JunctionTag.isRoundabout(object);
    }
```

Now that we’ve ensured all features passed into our flag function are Edges tagged as roundabouts, we must programmatically classify and store each individual roundabout by piecing its Edges together into a single Collection. This will prevent us from creating a MapRoulette Challenge for each individual Edge.

Using the [`connectedEdges()`](https://github.com/osmlab/atlas/blob/dev/src/main/java/org/openstreetmap/atlas/geography/atlas/items/Edge.java#L55) function, we can recursively loop through each Edge's connected Edges until each have been either marked as flagged, or added to our roundAboutEdges Set.

* *Edge edge* - current edge being analyzed
* *Set<Edge> roundaboutEdges* - Collection of roundabout edges 
* *List<Long> edgeIds* - used to easily keep track and compare roundabout Edge ids

```java
    private void stitchRoundaboutEdges(Edge edge, Set<Edge> roundaboutEdges, List<Long> edgeIds) {
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
                        // or itself
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
```

If our roundaboutEdges Collection has one or less Edges, we can assume this roundabout is a single feature. Alternatively, we’ll flag this Collection as a multi-featured roundabout. 

```java
        // Rule: If roundaboutEdges has one or less edges, we have a single feature roundabout
        if (roundaboutEdges.size() <= 1
                // analyze list of edge and osmIds
                || isSingleFeature(Long.toString(edge.getOsmIdentifier()), edgeIds))
        {
            return Optional.empty();
        }
        else
        {
            // mark as flagged
            this.markAsFlagged(object.getIdentifier());
            // create flag w/ instructions
            return Optional.of(
                    this.createFlag(roundaboutEdges, this.getLocalizedInstruction(0)));
        }
```

It's important to note that I use the [`getIdentifier()`](https://github.com/osmlab/atlas/blob/dev/src/main/java/org/openstreetmap/atlas/geography/atlas/items/AtlasObject.java#L26) function to store unique indentifiers for each Edge. Because of [Way Sectioning](https://github.com/osmlab/atlas#way-sectioning), we'll come across Edges with the same OSM Identifier. For example, Edges 13092706600001,13092706600002, & 13092706600003 share 130927066 as their OSM identifer. I created a simple helper function, `isSingleFeature(String osmId, List<Long> edgeIds)` that compares our Edge's OSM identifier w/ the list's unique identifiers generated above. 

```java
    private boolean isSingleFeature(String osmId, List<Long> edgeIds){
        List matches = edgeIds
                .stream()
                // returns true is roundabout Id contains full osmId value
                .filter(id -> Long.toString(id).contains(osmId))
                .collect(Collectors.toList());

        // if each value passes our test, this roundabout is a single feature
        return matches.size() == edgeIds.size();
    }
```

To learn more about the code, please look at the comments in the source code for the check.
[MultiFeatureRoundaboutCheck.java](../../src/main/java/org/openstreetmap/atlas/checks/validation/MultiFeatureRoundaboutCheck.java)
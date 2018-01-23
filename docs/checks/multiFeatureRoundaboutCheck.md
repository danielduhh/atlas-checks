# MutifeatureRoundaboutCheck

#### Description

This check identifies multi-featured roundabouts. As indicated by the OSM Wiki, [roundabouts](https://wiki.openstreetmap.org/wiki/Tag:junction%3Droundabout) should be created as a single feature, or “closed way.” Our goal is to flag each roundabout created with multiple Ways so an OSM Editor can easily stitch them together.

#### Live Example
Take a look at the following Way, [id:331929481](https://www.openstreetmap.org/way/331929481). It has been incorrectly drawn. Using the iD Editor, we can select each feature with the Shift key, right click, and select merge them stitch them togethe

#### Code Review

In [Atlas](https://github.com/osmlab/atlas), OSM elements are represented as Edges, Points, Lines, Nodes & Relations; in our case, we’re working with [Edges](https://github.com/osmlab/atlas/blob/dev/src/main/java/org/openstreetmap/atlas/geography/atlas/items/Edge.java). In OpenStreetMap, roundabouts are [Ways](https://wiki.openstreetmap.org/wiki/Way) classified with the `junction=roundabout` tag. We’ll use this information to filter our potential flag candidates.

Our first goal is to validate the incoming Atlas Object. We know two things about roundabouts:
* Must be a valid Edge
* Must have junction=roundabout tag

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

If this Collection has one or less Edges, we can assume this roundabout is a single feature. Alternatively, we’ll flag this Collection as a multi-featured roundabout. 

To learn more about the code, please look at the comments in the source code for the check.
[MultiFeatureRoundaboutCheck.java](../../src/main/java/org/openstreetmap/atlas/checks/validation/MultiFeatureRoundaboutCheck.java)
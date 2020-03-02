package org.openstreetmap.atlas.checks.validation.intersections;

import org.openstreetmap.atlas.geography.atlas.Atlas;
import org.openstreetmap.atlas.tags.RelationTypeTag;
import org.openstreetmap.atlas.utilities.testing.CoreTestRule;
import org.openstreetmap.atlas.utilities.testing.TestAtlas;
import org.openstreetmap.atlas.utilities.testing.TestAtlas.Area;
import org.openstreetmap.atlas.utilities.testing.TestAtlas.Edge;
import org.openstreetmap.atlas.utilities.testing.TestAtlas.Line;
import org.openstreetmap.atlas.utilities.testing.TestAtlas.Loc;
import org.openstreetmap.atlas.utilities.testing.TestAtlas.Node;
import org.openstreetmap.atlas.utilities.testing.TestAtlas.Relation;
import org.openstreetmap.atlas.utilities.testing.TestAtlas.Relation.Member;

/**
 * {@link LineCrossingWaterBodyCheckTest} data generator
 *
 * @author mkalender
 */
public class LineCrossingWaterBodyCheckTestRule extends CoreTestRule
{

    private static final String AREA_LOCATION_1 = "47.576973, -122.304985";
    private static final String AREA_LOCATION_2 = "47.575661, -122.304222";
    private static final String AREA_LOCATION_3 = "47.574612, -122.305855";
    private static final String AREA_LOCATION_4 = "47.575371, -122.308121";
    private static final String AREA_LOCATION_5 = "47.576485, -122.307098";
    private static final String AREA_LOCATION_BETWEEN_2_AND_3 = "47.5751365,-122.3050385";

    private static final String LOCATION_OUTSIDE_AREA_1 = "47.578064, -122.318642";
    private static final String LOCATION_OUTSIDE_AREA_2 = "47.581829, -122.303734";
    private static final String LOCATION_OUTSIDE_AREA_3 = "47.573128, -122.292999";
    private static final String LOCATION_OUTSIDE_AREA_4 = "47.569073, -122.309608";

    @TestAtlas(
            // nodes
            nodes = { @Node(coordinates = @Loc(value = AREA_LOCATION_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_2)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_3)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_4)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_5)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_1)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_2)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_3)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_4)) },
            // area
            areas = { @Area(coordinates = { @Loc(value = AREA_LOCATION_1),
                    @Loc(value = AREA_LOCATION_2), @Loc(value = AREA_LOCATION_3),
                    @Loc(value = AREA_LOCATION_4),
                    @Loc(value = AREA_LOCATION_5) }, tags = { "natural=water" }) },
            // edges
            edges = {
                    // an edge
                    @Edge(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = LOCATION_OUTSIDE_AREA_2) }),
                    // another edge
                    @Edge(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }) },
            // lines
            lines = {
                    // a line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_4),
                            @Loc(value = LOCATION_OUTSIDE_AREA_1) }) })
    private Atlas noCrossingItemsAtlas;

    @TestAtlas(
            // nodes
            nodes = { @Node(coordinates = @Loc(value = AREA_LOCATION_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_2)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_3)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_4)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_5)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_1)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_2)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_3)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_4)) },
            // area
            areas = { @Area(coordinates = { @Loc(value = AREA_LOCATION_1),
                    @Loc(value = AREA_LOCATION_2), @Loc(value = AREA_LOCATION_3),
                    @Loc(value = AREA_LOCATION_4),
                    @Loc(value = AREA_LOCATION_5) }, tags = { "natural=water" }) },
            // edges
            edges = {
                    // an edge
                    @Edge(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }, tags = { "bridge=yes" }),
                    // another edge
                    @Edge(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = { "tunnel=yes" }),
                    // another edge
                    @Edge(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = { "embankment=yes" }) },
            // lines
            lines = {
                    // a line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }, tags = { "waterway=river" }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = {
                                    "boundary=administrative" }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = AREA_LOCATION_5),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = {
                                    "landuse=construction" }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = { "power=line" }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = {
                                    "location=underwater" }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = {
                                    "location=underground" }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = { "man_made=pier" }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = { "bridge=aqueduct" }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = { "route=ferry" }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = {
                                    "highway=construction" }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = {
                                    "addr:street=john" }), })
    private Atlas validCrossingItemsAtlas;

    @TestAtlas(
            // nodes
            nodes = { @Node(coordinates = @Loc(value = AREA_LOCATION_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_2)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_3)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_4)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_5)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_1)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_2)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_3)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_4)) },
            // area
            areas = { @Area(coordinates = { @Loc(value = AREA_LOCATION_1),
                    @Loc(value = AREA_LOCATION_2), @Loc(value = AREA_LOCATION_3),
                    @Loc(value = AREA_LOCATION_4),
                    @Loc(value = AREA_LOCATION_5) }, tags = { "natural=water" }) },
            // edges
            edges = {
                    // an edge
                    @Edge(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }),
                    // another edge
                    @Edge(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }) },
            // lines
            lines = {
                    // a line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = AREA_LOCATION_5), @Loc(value = LOCATION_OUTSIDE_AREA_3) }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }) })
    private Atlas invalidCrossingItemsAtlas;

    @TestAtlas(
            // nodes
            nodes = { @Node(coordinates = @Loc(value = AREA_LOCATION_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_2)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_3)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_4)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_5)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_1)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_2)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_3)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_4)) },
            // area
            areas = { @Area(coordinates = { @Loc(value = AREA_LOCATION_1),
                    @Loc(value = AREA_LOCATION_2), @Loc(value = AREA_LOCATION_3),
                    @Loc(value = AREA_LOCATION_4),
                    @Loc(value = AREA_LOCATION_5) }, tags = { "natural=water" }) },
            // edges
            edges = {
                    // an edge
                    @Edge(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = AREA_LOCATION_4), @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }),
                    // another edge
                    @Edge(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = AREA_LOCATION_4), @Loc(value = AREA_LOCATION_5),
                            @Loc(value = LOCATION_OUTSIDE_AREA_2) }),
                    // another edge
                    @Edge(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_1), @Loc(value = AREA_LOCATION_5),
                            @Loc(value = AREA_LOCATION_2),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }) },
            // lines
            lines = {
                    // a line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = AREA_LOCATION_4), @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }),
                    // another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = AREA_LOCATION_4), @Loc(value = AREA_LOCATION_5),
                            @Loc(value = LOCATION_OUTSIDE_AREA_2) }),
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_1), @Loc(value = AREA_LOCATION_5),
                            @Loc(value = AREA_LOCATION_2),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }) })
    private Atlas validIntersectionItemsAtlas;

    @TestAtlas(
            // nodes
            nodes = { @Node(coordinates = @Loc(value = AREA_LOCATION_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_2)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_3)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_4)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_5)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_BETWEEN_2_AND_3)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_1)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_2)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_3)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_4)) },
            // area
            areas = { @Area(coordinates = { @Loc(value = AREA_LOCATION_1),
                    @Loc(value = AREA_LOCATION_2), @Loc(value = AREA_LOCATION_3),
                    @Loc(value = AREA_LOCATION_4),
                    @Loc(value = AREA_LOCATION_5) }, tags = { "natural=water" }) },
            // edges
            edges = {
                    // an edge
                    @Edge(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_4),
                            @Loc(value = AREA_LOCATION_BETWEEN_2_AND_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }) },
            // lines
            lines = {
                    // a line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_4),
                            @Loc(value = AREA_LOCATION_BETWEEN_2_AND_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }) })
    private Atlas invalidIntersectionItemsAtlas;

    @TestAtlas(
            // Nodes
            nodes = { @Node(coordinates = @Loc(value = AREA_LOCATION_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_2)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_3)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_4)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_5)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_1)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_2)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_3)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_4)) },
            // Area
            areas = { @Area(coordinates = { @Loc(value = AREA_LOCATION_1),
                    @Loc(value = AREA_LOCATION_2), @Loc(value = AREA_LOCATION_3),
                    @Loc(value = AREA_LOCATION_4),
                    @Loc(value = AREA_LOCATION_5) }, tags = { "natural=water" }) },
            // Lines
            lines = {
                    // Line with tags
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }, tags = { "route=road" }),
                    // Another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = { "route=road" }),
                    // Another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = AREA_LOCATION_5),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }, tags = { "route=road" }),
                    // Another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = { "route=road" }) })
    private Atlas invalidLineCrossingAtlas;

    @TestAtlas(
            // Nodes
            nodes = { @Node(coordinates = @Loc(value = AREA_LOCATION_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_2)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_3)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_4)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_5)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_1)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_2)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_3)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_4)) },
            // Area
            areas = { @Area(coordinates = { @Loc(value = AREA_LOCATION_1),
                    @Loc(value = AREA_LOCATION_2), @Loc(value = AREA_LOCATION_3),
                    @Loc(value = AREA_LOCATION_4),
                    @Loc(value = AREA_LOCATION_5) }, tags = { "natural=water" }) },
            // Lines
            lines = {
                    // Line crossing waterbody with no tags
                    @Line(id = "1", coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }),
                    // Line crossing waterbody with no tags
                    @Line(id = "2", coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }),
                    // Line crossing water body with tags not in whitelist, will be flagged
                    @Line(id = "3", coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = AREA_LOCATION_5),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }, tags = { "route=road" }),
                    // Another line crossing waterbody with no tags
                    @Line(id = "4", coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }) },
            // Relations
            relations = {
                    // Multipolygon relation with no whitelisted tags - will be flagged
                    @Relation(members = {
                            @Member(id = "1", type = "line", role = RelationTypeTag.MULTIPOLYGON_ROLE_OUTER),
                            @Member(id = "2", type = "line", role = RelationTypeTag.MULTIPOLYGON_ROLE_INNER) }, tags = {
                                    "type=multipolygon" }),
                    // Multipolygon relation with whitelisted tag - will not be flagged
                    @Relation(members = {
                            @Member(id = "4", type = "line", role = RelationTypeTag.MULTIPOLYGON_ROLE_OUTER) }, tags = {
                                    "type=multipolygon", "place=village" }) })
    private Atlas multipolygonMemberCrossingAtlas;

    @TestAtlas(
            // Nodes
            nodes = { @Node(coordinates = @Loc(value = AREA_LOCATION_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_2)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_3)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_4)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_5)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_1)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_2)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_3)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_4)) },
            // Area
            areas = { @Area(coordinates = { @Loc(value = AREA_LOCATION_1),
                    @Loc(value = AREA_LOCATION_2), @Loc(value = AREA_LOCATION_3),
                    @Loc(value = AREA_LOCATION_4),
                    @Loc(value = AREA_LOCATION_5) }, tags = { "natural=water" }) },
            // Lines
            lines = {
                    // Line with tags
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }),
                    // Another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }),
                    // Another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = AREA_LOCATION_5), @Loc(value = LOCATION_OUTSIDE_AREA_3) }),
                    // Another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }) })
    private Atlas crossingLineWithNoOsmTagAtlas;

    @TestAtlas(
            // Nodes
            nodes = { @Node(coordinates = @Loc(value = AREA_LOCATION_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_2)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_3)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_4)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_5)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_1)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_2)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_3)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_4)) },
            // Area
            areas = { @Area(coordinates = { @Loc(value = AREA_LOCATION_1),
                    @Loc(value = AREA_LOCATION_2), @Loc(value = AREA_LOCATION_3),
                    @Loc(value = AREA_LOCATION_4),
                    @Loc(value = AREA_LOCATION_5) }, tags = { "natural=water" }) },
            // Lines
            lines = {
                    // Line with tags
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = LOCATION_OUTSIDE_AREA_3) }, tags = { "place=village" }),
                    // Another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }, tags = { "admin_level=1" }),
                    // Another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_1),
                            @Loc(value = AREA_LOCATION_5), @Loc(value = LOCATION_OUTSIDE_AREA_3) }),
                    // Another line
                    @Line(coordinates = { @Loc(value = LOCATION_OUTSIDE_AREA_2),
                            @Loc(value = AREA_LOCATION_3),
                            @Loc(value = LOCATION_OUTSIDE_AREA_4) }) })
    private Atlas crossingLineWithValidLineTagAtlas;

    @TestAtlas(
            // Nodes
            nodes = { @Node(coordinates = @Loc(value = AREA_LOCATION_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_2)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_3)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_4)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_5)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_BETWEEN_2_AND_3)) },
            // Area
            areas = {
                    @Area(coordinates = { @Loc(value = AREA_LOCATION_1),
                            @Loc(value = AREA_LOCATION_2), @Loc(value = AREA_LOCATION_3),
                            @Loc(value = AREA_LOCATION_4),
                            @Loc(value = AREA_LOCATION_5) }, tags = { "landuse=reservoir" }),
                    @Area(coordinates = { @Loc(LOCATION_OUTSIDE_AREA_1), @Loc(AREA_LOCATION_1),
                            @Loc(AREA_LOCATION_BETWEEN_2_AND_3) }, tags = { "building=hut" }) })
    private Atlas invalidCrossingBuildingAtlas;

    @TestAtlas(
            // Nodes
            nodes = { @Node(coordinates = @Loc(value = AREA_LOCATION_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_2)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_3)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_4)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_5)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_BETWEEN_2_AND_3)) },
            // Lines
            lines = { @Line(coordinates = { @Loc(AREA_LOCATION_1),
                    @Loc(LOCATION_OUTSIDE_AREA_1) }, tags = "railway=abandoned") })
    private Atlas validCrossingLineItemAtlas;

    @TestAtlas(
            // Nodes
            nodes = { @Node(coordinates = @Loc(value = AREA_LOCATION_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_2)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_3)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_4)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_5)),
                    @Node(coordinates = @Loc(value = LOCATION_OUTSIDE_AREA_1)),
                    @Node(coordinates = @Loc(value = AREA_LOCATION_BETWEEN_2_AND_3)) },
            // Lines
            lines = { @Line(coordinates = { @Loc(AREA_LOCATION_1),
                    @Loc(LOCATION_OUTSIDE_AREA_1) }, tags = "railway=tram") })
    private Atlas invalidCrossingLineItemAtlas;

    public Atlas crossingLineWithNoOsmTagAtlas()
    {
        return this.crossingLineWithNoOsmTagAtlas;
    }

    public Atlas crossingLineWithValidLineTagAtlas()
    {
        return this.crossingLineWithValidLineTagAtlas;
    }

    public Atlas invalidCrossingBuildingAtlas()
    {
        return this.invalidCrossingBuildingAtlas;
    }

    public Atlas invalidCrossingItemsAtlas()
    {
        return this.invalidCrossingItemsAtlas;
    }

    public Atlas invalidCrossingLineItemAtlas()
    {
        return this.invalidCrossingLineItemAtlas;
    }

    public Atlas invalidIntersectionItemsAtlas()
    {
        return this.invalidIntersectionItemsAtlas;
    }

    public Atlas invalidLineCrossingAtlas()
    {
        return this.invalidLineCrossingAtlas;
    }

    public Atlas multipolygonMemberCrossingAtlas()
    {
        return this.multipolygonMemberCrossingAtlas;
    }

    public Atlas noCrossingItemsAtlas()
    {
        return this.noCrossingItemsAtlas;
    }

    public Atlas validCrossingItemsAtlas()
    {
        return this.validCrossingItemsAtlas;
    }

    public Atlas validCrossingLineItemAtlas()
    {
        return this.validCrossingLineItemAtlas;
    }

    public Atlas validIntersectionItemsAtlas()
    {
        return this.validIntersectionItemsAtlas;
    }
}

package org.openstreetmap.atlas.checks.validation;

import org.junit.Test;
import org.openstreetmap.atlas.geography.atlas.Atlas;
import org.openstreetmap.atlas.utilities.testing.CoreTestRule;
import org.openstreetmap.atlas.utilities.testing.TestAtlas;
import org.openstreetmap.atlas.utilities.testing.TestAtlas.Edge;
import org.openstreetmap.atlas.utilities.testing.TestAtlas.Loc;
import org.openstreetmap.atlas.utilities.testing.TestAtlas.Node;

import static org.junit.Assert.*;

public class MultiFeatureRoundaboutCheckTestRule extends CoreTestRule {
    private static final String TEST_1 = "37.3620565364349,-122.03360080718994";
    private static final String TEST_2 = "37.36140844684423,-122.0345664024353";
    private static final String TEST_3 = "37.361101455084594,-122.03355789184572";
    private static final String TEST_4 = "37.361715437348025,-122.03257083892822";

    @TestAtlas(
            nodes ={
                    @Node(coordinates = @Loc(value = TEST_1)),
                    @Node(coordinates = @Loc(value = TEST_2)),
                    @Node(coordinates = @Loc(value = TEST_3)),
                    @Node(coordinates = @Loc(value = TEST_4))
            },
            edges = {
                    @Edge(id = "12700600001" , coordinates = {@Loc(value = TEST_1), @Loc(value = TEST_2)},
                            tags = {"junction=roundabout"}),
                    @Edge(id = "12700600002" , coordinates = {@Loc(value = TEST_2), @Loc(value = TEST_3)},
                            tags = {"junction=roundabout"}),
                    @Edge(id = "12700600003" , coordinates = {@Loc(value = TEST_3), @Loc(value = TEST_4),},
                            tags = {"junction=roundabout"}),
                    @Edge(id = "12700600004" , coordinates = {@Loc(value = TEST_4), @Loc(value = TEST_1)},
                            tags = {"junction=roundabout"})
            }
    )
    private Atlas validSectionedWayRoundabout;

    @TestAtlas(
            nodes ={
                    @Node(coordinates = @Loc(value = TEST_2)),
                    @Node(coordinates = @Loc(value = TEST_3))
            },
            edges = {
                    @Edge(id = "12700600002" , coordinates = {@Loc(value = TEST_2), @Loc(value = TEST_3)},
                            tags = {"notquite=aroundabout"})
            }
    )
    private Atlas missingRoundaboutTag;

    @TestAtlas(
            nodes ={
                    @Node(coordinates = @Loc(value = TEST_1)),
                    @Node(coordinates = @Loc(value = TEST_4))
            },
            edges = {
                    @Edge(coordinates = {@Loc(value = TEST_4), @Loc(value = TEST_1)},
                            tags = {"junction=roundabout"})
            }
    )
    private Atlas validSingleFeatureRoundabout;

    @TestAtlas(
            nodes = {
                    @Node(coordinates = @Loc(value = TEST_1)),
                    @Node(coordinates = @Loc(value = TEST_2)),
                    @Node(coordinates = @Loc(value = TEST_3)),
                    @Node(coordinates = @Loc(value = TEST_4))
            },
            edges = {
                    @Edge(id = "127121", coordinates = {@Loc(value = TEST_2), @Loc(value = TEST_3)},
                            tags = {"junction=roundabout"}),
                    @Edge(id = "127122", coordinates = {@Loc(value = TEST_3), @Loc(value = TEST_4)},
                            tags = {"junction=roundabout"}),
                    @Edge(id = "127123", coordinates = {@Loc(value = TEST_4), @Loc(value = TEST_1)},
                            tags = {"junction=roundabout"}),
                    @Edge(id = "127124", coordinates = {@Loc(value = TEST_1), @Loc(value = TEST_2)},
                            tags = {"junction=roundabout"})
            }
    )
    private Atlas invalidMultiFeatureRoundabout;

    public Atlas validSectionedWayRoundabout() {
        return this.validSectionedWayRoundabout;
    }

    public Atlas missingRoundaboutTag() {
        return this.missingRoundaboutTag;
    }

    public Atlas validSingleFeatureRoundabout() {
        return this.validSingleFeatureRoundabout;
    }

    public Atlas invalidMultiFeatureRoundabout() {
        return this.invalidMultiFeatureRoundabout;
    }

}
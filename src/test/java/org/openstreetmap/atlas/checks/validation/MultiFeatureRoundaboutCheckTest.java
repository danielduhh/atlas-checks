package org.openstreetmap.atlas.checks.validation;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openstreetmap.atlas.checks.configuration.ConfigurationResolver;
import org.openstreetmap.atlas.checks.flag.CheckFlag;
import org.openstreetmap.atlas.checks.validation.verifier.ConsumerBasedExpectedCheckVerifier;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

/***
 * Tests for {@link MultiFeatureRoundaboutCheck}
 *
 * @author danielbaah
 */
public class MultiFeatureRoundaboutCheckTest {

    @Rule
    public MultiFeatureRoundaboutCheckTestRule setup = new MultiFeatureRoundaboutCheckTestRule();

    @Rule
    public ConsumerBasedExpectedCheckVerifier verifier = new ConsumerBasedExpectedCheckVerifier();

    @Test
    public void testValidSectionedWayRoundabout()
    {
        this.verifier.actual(this.setup.validSectionedWayRoundabout(),
                new MultiFeatureRoundaboutCheck(ConfigurationResolver.emptyConfiguration()));
        this.verifier.verifyEmpty();
    }

    @Test
    public void testNonRoundaboutTag()
    {
        this.verifier.actual(this.setup.missingRoundaboutTag(),
                new MultiFeatureRoundaboutCheck(ConfigurationResolver.emptyConfiguration()));
        this.verifier.verifyEmpty();
    }

    @Test
    public void testSingleFeatureRoundabout()
    {
        this.verifier.actual(this.setup.validSingleFeatureRoundabout(),
                new MultiFeatureRoundaboutCheck(ConfigurationResolver.emptyConfiguration()));
        this.verifier.verifyEmpty();
    }

    @Test
    public void testInvalidMultiFeatureRoundabout()
    {
        this.verifier.actual(this.setup.invalidMultiFeatureRoundabout(),
                new MultiFeatureRoundaboutCheck(ConfigurationResolver.emptyConfiguration()));
        this.verifier.verify(flag ->
            Assert.assertEquals(flag.getInstructions(),
                    // osm id not generated in test, so use hard coded string
                    "1. This is a multi-feature roundabout: 0. Merge roundabout edges to create one feature (OSM Way).")
        );
    }
}
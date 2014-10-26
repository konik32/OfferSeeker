package pl.edu.agh.offerseeker.analysing;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by bartQH on 2014-10-26.
 */
public class VectorSpaceModelTest {
    @Test
    public void TermFrequencyTest() {
        String testString = "this is test this test test";
        String expectedString = "test:3 this:2 is:1 ";
        String actualString = "";

        Map<String, Integer> frequency = VectorSpaceModel.TermFrequency(testString);

        for (String key : frequency.keySet()) {
            actualString += key + ":" + frequency.get((key)) + " ";
        }

        assertEquals(expectedString, actualString);
    }


}

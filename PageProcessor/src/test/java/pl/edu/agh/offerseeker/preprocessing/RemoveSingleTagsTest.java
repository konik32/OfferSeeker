package pl.edu.agh.offerseeker.preprocessing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by bartQH on 2014-10-24.
 */
public class RemoveSingleTagsTest {

    String testPage = "<p><b>Bold</b> text is <b>necessary</b> here...";

    @Test
    public void preprocessTest() {
        RemoveSingleTags remOpenB = new RemoveSingleTags("b");
        RemoveSingleTags remCloseB = new RemoveSingleTags("/b");
        String actualPage = remOpenB.preprocess(testPage);
        actualPage = remCloseB.preprocess(actualPage);

        String expectedPage = "<p>Bold text is necessary here...";

        assertEquals(expectedPage, actualPage);
    }
}

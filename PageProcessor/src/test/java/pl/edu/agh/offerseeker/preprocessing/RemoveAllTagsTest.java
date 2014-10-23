package pl.edu.agh.offerseeker.preprocessing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by bartQH on 2014-10-23.
 */
public class RemoveAllTagsTest {
    private String refString = "This is simple test";

    @Test
    public void preprocessTest() {
        IPagePreprocessor pre = new RemoveAllTags();
        String page = "<br />This <b>is</b> <hr /></head><body>simple test</body>";
        String testString = pre.preprocess(page);

        assertEquals(refString, testString);
    }
}

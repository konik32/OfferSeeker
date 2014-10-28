package pl.edu.agh.offerseeker.preprocessing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by bartQH on 2014-10-24.
 */
public class ExtractContentOfBlockToSeparateLinesTest {

    @Test
    public void preprocessTest() {
        ExtractContentOfBlockToSeparateLines extract = new ExtractContentOfBlockToSeparateLines();
        String testPage = "<html><head><title>Simple title...</title></head>" +
                "<body><div class=\"ad\">SPAM <div>SPAM24</div>SPAM</div><div>Best.Offer ever!</div></html>";
        String actualPage = extract.preprocess(testPage);

        String expectedPage = "spam24\nbest.offer ever!\nspam spam24spam\n";

        assertEquals(expectedPage, actualPage);
    }


}

package pl.edu.agh.offerseeker;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class WebPagePullerTest {
    private String refPage = "<html><head><title>\n" +
            "Very simple HTML page.\n" +
            "</title></head>\n" +
            "<body>\n" +
            "\n" +
            "<p>You can look at the source of this page by: Right clicking anywhere\n" +
            "out in space on this page then selecting \"View\" in the menu.</p>\n" +
            "<p>This works on any page, but sometimes\n" +
            "what you see may be very complex\n" +
            "and seem confusing.</p>\n" +
            "\n" +
            "<p>\n" +
            "<b>Please,</b> look at the source and what you see with the browser.\n" +
            "You should understand and see the effect of every tag. Use the little\n" +
            "Icons up in the right of your browser screen to change the size of the\n" +
            "window and see the effect, and how the browser displays this page.</p>\n" +
            "<p align=\"right\">\n" +
            "Yes, this is a <b>Very Plain</b> page. <i>But it works!</i></p>\n" +
            "<p><i><b>Remember. We are just getting started,</b> and I haven't used\n" +
            "anything more than I have talked about in a couple pages!</i>\n" +
            "Yes. You will want to be more fancy.\n" +
            "Just be patient, we'll get there.\n" +
            "\n" +
            "<p align=\"center\">Now create a page like\n" +
            "this of your own. <b>Have fun!</b></p\n" +
            "\n" +
            "</body></html>\n";

    @Test
    public void pullPageTest() {
        try {
            WebPagePuller pagePuller = new WebPagePuller();
            URL testPageUrl = new URL("http://www.zyvra.org/html/simple.htm");
            String testPage = pagePuller.pullPage(testPageUrl);
            for (int i = 0; i < testPage.length(); i++) {
                if (testPage.charAt(i) != refPage.charAt(i)) {
                    System.out.println("Char: " + i + " at " + testPage.charAt(i));
                }
            }
            assertEquals(testPage, refPage);
        } catch (MalformedURLException mExcept) {
            Assert.fail("MalformedURLException has occurred!");
        } catch (IOException ioExcept) {
            Assert.fail("Failed to get page content");
        } catch (Exception eExcept) {
            Assert.fail("Unexpected exception");
        }
    }
}

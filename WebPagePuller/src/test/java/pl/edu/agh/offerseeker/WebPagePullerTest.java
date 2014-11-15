package pl.edu.agh.offerseeker;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class WebPagePullerTest {
    private String expectedPage = "<html>\n" +
            " <head>\n" +
            "  <title>\n" +
            "Very simple HTML page.\n" +
            "</title>\n" +
            " </head> \n" +
            " <body> \n" +
            "  <p>You can look at the source of this page by: Right clicking anywhere out in space on this page then selecting \"View\" in the menu.</p> \n" +
            "  <p>This works on any page, but sometimes what you see may be very complex and seem confusing.</p> \n" +
            "  <p> <b>Please,</b> look at the source and what you see with the browser. You should understand and see the effect of every tag. Use the little Icons up in the right of your browser screen to change the size of the window and see the effect, and how the browser displays this page.</p> \n" +
            "  <p align=\"right\"> Yes, this is a <b>Very Plain</b> page. <i>But it works!</i></p> \n" +
            "  <p><i><b>Remember. We are just getting started,</b> and I haven't used anything more than I have talked about in a couple pages!</i> Yes. You will want to be more fancy. Just be patient, we'll get there. </p>\n" +
            "  <p align=\"center\">Now create a page like this of your own. <b>Have fun!</b></p> \n" +
            " </body>\n" +
            "</html>";

    @Test
    public void pullPageTest() {
        try {
            WebPagePuller pagePuller = new WebPagePuller();
            URL testPageUrl = new URL("http://www.zyvra.org/html/simple.htm");
            String testPage = pagePuller.pullPage(testPageUrl).toString();
            assertEquals(expectedPage, testPage);
        } catch (MalformedURLException mExcept) {
            Assert.fail("MalformedURLException has occurred!");
        } catch (IOException ioExcept) {
            Assert.fail("Failed to get page content");
        } catch (Exception eExcept) {
            Assert.fail("Unexpected exception");
        }
    }
}

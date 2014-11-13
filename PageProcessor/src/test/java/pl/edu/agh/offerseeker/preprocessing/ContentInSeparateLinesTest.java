package pl.edu.agh.offerseeker.preprocessing;

import junit.framework.Assert;
import org.jsoup.nodes.Document;
import org.junit.Test;
import pl.edu.agh.offerseeker.WebPagePuller;

import java.io.IOException;
import java.net.URL;

/**
 * Created by bartQH on 2014-11-08.
 */
public class ContentInSeparateLinesTest {
    private String expected = "main menu html php perl... technical and managerial tutorials right menu html php perl...\n" +
            "technical and managerial tutorials\n" +
            "home programming java web database academic more android tutorial iphone tutorial phonegap tutorial management tutorials software quality tutorials telecom tutorials miscellaneous tutorials\n" +
            "write for us faq's helping contact\n" +
            "this is web page main title\n" +
            "© copyright 2014. all rights reserved.\n" +
            "copyright © 2007 tutorialspoint.com\n" +
            "this is web page main title main menu html php perl... technical and managerial tutorials copyright © 2007 tutorialspoint.com\n" +
            "facebook google+ twitter rss\n";

    @Test
    public void preprocessTest() throws IOException {
        URL url = new URL("http://www.tutorialspoint.com/html/html_layouts.htm");
        WebPagePuller puller = new WebPagePuller();
        Document x = puller.pullPage(url);
        String current = new ContentInSeparateLines(x).preprocess();

        Assert.assertEquals(expected, current);
    }

}

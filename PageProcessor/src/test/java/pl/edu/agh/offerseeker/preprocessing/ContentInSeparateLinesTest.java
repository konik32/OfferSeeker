package pl.edu.agh.offerseeker.preprocessing;

import org.jsoup.nodes.Document;
import org.junit.Test;
import pl.edu.agh.offerseeker.WebPagePuller;

import java.io.IOException;
import java.net.URL;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by bartQH on 2014-11-08.
 */
public class ContentInSeparateLinesTest {
    private String[] expectedLines = new String[]{
            "main menu html php perl... technical and managerial tutorials right menu html php perl...",
            "technical and managerial tutorials",
            "home programming java web database academic more android tutorial iphone tutorial phonegap tutorial management tutorials software quality tutorials telecom tutorials miscellaneous tutorials",
            "write for us faq's helping contact",
            "this is web page main title",
            "© copyright 2014. all rights reserved.",
            "copyright © 2007 tutorialspoint.com",
            "this is web page main title main menu html php perl... technical and managerial tutorials copyright © 2007 tutorialspoint.com",
            "facebook google+ twitter rss"
    };

    @Test
    public void preprocessTest() throws IOException {
        URL url = new URL("http://www.tutorialspoint.com/html/html_layouts.htm");
        WebPagePuller puller = new WebPagePuller();
        Document x = puller.pullPage(url);
        String extractedLines = new ContentInSeparateLines(x).preprocess();

        assertThat(extractedLines.split("\n").length).isEqualTo(expectedLines.length);
        for(String expectedLine : expectedLines) {
            assertThat(extractedLines).contains(expectedLine);
        }
    }

}

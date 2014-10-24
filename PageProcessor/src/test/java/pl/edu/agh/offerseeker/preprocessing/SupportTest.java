package pl.edu.agh.offerseeker.preprocessing;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by bartQH on 2014-10-24.
 */
public class SupportTest {
    String testPage = "<html>\n" +
            "<head>\n" +
            "<title>This is simple title</title>\n" +
            "<meta charset=\"utf-8\" />\n" +
            "</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div class=\"test\">\n" +
            "<h1>Header<h1>\n" +
            "<p>Page paragraph...</p>\n" +
            "</div>\n" +
            "<div style=\"background-color:#202020\">\n" +
            "Hello...\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";

    @Test
    public void getAllOccurrencesStringTest() {
        List<Integer> openTagIndices = Support.getAllOccurrencesOfString(testPage, "<div");


        assertEquals(99, openTagIndices.get(0).intValue());
        assertEquals(165, openTagIndices.get(1).intValue());
    }
}

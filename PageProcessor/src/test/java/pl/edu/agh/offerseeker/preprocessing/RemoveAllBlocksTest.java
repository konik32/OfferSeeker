package pl.edu.agh.offerseeker.preprocessing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by bartQH on 2014-10-24.
 */
public class RemoveAllBlocksTest {

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
    public void preprocessTest() {
        RemoveAllBlocks rem = new RemoveAllBlocks("div");
        String actualPage = rem.preprocess(testPage);
        String expectedPage = "<html>\n" +
                "<head>\n" +
                "<title>This is simple title</title>\n" +
                "<meta charset=\"utf-8\" />\n" +
                "</title>\n" +
                "</head>\n" +
                "<body>\n\n\n" +
                "</body>\n" +
                "</html>";

        assertEquals(expectedPage, actualPage);
    }
}

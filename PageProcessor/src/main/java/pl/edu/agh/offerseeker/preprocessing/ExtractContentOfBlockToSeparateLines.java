package pl.edu.agh.offerseeker.preprocessing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;

/**
 * Created by bartQH on 2014-10-24.
 */
public class ExtractContentOfBlockToSeparateLines implements IPagePreprocessor {

    @Override
    public String preprocess(String page) {
        HashSet<String> lines = new HashSet<String>();
        Document doc = null;
        doc = Jsoup.parse(page);

        Elements divs = doc.select("div");
        for (Element x : divs) {
            if (x.hasText())
                lines.add(x.text().replace("\r\n", "").replace("\n", ""));
        }
        StringBuilder builder = new StringBuilder();
        for (String line : lines)
            builder.append(line.toLowerCase().replace("\r\n", "").replace("\n", "").trim() + "\n");

        return builder.toString();

    }

}

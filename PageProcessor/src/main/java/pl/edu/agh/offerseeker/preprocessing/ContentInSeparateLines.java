package pl.edu.agh.offerseeker.preprocessing;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;

/* Ma za zadanie wyciecie potencjalnych ogloszen i zapis w linii*/
public class ContentInSeparateLines {

    private Document _page;

    public ContentInSeparateLines(Document page) {
        _page = page;
    }

    /* Kazde potencjalne oglosznie w nowej linii */
    public String preprocess() {
        HashSet<String> lines = extractContent();

        StringBuilder builder = new StringBuilder();
        for (String line : lines)
            builder.append(line.toLowerCase() + "\n");

        return builder.toString();

    }

    /* Wyciagnieniecie tylko wazne dane ze strony, bez duplikatow */
    private HashSet<String> extractContent() {

        HashSet<String> lines = new HashSet<String>();
        Elements divs = _page.select("div");
        for (Element x : divs) {
            // construction limit
            if (x.hasText() && x.attributes().size() > 0 && x.children().size() < 2 && x.select("div").size() == 1) {
                String inner = x.text();
                inner = inner.replace("\u00a0", "");
                // length limit
                if (inner.length() > 20) {
                    // weird characters at beginning
                    if (!inner.substring(0, 10).matches(".*[<>].*")) {
                        lines.add(inner);
                    }
                }
            }
        }

        return lines;
    }

}

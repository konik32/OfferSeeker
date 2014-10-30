package pl.edu.agh.offerseeker.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.edu.agh.offerseeker.IWebPagePuller;
import pl.edu.agh.offerseeker.model.Offer;
import pl.edu.agh.offerseeker.model.OfferEvaluation;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by bartQH on 2014-10-28.
 */
public class SVMPageProcessor implements IPageProcessor {
    private IWebPagePuller pagePuller;

    public SVMPageProcessor(IWebPagePuller pagePuller) {
        this.pagePuller = pagePuller;
    }

    @Override
    public Offer processPage(URL url) throws IOException {
        // set up page
        Document page = pagePuller.pullPage(url);

        // remove nonbreakable space
        page.select(":containsOwn(\u00a0)").remove();
        // remove scripts
//

//        // remove tables
//        for (Element element : page.select("table"))
//                element.remove();

        // remove noscrpits
        for (Element element : page.select("noscript"))
            element.remove();

        // remove head
        for (Element element : page.select("head"))
            element.remove();

        // remove images
        for (Element element : page.select("img"))
            element.remove();

        // remove headers
        for (Element element : page.select("h1,h2,h3,h4,h5,h6"))
            element.remove();

        // select specific content form page
        // remove duplicates
        HashSet<String> lines = new HashSet<String>();

        Elements divs = page.select("div");
        for (Element x : divs) {
            String inner = x.text();
            inner = inner.replace("\u00a0", "");
            // construction limit
            if (x.hasText() && x.attributes().size() > 0 && x.children().size() < 2 && x.select("div").size() == 1) {
                // length limit
                if (inner.length() > 20) {
                    // weird characters at beginning
                    if (!inner.substring(0, 10).matches(".*[<>].*")) {
                        lines.add(inner);
                    }
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for (String line : lines)
            builder.append(line.toLowerCase() + "\n");

        System.out.println(builder.toString());


        return null;
    }

    @Override
    public void evaluateOffer(UUID offerID, OfferEvaluation evaluation) {

    }

}

//class Program {
//    public static void main(String[] args) {
//        try {
//            SVMPageProcessor processor = new SVMPageProcessor(new WebPagePuller());
//            processor.processPage(new URL("http://olx.pl/oferta/ulotkarz-plakaciarz-kolporter-z-doswiadczenie-stale-zlecenia-krakow-CID4-ID7kAcF.html#050bd1040c;promoted"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
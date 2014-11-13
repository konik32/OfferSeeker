package pl.edu.agh.offerseeker.service;

import org.jsoup.nodes.Document;
import pl.edu.agh.offerseeker.WebPagePuller;
import pl.edu.agh.offerseeker.model.Offer;
import pl.edu.agh.offerseeker.model.OfferEvaluation;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class GumtreePageProcessor implements IPageProcessor {
    private WebPagePuller pagePuller;

    public GumtreePageProcessor() {
        pagePuller = new WebPagePuller();
    }

    @Override
    public Offer processPage(URL url) throws IOException {
        Document page = pagePuller.pullPage(url);
        String description = page.select("#ad-desc").text();

        return new Offer(description);
    }

    @Override
    public void evaluateOffer(UUID offerID, OfferEvaluation evaluation) {
        //TODO: implement method
    }

}

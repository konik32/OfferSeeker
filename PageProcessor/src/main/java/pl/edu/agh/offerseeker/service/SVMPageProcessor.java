package pl.edu.agh.offerseeker.service;

import org.jsoup.nodes.Document;
import pl.edu.agh.offerseeker.IWebPagePuller;
import pl.edu.agh.offerseeker.WebPagePuller;
import pl.edu.agh.offerseeker.analysing.VSM;
import pl.edu.agh.offerseeker.collecting.Provider;
import pl.edu.agh.offerseeker.model.Offer;
import pl.edu.agh.offerseeker.model.OfferEvaluation;
import pl.edu.agh.offerseeker.preprocessing.ContentInSeparateLines;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class SVMPageProcessor implements IPageProcessor {
    private IWebPagePuller _pagePuller;
    private VSM _vsm;

    public SVMPageProcessor(Provider provider) throws IOException {
        _pagePuller = new WebPagePuller();
        _vsm = new VSM(provider);
    }

    @Override
    public Offer processPage(URL url) throws IOException {
        Document page = _pagePuller.pullPage(url);
        String content = new ContentInSeparateLines(page).preprocess();
        List<Double> features = _vsm.getFeature(content);


        return null;
    }

    @Override
    public void evaluateOffer(UUID offerID, OfferEvaluation evaluation) {

    }

}

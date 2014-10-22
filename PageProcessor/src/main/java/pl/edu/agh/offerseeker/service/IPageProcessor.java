package pl.edu.agh.offerseeker.service;

import pl.edu.agh.offerseeker.model.Offer;
import pl.edu.agh.offerseeker.model.OfferEvaluation;

import java.net.URL;
import java.util.UUID;

/**
 * Created by Krzysztof Balon on 2014-10-21.
 */
public interface IPageProcessor {
    /**
     * @param url of page to be processed
     * @return extracted Offer from webpage or null
     * if it wasn't page with offer
     */
    Offer processPage(URL url);

    /**
     * @param offerID unique ID of offer
     * @param evaluation to be set for offer
     */
    void evaluateOffer(UUID offerID, OfferEvaluation evaluation);
}

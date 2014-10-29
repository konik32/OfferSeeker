package pl.edu.agh.offerseeker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.edu.agh.offerseeker.model.OfferEvaluation;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-10-29.
 */
@Service
public class OffersValidationService {

	@Autowired
	private IPageProcessor pageProcessor;

	@Async
	public void addValidationResponse(UUID offerID, boolean isOffer) {
		pageProcessor.evaluateOffer(offerID, (isOffer) ? OfferEvaluation.GOOD : OfferEvaluation.BAD);
	}


}

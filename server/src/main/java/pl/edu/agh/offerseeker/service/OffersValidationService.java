package pl.edu.agh.offerseeker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.offerseeker.domain.Statistic;
import pl.edu.agh.offerseeker.model.OfferEvaluation;
import pl.edu.agh.offerseeker.repository.StatisticRepository;

import java.util.UUID;

/**
 * Created by Mateusz on 2014-10-29.
 */
@Service
public class OffersValidationService {

	@Autowired
	private IPageProcessor pageProcessor;

	@Autowired
	private StatisticRepository repository;

	@Async
	public void addValidationResponse(UUID offerID, boolean isOffer) {
		pageProcessor.evaluateOffer(offerID, (isOffer) ? OfferEvaluation.GOOD : OfferEvaluation.BAD);
	}

	public void saveStatisticToDatabase(UUID offerID, boolean isOffer) {
		Statistic statistic = new Statistic(offerID, isOffer);
		repository.save(statistic);
	}
}

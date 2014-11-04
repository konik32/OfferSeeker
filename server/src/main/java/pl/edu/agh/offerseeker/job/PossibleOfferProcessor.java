package pl.edu.agh.offerseeker.job;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import pl.edu.agh.offerseeker.commons.model.PossibleOfferLink;
import pl.edu.agh.offerseeker.domain.Offer;
import pl.edu.agh.offerseeker.service.IPageProcessor;
/**
 * 
 * Custom {@link ItemProcessor} to process {@link PossibleOfferLink} into {@link Offer} using {@link IPageProcessor}.
 * 
 * 
 * @author Szymon Konicki
 *
 */
public class PossibleOfferProcessor implements ItemProcessor<PossibleOfferLink, Offer> {

	@Autowired
	private IPageProcessor pageProcessor;

	@Override
	public Offer process(PossibleOfferLink offerLink) throws Exception {
		pl.edu.agh.offerseeker.model.Offer processedOffer = pageProcessor.processPage(offerLink.getUrl());
		
		//TODO: set other offer's parameters
		return new Offer(processedOffer.getId(), processedOffer.getDescription(), null);
	}

}

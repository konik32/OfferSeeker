package pl.edu.agh.offerseeker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.agh.offerseeker.WebSpider.VisitedUrlsDatabase;
import pl.edu.agh.offerseeker.commons.model.PossibleOfferLink;
import pl.edu.agh.offerseeker.repository.PossibleOfferLinkRepository;

@Service
public class VisitedUrlDatabaseImpl implements VisitedUrlsDatabase {

	@Autowired
	private PossibleOfferLinkRepository repository;

	@Override
	public boolean isAlreadyVisited(PossibleOfferLink url) {
		return repository.findByUrl(url.getUrl()) != null;
	}

}

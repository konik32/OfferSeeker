package pl.edu.agh.offerseeker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import pl.edu.agh.offerseeker.domain.Domain;
import pl.edu.agh.offerseeker.domain.Offer;
import pl.edu.agh.offerseeker.repository.DomainRepository;
import pl.edu.agh.offerseeker.repository.StatisticRepository;
import pl.edu.agh.offerseeker.service.OffersFullTextSearchService;
import pl.edu.agh.offerseeker.service.OffersValidationService;

import javax.transaction.Transactional;

/**
 * @author Szymon Konicki
 */
@Controller
@RequestMapping("/api")
public class MainController {

	@Autowired
	private OffersValidationService offersValidationService;

	@Autowired
	private StatisticRepository repository;

	@Autowired
	private DomainRepository domainRepository;
	
	@Autowired
	private OffersFullTextSearchService offersSearchService;

	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	@ResponseBody
	public Page<Offer> getOffers(@RequestParam(value = "keywords", required=true) String keywords, Pageable pageable) {
		if(keywords == null || keywords.isEmpty()) return new PageImpl(Collections.emptyList(), pageable, 0);
		return offersSearchService.findByKeywords(keywords, pageable);
	}

	@RequestMapping(value = "/offers/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void getOfferValidation(@RequestParam("isOffer") boolean isOffer, @PathVariable("id") UUID offerID) {
		offersValidationService.addValidationResponse(offerID, isOffer);
		offersValidationService.saveStatisticToDatabase(offerID, isOffer);
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	@ResponseBody
	public Long getStatistics(@RequestParam(value = "isOffer") boolean isOffer, Pageable pageable) {
		return repository.countByIsOffer(isOffer);
	}

	@RequestMapping(value = "/domains", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void postDomain(@RequestParam(value = "url") URL url) {
		domainRepository.save(new Domain(url));
	}
}

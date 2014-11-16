package pl.edu.agh.offerseeker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import pl.edu.agh.offerseeker.domain.Domain;
import pl.edu.agh.offerseeker.domain.Offer;
import pl.edu.agh.offerseeker.domain.Statistic;
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
	public Page<Offer> getOffers(@RequestParam(value = "keywords", required=true) String keywords,@RequestParam(value = "timestamp", required=false)@DateTimeFormat(pattern="yyyy-MM-dd") Date timestamp, Pageable pageable) {
		if(keywords == null || keywords.isEmpty()) return new PageImpl(Collections.emptyList(), pageable, 0);
		return offersSearchService.findByKeywords(keywords, pageable, timestamp);
	}

	@RequestMapping(value = "/offers/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void getOfferValidation(@RequestParam("isOffer") boolean isOffer, @PathVariable("id") UUID offerID) {
		offersValidationService.addValidationResponse(offerID, isOffer);
		offersValidationService.saveStatisticToDatabase(offerID, isOffer);
	}

	@RequestMapping(value = "/statistics/count", method = RequestMethod.GET)
	@ResponseBody
	public Long getStatistics(@RequestParam(value = "isOffer", required = false) Boolean isOffer, Pageable pageable) {
		if(isOffer != null) return repository.countByIsOffer(isOffer);
		else return  repository.count();
	}

	@RequestMapping(value = "/statistics/list", method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Statistic> getAllStatistics(Pageable pageable,
	                                            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")Date startDate,
	                                            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate,
												@RequestParam(value = "isOffer", required = false) Boolean isOffer){
		if(startDate != null && endDate != null && isOffer != null)
			return repository.findByValidationDateBetweenAndIsOffer(startDate, endDate, isOffer);
		else if(startDate != null && endDate != null)
			return repository.findByValidationDateBetween(startDate, endDate);
		else if(startDate != null && isOffer != null) {
			Iterable<Statistic> list = repository.findByValidationDateGreaterThanOrValidationDate(startDate, startDate);
			LinkedList<Statistic> statisticsList = new LinkedList<>();
			for(Statistic statistic : list)
				if(statistic.isOffer() == isOffer)
					statisticsList.add(statistic);
			return statisticsList;
		}
		else if(startDate != null)
			return repository.findByValidationDateGreaterThanOrValidationDate(startDate, startDate);
		else if(endDate != null && isOffer != null) {
			Iterable<Statistic> list = repository.findByValidationDateLessThanOrValidationDate(endDate, endDate);
			LinkedList<Statistic> statisticsList = new LinkedList<>();
			for(Statistic statistic : list)
				if(statistic.isOffer() == isOffer)
					statisticsList.add(statistic);
			return statisticsList;
		}
		else if(endDate != null)
			return repository.findByValidationDateLessThanOrValidationDate(endDate, endDate);
		else if(isOffer != null)
			return repository.findByIsOffer(isOffer);
		else return repository.findAll();
	}

	@RequestMapping(value = "/domains", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void postDomain(@RequestParam(value = "url") URL url) {
		domainRepository.save(new Domain(url));
	}
}

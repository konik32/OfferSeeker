package pl.edu.agh.offerseeker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.offerseeker.domain.Offer;
import pl.edu.agh.offerseeker.domain.Statistic;
import pl.edu.agh.offerseeker.repository.StatisticRepository;
import pl.edu.agh.offerseeker.service.OffersValidationService;

import java.util.Date;
import java.util.UUID;

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

	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	@ResponseBody
	public Offer getOffers(@RequestParam("keywords") String keywords, Pageable pageable) {
		Offer offer = new Offer(UUID.randomUUID(), "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?", "Tytuł");
		return offer;
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
	                                            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate) {
		if(startDate != null && endDate != null)
		   return repository.findByValidationDateBetween(startDate, endDate);
		else if(startDate != null)
		   return repository.findByValidationDateGreaterThanOrValidationDate(startDate, startDate);
		else if(endDate != null)
		   return repository.findByValidationDateLessThanOrValidationDate(endDate, endDate);
		else return repository.findAll();
	}

}

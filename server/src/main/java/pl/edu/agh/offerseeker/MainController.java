package pl.edu.agh.offerseeker;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import pl.edu.agh.offerseeker.domain.Offer;
import pl.edu.agh.offerseeker.model.OfferEvaluation;
import pl.edu.agh.offerseeker.service.GumtreePageProcessor;
import pl.edu.agh.offerseeker.service.IPageProcessor;
import pl.edu.agh.offerseeker.service.OffersValidationService;

/**
 * 
 * @author Szymon Konicki
 *
 */
@Controller
@RequestMapping("/api")
public class MainController {

	@Autowired
	 private OffersValidationService offersValidationService;

	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	@ResponseBody
	public Offer getOffers(@RequestParam("keywords") String keywords,
			Pageable pageable) {
		Offer offer = new Offer(UUID.randomUUID(), "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?","Tytuł" );
		return offer;
	}

	@RequestMapping(value = "/offers/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void getOfferValidation(@RequestParam("isOffer") boolean isOffer, @PathVariable("id") UUID offerID) {
		offersValidationService.addValidationResponse(offerID, isOffer);
	}
}

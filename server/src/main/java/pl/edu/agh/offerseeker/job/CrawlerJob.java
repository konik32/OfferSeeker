package pl.edu.agh.offerseeker.job;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.offerseeker.WebSpider;
import pl.edu.agh.offerseeker.commons.model.PossibleOfferLink;
import pl.edu.agh.offerseeker.exceptions.CrawlerInternalException;
import pl.edu.agh.offerseeker.repository.PossibleOfferLinkRepository;

@Component
public class CrawlerJob {
	
	@Autowired
	private WebSpider webSpider;
	
	@Value("${offerseeker.crawlerJob.fetchedUrlsCount}")
	private Integer fetchedUrlsCount;
	
	@Autowired
	private PossibleOfferLinkRepository repository;
	
	@Transactional
	public void init(){
		String offerSiteUrl = "http://www.gumtree.pl/";
		try {
			Set<PossibleOfferLink> visitedUrls = webSpider.crawl(new URL(offerSiteUrl), fetchedUrlsCount);
			repository.save(visitedUrls);
		} catch (MalformedURLException | CrawlerInternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

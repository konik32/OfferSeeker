package pl.edu.agh.offerseeker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.edu.agh.offerseeker.WebSpider;
import pl.edu.agh.offerseeker.WebSpider.VisitedUrlsDatabase;
import pl.edu.agh.offerseeker.crawler.WebSpiderImpl;

@Configuration
public class WebSpiderConfig {

	@Autowired
	private VisitedUrlsDatabase visitedUrlsDatabase;

	@Value("${offerseeker.webspider.crawlStorageFolder}")
	private String crawlStorageFolder;
	@Value("${offerseeker.webspider.politenessDelay}")
	private Integer politenessDelay;
	@Value("${offerseeker.webspider.maxDepthOfCrawling}")
	private Integer maxDepthOfCrawling;
	@Value("${offerseeker.webspider.maxPagesToFetch}")
	private Integer maxPagesToFetch;
	@Value("${offerseeker.webspider.numberOfCrawlers}")
	private Integer numberOfCrawlers;

	@Bean
	public WebSpider webSpider() {
		return new WebSpiderImpl(crawlStorageFolder, politenessDelay, maxDepthOfCrawling, maxPagesToFetch, numberOfCrawlers, visitedUrlsDatabase);
	}
}

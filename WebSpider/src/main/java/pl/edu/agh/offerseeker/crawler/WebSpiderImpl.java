package pl.edu.agh.offerseeker.crawler;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.edu.agh.offerseeker.WebSpider;
import pl.edu.agh.offerseeker.exceptions.CrawlerInternalException;
import pl.edu.agh.offerseeker.model.VisitedUrl;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

public class WebSpiderImpl implements WebSpider {

	private String crawlStorageFolder = "crawldata";
	private int politenessDelay = 200;
	private int maxDepthOfCrawling = -1;
	private int maxPagesToFetch = -1;
	private int numberOfCrawlers = 7;

	@Override
	public Set<VisitedUrl> crawl(URL offerSiteUrl, int fetchedUrlsCount,
			VisitedUrlsDatabase visitedUrlsDatabase)
			throws CrawlerInternalException {
		if (offerSiteUrl.getPath().isEmpty()) {
			throw new IllegalArgumentException(
					"Path of offerSiteUrl may not be empty, at least a slash is required");
		}
		if (fetchedUrlsCount <= 0) {
			throw new IllegalArgumentException(
					"fetchedUrlsCount must be greater than 0");
		}
		if (visitedUrlsDatabase == null) {
			throw new IllegalArgumentException(
					"visitedUrlsDatabase may not be null");
		}

		try {
			return crawlInner(offerSiteUrl, fetchedUrlsCount,
					visitedUrlsDatabase);
		} catch (Exception e) {
			throw new CrawlerInternalException(
					"Error creating CrawlController", e);
		}
	}

	public Set<VisitedUrl> crawlInner(URL offerSiteUrl, int fetchedUrlsCount,
			VisitedUrlsDatabase visitedUrlsDatabase) throws Exception {
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setPolitenessDelay(politenessDelay);
		config.setMaxDepthOfCrawling(maxDepthOfCrawling);
		config.setMaxPagesToFetch(maxPagesToFetch);
		config.setIncludeBinaryContentInCrawling(false);

		// Instantiate the controller for this crawl.
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,
				pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher,
				robotstxtServer);

		// These are the first URLs that are fetched and then the crawler starts
		// following links which are found in these pages.
		controller.addSeed(offerSiteUrl.toString());

		// Set up arguments for the crawlers.
		VisitedUrlsCounter visitedUrlsCounter = new VisitedUrlsCounter(
				fetchedUrlsCount);
		WebURL offerSiteWebUrl = new WebURL();
		offerSiteWebUrl.setURL(offerSiteUrl.toString());
		String domain = offerSiteWebUrl.getDomain();
		
		CrawlerArguments crawlerArguments = new CrawlerArguments(
				visitedUrlsDatabase, visitedUrlsCounter, domain);
		controller.setCustomData(crawlerArguments);

		// Start the crawl.
		controller.start(Crawler.class, numberOfCrawlers);

		// Collect the results.
		Set<VisitedUrl> newUrls = new HashSet<>();
		List<Object> crawlersLocalData = controller.getCrawlersLocalData();
		for (Object localData : crawlersLocalData) {
			Set<VisitedUrl> crawlerUrls = (Set<VisitedUrl>) localData;
			newUrls.addAll(crawlerUrls);
		}

		return newUrls;
	}

}

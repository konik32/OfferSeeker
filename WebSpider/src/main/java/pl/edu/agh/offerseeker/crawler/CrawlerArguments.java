package pl.edu.agh.offerseeker.crawler;

import pl.edu.agh.offerseeker.WebSpider.VisitedUrlsDatabase;

/**
 * Arguments passed to a Crawler.
 * 
 * @author g.kostalkowicz
 * 
 */
public class CrawlerArguments {

	private VisitedUrlsDatabase visitedUrlsDatabase;
	private VisitedUrlsCounter visitedUrlsCounter;
	private String domain;

	public CrawlerArguments(VisitedUrlsDatabase visitedUrlsDatabase,
			VisitedUrlsCounter visitedUrlsCounter, String domain) {
		this.visitedUrlsDatabase = visitedUrlsDatabase;
		this.visitedUrlsCounter = visitedUrlsCounter;
		this.domain = domain;
	}

	public VisitedUrlsDatabase getVisitedUrlsDatabase() {
		return visitedUrlsDatabase;
	}

	public VisitedUrlsCounter getVisitedUrlsCounter() {
		return visitedUrlsCounter;
	}

	public String getDomain() {
		return domain;
	}

}

package pl.edu.agh.offerseeker.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import pl.edu.agh.offerseeker.WebSpider.VisitedUrlsDatabase;
import pl.edu.agh.offerseeker.model.VisitedUrl;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * A web crawler. Each crawler runs on its own thread.
 * 
 * @author g.kostalkowicz
 * 
 */
public class Crawler extends WebCrawler {

	private final static Pattern FILTER_OUT = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|doc|docx|xls|xlsx|ppt|pptx"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	private VisitedUrlsDatabase visitedUrlsDatabase;
	private VisitedUrlsCounter visitedUrlsCounter;
	private String domain;
	private Set<VisitedUrl> newUrls = new HashSet<VisitedUrl>();

	@Override
	public void init(int id, CrawlController crawlController) {
		super.init(id, crawlController);

		CrawlerArguments crawlerArguments = (CrawlerArguments) getMyController()
				.getCustomData();
		this.visitedUrlsDatabase = crawlerArguments.getVisitedUrlsDatabase();
		this.visitedUrlsCounter = crawlerArguments.getVisitedUrlsCounter();
		this.domain = crawlerArguments.getDomain();
	}

	@Override
	public Object getMyLocalData() {
		return newUrls;
	}

	@Override
	public boolean shouldVisit(WebURL url) {
		// Already visited enough URLs?
		if (visitedUrlsCounter.isLimitReached()) {
			return false;
		}

		if (FILTER_OUT.matcher(url.getURL().toLowerCase()).matches()) {
			return false;
		}

		return url.getDomain().matches(domain);
	}

	@Override
	public void visit(Page page) {
		try {
			// Already visited enough URLs?
			if (visitedUrlsCounter.isLimitReached()) {
				if (!myController.isShuttingDown()) {
					myController.shutdown();
				}
				return;
			}

			if (!(page.getParseData() instanceof HtmlParseData)) {
				return;
			}

			VisitedUrl currentUrl = new VisitedUrl(new URL(page.getWebURL()
					.getURL()));

			if (!visitedUrlsDatabase.isAlreadyVisited(currentUrl)) {
				newUrls.add(currentUrl);
				visitedUrlsCounter.increment();

				System.out.println("Visit : " + page.getWebURL().getURL());
			} else {
				System.out.println(" Skip : " + page.getWebURL().getURL());
			}
		} catch (MalformedURLException e) {
			System.out.println("Found an invalid URL during crawling: "
					+ page.getWebURL().getURL());
		}
	}

}
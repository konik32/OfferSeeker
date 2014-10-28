package pl.edu.agh.offerseeker;

import java.net.URL;
import java.util.Set;

import pl.edu.agh.offerseeker.exceptions.CrawlerInternalException;
import pl.edu.agh.offerseeker.model.VisitedUrl;

/**
 * This interface allows to crawl a given offer site searching for new URLs.
 * 
 * Created by Krzysztof Balon on 2014-10-21.
 */
public interface WebSpider {

	/**
	 * This interface provides information about which URLs has been already
	 * visited during some previous crawls.
	 * 
	 * @author g.kostalkowicz
	 * 
	 */
	public interface VisitedUrlsDatabase {
		/**
		 * Checks if given URL has been already visited in some previous crawl.
		 * 
		 * @param url
		 * @return
		 */
		public boolean isAlreadyVisited(VisitedUrl url);
	}

	/**
	 * Crawl the given offer site searching for new URLs which haven't been
	 * visited before.
	 * 
	 * @param offerSiteUrl
	 *            root URL of the offer site, such as
	 *            <code>http://www.gumtree.pl/</code>. If the URL points at the
	 *            root directory of a given domain, it must end with a slash.
	 * @param alreadyVisitedUrls
	 *            all previously visited URLs
	 * @param fetchedUrlsCount
	 *            number of URLs to return. If the offer site can't provide that
	 *            many URLs, less will be returned.
	 * @return a set of URLs in given offer site which haven't been visited
	 *         before, or an empty set if couldn't connect to root URL or
	 *         timeout occurred
	 */
	public Set<VisitedUrl> crawl(URL offerSiteUrl, int fetchedUrlsCount,
			VisitedUrlsDatabase visitedUrlsDatabase)
			throws CrawlerInternalException;

}

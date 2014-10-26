package pl.edu.agh.offerseeker;

import java.net.URL;
import java.util.Set;

import pl.edu.agh.offerseeker.model.VisitedUrl;

/**
 * Created by Krzysztof Balon on 2014-10-21.
 */
public interface WebSpider {

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
	 *            approximate number of URLs to return. The number of returned
	 *            URLs will be generally a bit higher than this number, given
	 *            that there is enough URLs on the offer site. If offer site
	 *            can't provide enough URLs, less URLs than this argument will
	 *            be returned.
	 * @return a set of URLs in given offer site which haven't been visited
	 *         before, or an empty set if an error or timeout occurred
	 */
	public Set<VisitedUrl> crawl(URL offerSiteUrl,
			Set<VisitedUrl> alreadyVisitedUrls, int fetchedUrlsCount)
			throws Exception;

}

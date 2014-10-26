package pl.edu.agh.offerseeker.model;

import java.net.URL;
import java.util.Set;

/**
 * Represents an offer site such as Gumtree or Ebay.
 * 
 * @author g.kostalkowicz
 * 
 */
public class OfferSite {

	private int id;
	private URL url;
	private Set<Crawl> crawls;

	public OfferSite() {
	}

	/**
	 * Gets the unique ID of the offer site.
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the root URL of the offer site, such as
	 * <code>http://www.gumtree.pl/</code>
	 * 
	 * @return
	 */
	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	/**
	 * Gets the crawls done in the past inside the offer site.
	 * 
	 * @return
	 */
	public Set<Crawl> getCrawls() {
		return crawls;
	}

	public void setCrawls(Set<Crawl> crawls) {
		this.crawls = crawls;
	}

}

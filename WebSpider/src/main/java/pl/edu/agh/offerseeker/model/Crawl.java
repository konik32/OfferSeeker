package pl.edu.agh.offerseeker.model;

import java.util.Date;
import java.util.Set;

import pl.edu.agh.offerseeker.commons.model.PossibleOfferLink;

/**
 * Represents a single process of crawl done for given offer site, which has
 * visited a couple of URLs.
 * 
 * @author g.kostalkowicz
 * 
 */
public class Crawl {

	private int id;
	private Date date;
	private Set<PossibleOfferLink> visitedUrls;

	public Crawl() {
	}

	/**
	 * Gets the unique ID of the crawl.
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
	 * Gets the date of the crawl.
	 * 
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets URLs belonging to the offer site which have been visited during this
	 * crawl.
	 * 
	 * @return
	 */
	public Set<PossibleOfferLink> getVisitedUrls() {
		return visitedUrls;
	}

	public void setVisitedUrls(Set<PossibleOfferLink> visitedUrls) {
		this.visitedUrls = visitedUrls;
	}

}

package pl.edu.agh.offerseeker.model;

import java.util.Date;
import java.util.Set;

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
	private Set<VisitedUrl> visitedUrls;

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
	public Set<VisitedUrl> getVisitedUrls() {
		return visitedUrls;
	}

	public void setVisitedUrls(Set<VisitedUrl> visitedUrls) {
		this.visitedUrls = visitedUrls;
	}

}

package pl.edu.agh.offerseeker.model;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Represents an URL belonging to an offer site which has been visited during a
 * crawl.
 * 
 * @author g.kostalkowicz
 * 
 */
public class VisitedUrl {

	private int id;
	private URL url;

	public VisitedUrl() {
	}

	public VisitedUrl(URL url) {
		this.url = url;
	}
	
	public VisitedUrl(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	/**
	 * Gets the unique ID of the URL.
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
	 * Gets the URL address itself.
	 * 
	 * @return
	 */
	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VisitedUrl other = (VisitedUrl) obj;
		if (id != other.id)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return url.toString();
	}

}

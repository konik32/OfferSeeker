package pl.edu.agh.offerseeker.commons.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity for storing possible offers urls 
 * @author Szymon Konicki
 *
 */

@Entity
@Table(name = "possible_offers_links", indexes = @Index(columnList = "url", unique = true))
public class PossibleOfferLink extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7500640602413731576L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 1024, unique = true)
	private URL url;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date timestamp;

	public PossibleOfferLink() {
		this.timestamp = new Date();
	}

	public PossibleOfferLink(URL url) {
		this.url = url;
	}

	public PossibleOfferLink(String url) throws MalformedURLException {
		this.url = new URL(url);
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
	public String toString() {
		return url.toString();
	}

	@Override
	public Long getId() {
		return id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
//		if (!super.equals(obj))
//			return false;
		if (getClass() != obj.getClass())
			return false;
		PossibleOfferLink other = (PossibleOfferLink) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}

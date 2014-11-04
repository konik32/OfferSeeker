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
 * 
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
	private Date timestamp;

	public PossibleOfferLink() {
	}

	public PossibleOfferLink(URL url) {
		this.url = url;
	}

	public PossibleOfferLink(String url) throws MalformedURLException {
		this.url = new URL(url);
		this.timestamp = new Date();
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

}

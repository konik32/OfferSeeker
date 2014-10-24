package pl.edu.agh.offerseeker.domain;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 
 * @author Szymon Konicki
 *
 */
@Entity
@Table(name = "possible_offer_links")
public class PossibleOfferLink extends AbstractPersistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7083901164704381838L;

	private URL url;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	public PossibleOfferLink(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			// ignore for now
			e.printStackTrace();
		}
		this.timestamp = new Date();
	}

	public PossibleOfferLink() {
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}

package pl.edu.agh.offerseeker.domain;

import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import pl.edu.agh.offerseeker.commons.model.AbstractPersistable;

/**
 * Domain to be crawled for possible offers links like http://gumtree.pl
 * @author Szymon Konicki
 *
 */
@Entity
@Table(name = "domains")
public class Domain extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8648239655806553078L;
	private URL url;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	@Override
	public Long getId() {
		return id;
	}

	public Domain(URL url) {
		super();
		this.url = url;
	}

	public Domain() {
	}
}

package pl.edu.agh.offerseeker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="offers")
public class Offer extends AbstractPersistable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1572099896813035456L;
	
	@Column(length=2048)
	private String description;
	@Column(length=512)
	private String title;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}

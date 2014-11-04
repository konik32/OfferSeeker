package pl.edu.agh.offerseeker.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import pl.edu.agh.offerseeker.commons.model.AbstractPersistable;


/**
 * 
 * @author Szymon Konicki
 *
 */
@Entity
@Table(name = "offers")
public class Offer extends AbstractPersistable<UUID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1572099896813035456L;

	@Id
	private UUID id;

	@Column(length = 2048)
	private String description;
	@Column(length = 512)
	private String title;

	public Offer(UUID id, String description, String title) {
		super();
		this.id = id;
		this.description = description;
		this.title = title;
	}

	public Offer() {

	}

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

	@Override
	public UUID getId() {
		return id;
	}

	private void setId(UUID id) {
		this.id = id;
	}

}

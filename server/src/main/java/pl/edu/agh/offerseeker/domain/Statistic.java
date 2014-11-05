package pl.edu.agh.offerseeker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-10-29.
 */
@Entity
@Table(name = "statistics")
public class Statistic {

	@Id
	private UUID id;

	@Column
	private boolean isOffer;

	@Column
	private Date timestamp;

	public Statistic(UUID id, boolean isOffer) {
		this.id = id;
		this.isOffer = isOffer;
		this.timestamp = new Date();
	}

	public Statistic() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public boolean isOffer() {
		return isOffer;
	}

	public void setOffer(boolean isOffer) {
		this.isOffer = isOffer;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}

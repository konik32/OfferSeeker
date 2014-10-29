package pl.edu.agh.offerseeker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by Mateusz on 2014-10-29.
 */
@Entity
@Table(name = "statistics")
public class Statistics {

	@Id
	private UUID id;

	@Column
	private String userName;

	@Column
	private long numberOfAcceptedOffers = 0;

	@Column
	private long numberOfRejectedOffers = 0;

	public Statistics(String userName) {
		this.userName = userName;
	}

	public Statistics(String userName, long numberOfAcceptedOffers, long numberOfRejectedOffers) {
		this.userName = userName;
		this.numberOfAcceptedOffers = numberOfAcceptedOffers;
		this.numberOfRejectedOffers = numberOfRejectedOffers;
	}

	public void incrementAcceptedOffers() {
		numberOfAcceptedOffers++;
	}

	public void inclementRejectedOffers() {
		numberOfRejectedOffers++;
	}

	public String getUserName() {
		return userName;
	}

	public Long getNumberOfAcceptedOffers() {
		return numberOfAcceptedOffers;
	}

	public Long getNumberOfRejectedOffers() {
		return numberOfRejectedOffers;
	}
}

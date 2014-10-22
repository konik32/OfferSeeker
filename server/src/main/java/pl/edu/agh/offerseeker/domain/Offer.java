package pl.edu.agh.offerseeker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="offers")
@ToString
public class Offer extends AbstractPersistable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1572099896813035456L;
	
	@Column(length=2048)
	private @Getter @Setter String description;
	@Column(length=512)
	private @Getter @Setter String title;

}

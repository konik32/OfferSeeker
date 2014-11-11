package pl.edu.agh.offerseeker.job;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.batch.item.ItemWriter;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.offerseeker.domain.Offer;
/**
 * Implementation of {@link ItemWriter} to persist {@link Offer}s list
 * @author Szymon Konicki
 *
 */
public class OfferWritter implements ItemWriter<Offer> {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void write(List<? extends Offer> items) throws Exception {
		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();
		int i = 0;
		for(Offer offer: items){
			if(offer == null) continue;
			session.save(offer);
			if (i++ % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
		tx.commit();
		session.close();

	}

}

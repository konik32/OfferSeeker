package pl.edu.agh.offerseeker.run;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.offerseeker.domain.Domain;
import pl.edu.agh.offerseeker.repository.DomainRepository;
/**
 * Populates domains in database
 * @author Szymon Konicki
 *
 */
@Component
public class DatabaseDomainPopulator implements CommandLineRunner {

	private static final List<String> domains;

	static {
		List<String> temp = new ArrayList<String>(2);
		temp.add("http://www.gumtree.pl/");
		temp.add("http://olx.pl/");
		domains = Collections.unmodifiableList(temp);
	}
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DomainRepository repository;

	@Override
	@Transactional
	public void run(String... arg0) throws Exception {
		for (String urlStr : domains) {
			URL url = new URL(urlStr);
			if (repository.findByUrl(url) == null) {
				Session session = em.unwrap(Session.class);
				Transaction tx = session.beginTransaction();
				session.persist(new Domain(url));
				tx.commit();
				session.close();
			}

		}
	}

}

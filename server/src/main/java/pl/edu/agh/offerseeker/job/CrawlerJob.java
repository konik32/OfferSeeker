package pl.edu.agh.offerseeker.job;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.offerseeker.WebSpider;
import pl.edu.agh.offerseeker.commons.model.PossibleOfferLink;
import pl.edu.agh.offerseeker.domain.Domain;
import pl.edu.agh.offerseeker.exceptions.CrawlerInternalException;
import pl.edu.agh.offerseeker.repository.DomainRepository;
import pl.edu.agh.offerseeker.repository.PossibleOfferLinkRepository;

/**
 * Responsible for crawling domains and search for possible offers links
 * 
 * @author Szymon Konicki
 *
 */
@Component
public class CrawlerJob {

	@Autowired
	private WebSpider webSpider;

	@Value("${offerseeker.crawlerJob.fetchedUrlsCount}")
	private Integer fetchedUrlsCount;

	@Autowired
	private DomainRepository domainRepository;

	@Autowired
	private PossibleOfferLinkRepository repository;

	@PersistenceContext
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	public void init() {
		Iterable<Domain> domains = domainRepository.findAll();
		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();
		try {
			for (Domain domain : domains) {
				Set<PossibleOfferLink> visitedUrls;
				visitedUrls = webSpider.crawl(domain.getUrl(), fetchedUrlsCount);
				Iterator<PossibleOfferLink> it = visitedUrls.iterator();
				int i = 0;
				while (it.hasNext()) {
					PossibleOfferLink link = it.next();
					if (repository.findByUrl(link.getUrl()) != null)
						continue;
					link.setTimestamp(new Date());
					session.save(link);
					if (i++ % 20 == 0) {
						session.flush();
						session.clear();
					}
				}
			}
			tx.commit();
			session.close();
		} catch (CrawlerInternalException e) {
			e.printStackTrace();
		}
	}

}

package pl.edu.agh.offerseeker.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaDelete;

import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.bridge.builtin.DateBridge;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.offerseeker.commons.model.PossibleOfferLink;
import pl.edu.agh.offerseeker.domain.Offer;
import pl.edu.agh.offerseeker.repository.OfferRepository;
import pl.edu.agh.offerseeker.repository.PossibleOfferLinkRepository;

/**
 * 
 * @author Szymon Konicki
 *
 */
@Service
public class OffersFullTextSearchService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private PossibleOfferLinkRepository offerLinkRepository;

	/**
	 * Find offers based on keywords and above timestamp sorted by relevance.
	 * Returns offers lists in pages.
	 * 
	 * @param keywords
	 * @param pageable
	 * @return
	 */
	@Transactional
	public Page<Offer> findByKeywords(String keywords, Pageable pageable, Date timestamp) {
		FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
		QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(Offer.class).get();

		org.apache.lucene.search.Query finalQuery;
		org.apache.lucene.search.Query keywordQuery = qb.keyword().onFields("description").matching(keywords).createQuery();
		if (timestamp != null) {
			finalQuery = qb.bool().must(keywordQuery).must(qb.range().onField("timestamp").above(timestamp).createQuery()).createQuery();
		} else {
			finalQuery = keywordQuery;
		}

		FullTextQuery hibQuery = ftem.createFullTextQuery(finalQuery, Offer.class);

		int total = hibQuery.getResultSize();

		hibQuery.setMaxResults(pageable.getPageSize());
		hibQuery.setFirstResult(pageable.getOffset());

		// execute search
		List<Offer> result = hibQuery.getResultList();

		return new PageImpl<>(result, pageable, total);
	}

	@Transactional
	public void deleteSimilar(UUID offerId) {
		Offer offer = offerRepository.findOne(offerId);
		FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
		QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(Offer.class).get();

		org.apache.lucene.search.Query finalQuery = qb.phrase().onField("description").sentence(offer.getDescription()).createQuery();
		FullTextQuery hibQuery = ftem.createFullTextQuery(finalQuery, Offer.class);
		List<Offer> result = hibQuery.getResultList();
		for (Offer o : result) {
			PossibleOfferLink offerLink = offerLinkRepository.findByUrl(o.getUrl());
			if(offerLink!= null)
				offerLinkRepository.delete(offerLink);
			ftem.purge(Offer.class, o);
			offerRepository.delete(o);
		}
		ftem.getSearchFactory().optimize();
	}
}

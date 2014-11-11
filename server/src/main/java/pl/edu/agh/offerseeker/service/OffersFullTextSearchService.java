package pl.edu.agh.offerseeker.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.offerseeker.domain.Offer;

/**
 * 
 * @author Szymon Konicki
 *
 */
@Service
public class OffersFullTextSearchService {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Find offers based on keywords sorted by relevance. Returns offers lists
	 * in pages.
	 * 
	 * @param keywords
	 * @param pageable
	 * @return
	 */
	@Transactional
	public Page<Offer> findByKeywords(String keywords, Pageable pageable) {
		FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
		QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(Offer.class).get();

		org.apache.lucene.search.Query query = qb.keyword().onFields("description").matching(keywords).createQuery();

		FullTextQuery hibQuery = ftem.createFullTextQuery(query, Offer.class);

		int total = hibQuery.getResultSize();

		hibQuery.setMaxResults(pageable.getPageSize());
		hibQuery.setFirstResult(pageable.getOffset());

		// execute search
		List<Offer> result = hibQuery.getResultList();

		return new PageImpl<>(result, pageable, total);
	}
}

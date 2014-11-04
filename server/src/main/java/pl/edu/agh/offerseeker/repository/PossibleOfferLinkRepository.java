package pl.edu.agh.offerseeker.repository;

import java.net.URL;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.edu.agh.offerseeker.commons.model.PossibleOfferLink;

/**
 * 
 * @author Szymon Konicki
 *
 */
@Repository
public interface PossibleOfferLinkRepository extends PagingAndSortingRepository<PossibleOfferLink, Long> {

	Page<PossibleOfferLink> findByTimestampAfter(@Param("timestamp")Date timestamp, Pageable pageable);
	PossibleOfferLink findByUrl(@Param("url") URL url);
}

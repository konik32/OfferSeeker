package pl.edu.agh.offerseeker.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.edu.agh.offerseeker.domain.PossibleOfferLink;
/**
 * 
 * @author Szymon Konicki
 *
 */
@Repository
public interface PossibleOfferLinkRepository extends PagingAndSortingRepository<PossibleOfferLink, Long> {

	public Page<PossibleOfferLink> findByTimestampAfter(@Param("timestamp")Date timestamp, Pageable pageable);
}

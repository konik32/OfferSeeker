package pl.edu.agh.offerseeker.repository;

import java.net.URL;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.edu.agh.offerseeker.domain.Domain;
import pl.edu.agh.offerseeker.domain.Offer;
/**
 * 
 * Repository providing CRUD and paging operations for {@link Offer}
 * @author Szymon Konicki
 *
 */
@Repository
public interface OfferRepository extends PagingAndSortingRepository<Offer, UUID> {
	Offer findByUrl(@Param("url") URL url);
}

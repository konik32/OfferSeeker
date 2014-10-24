package pl.edu.agh.offerseeker.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import pl.edu.agh.offerseeker.domain.Offer;
/**
 * 
 * @author Szymon Konicki
 *
 */
@Repository
public interface OfferRepository extends PagingAndSortingRepository<Offer, UUID> {

}

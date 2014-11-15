package pl.edu.agh.offerseeker.repository;

import java.net.URL;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.edu.agh.offerseeker.domain.Domain;
import pl.edu.agh.offerseeker.domain.Offer;

/**
 * Repository providing CRUD operations for {@link Domain}
 * 
 * @author Szymon Konicki
 *
 */
@Repository
public interface DomainRepository extends CrudRepository<Domain, UUID> {

	Domain findByUrl(@Param("url") URL url);
}

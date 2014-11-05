package pl.edu.agh.offerseeker.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.offerseeker.domain.Statistic;

/**
 * Created by Mateusz on 2014-11-04.
 */
@Repository
public interface StatisticRepository extends PagingAndSortingRepository<Statistic, Long> {
	Long countByIsOffer(@Param("isOffer") boolean isOffer);
}


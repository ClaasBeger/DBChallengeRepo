package shorteningservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shorteningservices.entity.CallStatistics;
import shorteningservices.entity.URL;

@Repository
public interface StatisticsRepository extends JpaRepository<CallStatistics,Integer>  {

}

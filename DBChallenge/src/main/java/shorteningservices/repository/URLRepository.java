package shorteningservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import shorteningservices.entity.URL;

@Repository
public interface URLRepository extends JpaRepository<URL,Integer> {

	@Query("SELECT u.original FROM URL u WHERE u.alias=?1")
	String findOriginalByAlias(String alias);

}

package shorteningservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shorteningservices.entity.URL;

@Repository
public interface URLRepository extends JpaRepository<URL,Integer> {

	@Query("SELECT u.original FROM URL u WHERE u.alias=?1")
	String findOriginalByAlias(String alias);
	
	@Transactional
	@Modifying
	@Query("UPDATE URL u SET u.alias=?1 WHERE u.original=?2")
	Integer replaceAlias(String alias, String original);

}

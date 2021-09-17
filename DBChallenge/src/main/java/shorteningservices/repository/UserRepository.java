package shorteningservices.repository;

import java.util.List;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shorteningservices.entity.URL;
import shorteningservices.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("SELECT u FROM User u WHERE u.username=?1")
	Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM URL u WHERE u.owner.ID=?1")
	Optional<Iterable<URL>> findAllURLs(Integer ownerID);
	
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.username=?2 WHERE u.ID=?1")
	Integer replaceUsername(Integer userID, String newUsername);
	
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.password=?2 WHERE u.ID=?1")
	Integer replacePassword(Integer userID, String newPassword);

}

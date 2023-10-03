/**
 * 
 */
package no.hvl.dat152.rest.ws.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import no.hvl.dat152.rest.ws.model.User;

/**
 * 
 */
public interface UserRepository extends CrudRepository<User, Long> {
	@Transactional
	@Modifying
	@Query("Delete From User Where id = :id")
	void deleteUser(@Param("id") long id); 
	
	@Transactional
	@Modifying
	@Query("Update User Set firstname=:firstname, lastname=:lastname Where id = :id")
	void updateUser(@Param("id") long id, @Param("firstname") String firstname, @Param("lastname") String lastname); 
}

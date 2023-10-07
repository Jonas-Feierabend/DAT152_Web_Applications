/**
 * 
 */
package no.hvl.dat152.rest.ws.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.model.Order;


/**
 * 
 */
public interface UserRepository extends CrudRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	Boolean existsByEmail(String email);
	
	
	@Transactional
	@Modifying
	@Query("Delete From User Where id = :id")
	void deleteUser(@Param("id") long id); 
	
	@Transactional
	@Modifying
	@Query("Update User Set id =:new_id ,firstname=:firstname, lastname=:lastname, email=:email, orders=:orders Where id = :id")
	void updateUser(@Param("new_id") long new_id, @Param("firstname") String firstname, @Param("lastname") String lastname,@Param("email") String email, @Param("orders") Set<Order> orders,   @Param("id") long id); 
}

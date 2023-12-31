/**
 * 
 */
package no.hvl.dat152.rest.ws.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import no.hvl.dat152.rest.ws.model.Order;

/**
 * 
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query(value = "SELECT user_id FROM order WHERE id = :id", nativeQuery=true)
	Long findUserID(@Param("id") Long id);
	
	@Query(value = "SELECT id, isbn, expiry FROM order WHERE expiry <= :expiry limit :limit offset :offset ", nativeQuery=true)
	List<Order> findOrderByExpiry(
			@Param("expiry") LocalDate expiry,
			@Param("limit") int limit,
			@Param("offset") int offset);
	
	Page<Order> findByExpiryBefore(LocalDate expiry, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("Delete From Order Where id=:id")
	void deleteOrder(@Param("id") Long id ); 
	
	/* 
	@Transactional
	@Modifying
	@Query("Update Order Set")
	void updateOrder(@Param("id") Long id, @Param("order") Order order ); */ 
}

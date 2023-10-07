/**
 * 
 */
package no.hvl.dat152.rest.ws.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import no.hvl.dat152.rest.ws.model.Order;

/**
 * 
 */
public interface OrderRepository extends CrudRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {

	
	@Query(value = "SELECT user_id FROM orders WHERE id = :id", nativeQuery=true)
	Long findUserID(@Param("id") Long id);
	
	@Query(value = "SELECT id, isbn, expiry FROM orders WHERE expiry <= :expiry limit :limit offset :offset ", nativeQuery=true)
	List<Order> findOrderByExpiry(
			@Param("expiry") LocalDate expiry,
			@Param("limit") int limit,
			@Param("offset") int offset);
	
	Page<Order> findByExpiryBefore(LocalDate expiry, Pageable pageable);
	
	List<Order> findAll(Sort sort);
	
	Page<Order> findAll(Pageable pageable);
	
	@Query(value = "SELECT user_email FROM orders WHERE id = :id", nativeQuery=true)
	String findEmailByOrderId(Long id);
	

	
	@Transactional
	@Modifying
	@Query("Delete From Order Where id=:id")
	int deleteOrder(@Param("id") Long id ); 
	
	
	@Transactional
	@Modifying
	@Query("Update Order Set id=:new_id, isbn=:isbn, expiry=:expiry Where id =:id")
	int updateOrder(@Param("new_id") Long new_id,  @Param("id") Long id, @Param("isbn") String isbn,@Param("expiry") LocalDate expiry);
	
}

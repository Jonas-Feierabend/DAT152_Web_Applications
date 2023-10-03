/**
 * 
 */
package no.hvl.dat152.rest.ws.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
/**
 * @author tdoy
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {
	
	@Transactional
	@Modifying
	@Query("Delete From Author Where id = :id")
	void deleteByISBN(@Param("id") String id); 
	
	
	
	@Transactional
	@Modifying
	@Query("Update Author Set firstname = :firstname, lastname = :lastname, books = :books Where id = :id ")
	void updateAuthorById(@Param("id") Long id,@Param("firstname") String firstname,@Param("lastname") String lastname, @Param("books") Set<Book> books); 
	// , lastname = :lastname, books = :books  
}

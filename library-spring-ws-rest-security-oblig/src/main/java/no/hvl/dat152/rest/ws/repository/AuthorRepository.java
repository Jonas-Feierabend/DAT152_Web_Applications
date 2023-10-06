/**
 * 
 */
package no.hvl.dat152.rest.ws.repository;


import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;

/**
 * @author tdoy
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Author a WHERE a.authorId = :id")
	void deleteByISBN(@Param("id") String id); 
	
	
	
	@Transactional
	@Modifying
	@Query("UPDATE Author a SET a.firstname = :firstname, a.lastname = :lastname, a.books = :books WHERE a.authorId = :id")
	void updateAuthorById(@Param("id") Long id, @Param("firstname") String firstname, @Param("lastname") String lastname, @Param("books") Set<Book> books);
}

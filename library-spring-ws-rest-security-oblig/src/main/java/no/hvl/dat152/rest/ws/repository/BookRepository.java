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
 * 
 */
public interface BookRepository extends CrudRepository<Book, Long> {
	
	@Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
	Book findBookByISBN(@Param("isbn") String isbn);

	@Transactional
	@Modifying
	@Query("Delete From Book Where isbn = :isbn")
	void deleteByISBN(@Param("isbn") String isbn); 
	
	@Transactional
	@Modifying
	@Query("Update Book Set isbn = :isbn, title = :title, authors = :authors  Where id = :id ")
	void updateBookById(@Param("id") Long id,@Param("isbn") String isbn, @Param("title") String title, @Param("authors") Set<Author> authors); 

}

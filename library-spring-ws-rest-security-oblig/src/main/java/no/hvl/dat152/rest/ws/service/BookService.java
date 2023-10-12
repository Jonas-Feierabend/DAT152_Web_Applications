/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import no.hvl.dat152.rest.ws.exceptions.BookNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.UpdateBookFailedException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.repository.BookRepository;

/**
 * @author tdoy
 */
@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	

	
	public Book saveBook(Book book) {
		
		return bookRepository.save(book);
		
	}
	
	@Transactional
	public Book updateBook(String isbn, Book book) throws BookNotFoundException{
		try {
			//first get id to update 
			//long id = bookRepository.findBookByISBN(isbn).getId();
			//update
			//bookRepository.updateBookById(id, book.getIsbn(), book.getTitle(), book.getAuthors());
			
			Book book_obj = bookRepository.findBookByISBN(isbn); 
			book_obj.setAuthors(book.getAuthors());
			book_obj.setId(book.getId());
			book_obj.setIsbn(book.getIsbn());
			book_obj.setTitle(book.getTitle());
			
			return this.saveBook(book_obj);  
		}catch(Exception e) {
			throw new BookNotFoundException("Book  not found!");
		}
	}
		
		
	public List<Book> findAll(){
		
		return (List<Book>) bookRepository.findAll();
		
	}
	
	public List<Book> findAllPaginate(Pageable page){
		
		Page<Book> books = bookRepository.findAll(page);
		
		return books.getContent();
	}
	
	public Book findById(long id) throws BookNotFoundException{
		
		Book book = findBookById(id);
		
		return book;
	}
	
	public Book findByISBN(String isbn) throws BookNotFoundException {
		
		Book book = bookRepository.findByIsbn(isbn)
				.orElseThrow(() -> new BookNotFoundException("Book with isbn = "+isbn+" not found!"));
		
		return book;
	}
	
	public Set<Author> findAuthorsByBookISBN(String isbn) throws BookNotFoundException{
		
		Set <Author> authors = bookRepository.findAuthorsByBookIsbn(isbn); 
		
		return authors; 
		
	}
	@Transactional
	public int deleteByISBN(String isbn) throws BookNotFoundException{
		bookRepository.deleteByISBN(isbn);
		try {
			 bookRepository.deleteByISBN(isbn);
			 return 1; 
		}catch(Exception e) {
			
			System.out.println(e); 
			throw new BookNotFoundException("Book with isbn = "+isbn+" not found!");
		}
		
		
	}
	
	private Book findBookById(long id) throws BookNotFoundException {

        return bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book with id = "+id+" not found!"));
	}

	public List<Book> findBookByAuthorId(long id) throws BookNotFoundException {
		List<Book> list;
		try{
			list = bookRepository.findBooksByAuthorId(id);
		} catch(Exception e) {
			System.out.println(e);
			throw new BookNotFoundException("There are no Books found for Author with id = " + id);
		}
		return list;
	}
	

}

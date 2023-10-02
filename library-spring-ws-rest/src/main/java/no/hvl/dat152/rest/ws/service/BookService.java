/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import no.hvl.dat152.rest.ws.exceptions.BookNotFoundException;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.repository.BookRepository;

/**
 * 
 */
@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	
	public Book saveBook(Book book) {
		
		return bookRepository.save(book);
		
	}
	
	public List<Book> findAll(){
		
		return (List<Book>) bookRepository.findAll();
		
	}
	
	public Book findByISBN(String isbn) throws BookNotFoundException {
		
		Book book = null;
		try {
			book = bookRepository.findBookByISBN(isbn);
		}catch(Exception e) {
			throw new BookNotFoundException("Book with isbn = "+isbn+" not found!  "+e);
		}
		
		return book;
	}
	
	
	public int deleteByISBN(String isbn) throws BookNotFoundException{
		
		try {
			 bookRepository.deleteByISBN(isbn);
			 return 1; 
		}catch(Exception e) {
			
			System.out.println(e); 
			throw new BookNotFoundException("Book with isbn = "+isbn+" not found!");
		}
		
		
	}
	
	@Transactional
	public int updateBook(String isbn, Book book) throws BookNotFoundException{
		try {
			//first get id to update 
			long id = bookRepository.findBookByISBN(isbn).getId();
			//update
			bookRepository.updateBookById(id, book.getIsbn(), book.getTitle(), book.getAuthors());
			 return 1; 
		}catch(Exception e) {
			throw new BookNotFoundException("Book  not found!");
		}
		
	}

}

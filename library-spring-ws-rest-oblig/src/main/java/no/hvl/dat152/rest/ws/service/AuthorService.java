/**
 * 
 */
package no.hvl.dat152.rest.ws.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import no.hvl.dat152.rest.ws.exceptions.BookNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.repository.AuthorRepository;
import no.hvl.dat152.rest.ws.repository.BookRepository;

/**
 * 
 */
@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
	
	public Author findById(long id) {
		
		return authorRepository.findById(id).get();
	}
	
	public List<Author> getAllAuthors(){
		return (List<Author>) authorRepository.findAll();
		
	}
	
	
	public Author saveAuthor(Author author) {
		
		return authorRepository.save(author);
		
	}
	
	
	
	
	
	public int deleteById(long id) throws BookNotFoundException{
		
		try {
			 authorRepository.deleteById(id); 
			 return 1; 
		}catch(Exception e) {
			
			System.out.println(e); 
			throw new BookNotFoundException("author with id = "+id+" not found!");
		}
		
		
	}
	
	@Transactional
	public Author updateAuthor(Long id, Author author) throws BookNotFoundException{
		try {
	
			Author author_obj = this.findById(id); 
			author_obj.setAuthorId(author.getAuthorId());
			author_obj.setBooks(author.getBooks());
			author_obj.setFirstname(author.getFirstname());
			author_obj.setLastname(author.getLastname());
			return this.saveAuthor(author_obj);   
		}catch(Exception e) {
			System.out.println(e);
			//throw new BookNotFoundException("author  not found!");
			return null;  
		}
		
	}

}

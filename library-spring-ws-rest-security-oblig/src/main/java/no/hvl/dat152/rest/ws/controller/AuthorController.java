/**
 * 
 */
package no.hvl.dat152.rest.ws.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.BookNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.service.AuthorService;

/**
 * 
 */
@RestController
@RequestMapping("/elibrary/api/v1")
public class AuthorController {

	@Autowired
	private AuthorService authorService;
	
	@GetMapping("/authors/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Author> getAuthor(@PathVariable("id") Long id) throws AuthorNotFoundException {
		
		Author author = authorService.findById(id);
		
		return new ResponseEntity<>(author, HttpStatus.OK);		
	}
	@GetMapping("/authors")
	public ResponseEntity<Object> getAllAuthors(){
		
		List<Author> authors = authorService.getAllAuthors();
		
		if(authors.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<>(authors, HttpStatus.OK);		
	}
	

	
	@PostMapping("/authors")
	public ResponseEntity<Author> createAuthor(@RequestBody Author author){
		
		Author author_obj = authorService.saveAuthor(author); 
		
		return new ResponseEntity<>(author_obj, HttpStatus.CREATED);
	}

	@DeleteMapping("/authors/{id}")
	public ResponseEntity<Object> deleteAuthor(@PathVariable("id") Long id) throws BookNotFoundException{
		
		authorService.deleteById(id); 
		return this.getAllAuthors();  
	}
	
	
	@PutMapping("/authors/{id}")
	public ResponseEntity<Object> updateAuthor(@PathVariable("id") Long id, @RequestBody Author a){
		try {
			int author = authorService.updateAuthor(id, a); 
		} catch (BookNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
 
		Object nbook = null;
		return new ResponseEntity<>(nbook, HttpStatus.CREATED);
	}


}

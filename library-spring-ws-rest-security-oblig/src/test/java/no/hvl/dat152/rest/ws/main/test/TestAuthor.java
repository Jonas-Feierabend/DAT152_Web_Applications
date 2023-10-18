package no.hvl.dat152.rest.ws.main.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.BookNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.service.AuthorService;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TestAuthor {

	@Autowired
	private AuthorService authorService;

	private String API_ROOT = "http://localhost:8090/elibrary/api/v1";


	private String ADMIN_TOKEN ="eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJiZXJpdEBlbWFpbC5jb20iLCJpc3MiOiJEQVQxNTItTGVjdHVyZXJAVERPWSIsImZpcnN0bmFtZSI6IkJlcml0IiwibGFzdG5hbWUiOiJKw7hyZ2Vuc2VuIiwicm9sZXMiOlsiU1VQRVJfQURNSU4iLCJBRE1JTiIsIlVTRVIiXSwiaWF0IjoxNjk3NjE3NDQ4LCJleHAiOjE2OTgwNDk0NDh9.eFyKBpMeOO6omcQ3AAREnb7Vf8iXgxZTXfo8cKj4IAC5PzOl2Ub0O1PC1HixZ5sLAJ1QDl1buskvhH2Y0ISINgwLHFZTkq037WJL5KDh38N0uNp6kxQOLZdX5xXYO23XKFprRuOnRHEO8nx8jwRFUL7giZFDa8FdLHXh9CqPAjNcaBuFnFUYRv4t3BxoSjYykna-fA57o-IZ8VxOe5kGH2hRLiKh-U_7mysSq27xp9AT4MRjDHH1BuZHA7UwOUXQxpVSbh6U1Wn3WG4qmFUCRv-CB5TVucra6M3j8e0ahRBK_N5wICv_zh3Ue9jt2eBouDofDPs6bKDMB7WgHmVHeA";

	@Value("${user.token}")
	private String USER_TOKEN;
	
	@DisplayName("JUnit test for @GetMapping(/authors) endpoint")
	@Test
	public void getAllAuthors_thenOK() {
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ ADMIN_TOKEN)
				.get(API_ROOT+"/authors");
		
		System.out.println("Bearer "+ ADMIN_TOKEN); 
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.jsonPath().getList("authorId").size() > 0);
	}
	
	@DisplayName("JUnit test for @GetMapping(/authors/{id}) endpoint")
	@Test
	public void getAuthorById_thenOK() throws AuthorNotFoundException {

	    Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ ADMIN_TOKEN)
	    		.get(API_ROOT+"/authors/1");
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertEquals("1", response.jsonPath().get("authorId").toString());

	}
	
	@DisplayName("JUnit test for @PostMapping(/authors) endpoint")
	@Test
	public void createAuthor_thenOK() throws AuthorNotFoundException {
		Author author = new Author("Test", "Author");
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ ADMIN_TOKEN)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(author)
				.post(API_ROOT+"/authors");
	    
	    assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
	    assertEquals("Test", response.jsonPath().get("firstname"));
	}
	
	@DisplayName("JUnit test for @GetMapping(/authors/{id}/books) endpoint")
	@Test
	public void getBooksOfAuthor_thenOK() throws AuthorNotFoundException, BookNotFoundException {
		
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ ADMIN_TOKEN)
				.get(API_ROOT+"/authors/1/books");
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertTrue(response.jsonPath().getList("id").size() > 0);
	}
	
	@DisplayName("JUnit test for @PutMapping(/authors/{id}) endpoint")
	@Test
	public void updateAuthor_thenOK() throws AuthorNotFoundException, BookNotFoundException {
		Author author = new Author("Test2", "Author");
		author = authorService.saveAuthor(author);
		
		Author upd_author = new Author("Test22", "Author_2");
		upd_author.setAuthorId(author.getAuthorId());
		
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ ADMIN_TOKEN)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(upd_author)
				.put(API_ROOT+"/authors/{id}", upd_author.getAuthorId());
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertEquals("Test22", response.jsonPath().get("firstname"));
	    
	}
	
	@DisplayName("JUnit test for @PutMapping(/authors/{id}) endpoint")
	@Test
	public void updateAuthor_USER_ROLE_thenOK() throws AuthorNotFoundException, BookNotFoundException {
		Author author = new Author("Test2", "Author");
		author = authorService.saveAuthor(author);
		
		Author upd_author = new Author("Test22", "Author_2");
		upd_author.setAuthorId(author.getAuthorId());
		
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ USER_TOKEN)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(upd_author)
				.put(API_ROOT+"/authors/{id}", upd_author.getAuthorId());
	    
	    assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
	    
	}


}

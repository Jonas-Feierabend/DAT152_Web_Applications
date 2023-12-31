package no.hvl.dat152.rest.ws.main.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TestUser {

	
	private static final String API_ROOT = "http://localhost:8090/elibrary/api/v1";	
	
	private String ADMIN_TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJiZXJpdEBlbWFpbC5jb20iLCJpc3MiOiJEQVQxNTItTGVjdHVyZXJAVERPWSIsImZpcnN0bmFtZSI6IkJlcml0IiwibGFzdG5hbWUiOiJKw7hyZ2Vuc2VuIiwicm9sZXMiOlsiQURNSU4iLCJVU0VSIiwiU1VQRVJfQURNSU4iXSwiaWF0IjoxNjk2NDk2OTY4LCJleHAiOjE2OTY5Mjg5Njh9.iT5NQLJrm3adqM5s1mWSg1rllyP-Alm9_SIldH1ppTz--zpT3xolQoOrOwf21whE4x8WCBTp9vWm-VU-C-5HYH4ZE7RKBN2M-ZVcAqer2QzMiibmrdlWbKwR4Xdtu-5Zsr-aG23jBlMcaASYjG7AJVbE9bsKA4c4-H9Gg0kHTfokDbaugosiPZCI4-MZR4RzF728KOuqsS6_J6hLs6RLhhJkmqaxku8oGRusEyWAgOKmwyDAQHDy3cj-oTKNFLy2aoD13Huw4is_2iQdUsjt7guGUKFdDsRVpT0wHw1B3mt5-GeCv9PwO17Hy33oUmyTraKuDg_GQC1J57nHEWY7lA";
	
	private String USER_TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJyb2JlcnRAZW1haWwuY29tIiwiaXNzIjoiREFUMTUyLUxlY3R1cmVyQFRET"
			+ "1kiLCJmaXJzdG5hbWUiOiJSb2JlcnQiLCJsYXN0bmFtZSI6IklzYWFjIiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE2OTYyNTUwNjgsImV4cC"
			+ "I6MTY5NjM0MTQ2OH0.G1AdRErKUD06FCWZoUJ5RvlbVSFx8eIudf7T3tDamEYgfliPhSFzrBONCHBReUITpaRe5LccsV-IExgzR0UvwkbKff8B"
			+ "vHR6MukTtQkwRZryr7dklEV3SyMQFGi5rf8UKvchyFbprdWtIvpQWXTfhQM7wTyHiD3z7429dht3E0bJgRg8RzKqNlNBJD7YJLfdcS6j7gjPcu"
			+ "n2u8TJm73jSIXaW5zib_mwf5ovg0pH4bOh67pmSLFLVGnLEM4CkWvD8SLwGtD8nS5cvW9yEitgeqLhXDffCdQp0pYE17ddZ73DBQ2NvI0uOu5xe"
			+ "RG4dXIltWb3SdEiwJ3kV8-SKfaDNA";
	
	@DisplayName("JUnit test for @GetMapping(/users) endpoint")
	@Test
	public void getAllUsers_thenOK() {
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ ADMIN_TOKEN)
				.get(API_ROOT+"/users");
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.jsonPath().getList("userid").size() > 0);
	}
	
	@DisplayName("JUnit test for @GetMapping(/users) endpoint")
	@Test
	public void getAllUsers_USER_ROLE_thenOK() {
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ USER_TOKEN)
				.get(API_ROOT+"/users");
		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
	}
	
	@DisplayName("JUnit test for @GetMapping(/users/{id}) endpoint")
	@Test
	public void getUserById_thenOK() {

	    Response response = RestAssured
	    		.given().header("Authorization", "Bearer "+ ADMIN_TOKEN)
	    		.get(API_ROOT+"/users/1");
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertEquals("1", response.jsonPath().get("userid").toString());

	}
	
	@DisplayName("JUnit test for @GetMapping(/users/{id}/orders) endpoint")
	@Test
	public void getOrdersOfUser_thenOK() {
		
		Response response = RestAssured
				.given().header("Authorization", "Bearer "+ ADMIN_TOKEN)
				.get(API_ROOT+"/users/1/orders");
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertTrue(response.jsonPath().getList("isbn").size() > 0);
	}

	@DisplayName("JUnit test for @PostMapping(/users/{id}/orders) endpoint")
	@Test
	public void createOrderForUser_thenOK() throws UserNotFoundException {

		// new order
		Order order1 = new Order("qabfde1230", LocalDate.now().plusWeeks(2));	
		
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ ADMIN_TOKEN)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(order1)
				.post(API_ROOT+"/users/{id}/orders", "2");

	    assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
	    assertTrue(response.jsonPath().getList("orders").get(0).toString().contains("qabfde1230"));

	}
	
	@DisplayName("JUnit test for @DeleteMapping(/users/{id}) endpoint")
	@Test
	public void deleteUserById_thenOK() {

	    Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ ADMIN_TOKEN)
				.delete(API_ROOT+"/users/1");
	    
	    // attempt to access resource again
	    Response resp = RestAssured.given()
	    		.header("Authorization", "Bearer "+ ADMIN_TOKEN)
	    		.get(API_ROOT+"/users/1");
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resp.getStatusCode());

	}

}

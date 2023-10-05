package no.hvl.dat152.rest.ws.main.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import no.hvl.dat152.rest.ws.exceptions.OrderNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TestOrder {

	private String API_ROOT = "http://localhost:8090/elibrary/api/v1";
	
	@Value("${super.admin.token}") 
	private String SUPER_ADMIN_TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJiZXJpdEBlbWFpbC5jb20iLCJpc3MiOiJEQVQxNTItTGVjdHVyZXJAVERPWSIsImZpcnN0bmFtZSI6IkJlcml0IiwibGFzdG5hbWUiOiJKw7hyZ2Vuc2VuIiwicm9sZXMiOlsiQURNSU4iLCJVU0VSIiwiU1VQRVJfQURNSU4iXSwiaWF0IjoxNjk2NDk2OTY4LCJleHAiOjE2OTY5Mjg5Njh9.iT5NQLJrm3adqM5s1mWSg1rllyP-Alm9_SIldH1ppTz--zpT3xolQoOrOwf21whE4x8WCBTp9vWm-VU-C-5HYH4ZE7RKBN2M-ZVcAqer2QzMiibmrdlWbKwR4Xdtu-5Zsr-aG23jBlMcaASYjG7AJVbE9bsKA4c4-H9Gg0kHTfokDbaugosiPZCI4-MZR4RzF728KOuqsS6_J6hLs6RLhhJkmqaxku8oGRusEyWAgOKmwyDAQHDy3cj-oTKNFLy2aoD13Huw4is_2iQdUsjt7guGUKFdDsRVpT0wHw1B3mt5-GeCv9PwO17Hy33oUmyTraKuDg_GQC1J57nHEWY7lA";
	
	@Value("${user.token}")
	private String USER_TOKEN;
	
	@DisplayName("JUnit test for @GetMapping(/orders) endpoint")
	@Test
	public void getAllOrders_thenOK() {
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ SUPER_ADMIN_TOKEN)
				.get(API_ROOT+"/orders");
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.jsonPath().getList("isbn").size() > 0);
	}
	
	@DisplayName("JUnit test for @GetMapping(/orders) endpoint")
	@Test
	public void getAllOrders_USER_ROLE_thenOK() {
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ USER_TOKEN)
				.get(API_ROOT+"/orders");
		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
	}
	
	@DisplayName("JUnit test for @GetMapping(/orders/{id}) endpoint")
	@Test
	public void getOrderById_thenOK() {

	    Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ SUPER_ADMIN_TOKEN)
	    		.get(API_ROOT+"/orders/1");
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertEquals("ghijk1234", response.jsonPath().get("isbn"));
	}
	
	@DisplayName("JUnit test for @PutMapping(/orders/{id}) endpoint")
	@Test
	public void updateOrder_thenOK() throws OrderNotFoundException {
		
		Order order1 = new Order("ghijk1234", LocalDate.now().plusWeeks(4));
		order1.setId(1L);
		
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ SUPER_ADMIN_TOKEN)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(order1)
				.put(API_ROOT+"/orders/{id}", order1.getId());
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertEquals("ghijk1234", response.jsonPath().get("isbn"));
	}
	
	@DisplayName("JUnit test for @DeleteMapping(/orders/{id}) endpoint")
	@Test
	public void deleteOrderById_thenOK() {

	    Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ SUPER_ADMIN_TOKEN)
	    		.delete(API_ROOT+"/orders/2");
	    
	    // attempt to access the same resource again
	    Response resp = RestAssured.given()
				.header("Authorization", "Bearer "+ SUPER_ADMIN_TOKEN)
	    		.get(API_ROOT+"/orders/2");
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resp.getStatusCode());

	}

}

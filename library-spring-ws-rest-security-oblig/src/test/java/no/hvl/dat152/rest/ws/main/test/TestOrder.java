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
	private String SUPER_ADMIN_TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJiZXJpdEBlbWFpbC5jb20iLCJpc3MiOiJEQVQxNTItTGVjdHVyZXJAVERPWSIsImZpcnN0bmFtZSI6IkJlcml0IiwibGFzdG5hbWUiOiJKw7hyZ2Vuc2VuIiwicm9sZXMiOlsiU1VQRVJfQURNSU4iLCJVU0VSIiwiQURNSU4iXSwiaWF0IjoxNjk3MDQzMDkyLCJleHAiOjE2OTc0NzUwOTJ9.YznX5cx6fEGZn3cWlpZboZuqWq6VYT-kmquFwhFtAtz_kuFa0s5pB0-VcW1NG-bqUirkQDr4RoKQr7ssFHB25kMWQII3JdF2Zk6hWF4U1BiNY7827vLzylRGtktIiciMD3zCjsF33jhOKmXm9Kq9SRNBsDQAZHcD_XYZeil29Av70ldLzBm90WG6rWanGsf9WtK2qAW5uEGuvn9WTV6ANMA2aqji2FAjEzwlodjqIBXv94hsDENob-EZr6ROktH9Mk0teHAixEJjM3yiXUFooDdhSd2A8XNCLup3JF6lsdmFZLADlPPX8rUOfQKWeygLZyyLRp32pE0K5vsGsOJN2w"; 
	
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
		System.out.println("The d is " + order1.getId()); 
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

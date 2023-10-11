package no.hvl.dat152.rest.ws.main.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.Response;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TestsAdminUser {
	
	private String API_ROOT = "http://localhost:8090/elibrary/api/v1/admin";
	
	private String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJiZXJpdEBlbWFpbC5jb20iLCJpc3MiOiJEQVQxNTItTGVjdHVyZXJAVERPWSIsImZpcnN0bmFtZSI6IkJlcml0IiwibGFzdG5hbWUiOiJKw7hyZ2Vuc2VuIiwicm9sZXMiOlsiU1VQRVJfQURNSU4iLCJVU0VSIiwiQURNSU4iXSwiaWF0IjoxNjk3MDQzMDkyLCJleHAiOjE2OTc0NzUwOTJ9.YznX5cx6fEGZn3cWlpZboZuqWq6VYT-kmquFwhFtAtz_kuFa0s5pB0-VcW1NG-bqUirkQDr4RoKQr7ssFHB25kMWQII3JdF2Zk6hWF4U1BiNY7827vLzylRGtktIiciMD3zCjsF33jhOKmXm9Kq9SRNBsDQAZHcD_XYZeil29Av70ldLzBm90WG6rWanGsf9WtK2qAW5uEGuvn9WTV6ANMA2aqji2FAjEzwlodjqIBXv94hsDENob-EZr6ROktH9Mk0teHAixEJjM3yiXUFooDdhSd2A8XNCLup3JF6lsdmFZLADlPPX8rUOfQKWeygLZyyLRp32pE0K5vsGsOJN2w"; 
	
	@DisplayName("JUnit test for @PutMapping(/users/{id}) endpoint")
	@Test
	public void AupdateUserRole_thenOK() {
		
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.queryParam("role", "admin")
				.put(API_ROOT+"/users/{id}", 1);
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertEquals("robert@email.com", response.jsonPath().get("email"));
	}
	
	@DisplayName("JUnit test for @DeleteMapping(/users/{id}) endpoint")
	@Test
	public void BdeleteUserRole_thenOK() {
		
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ token)
				.queryParam("role", "user")
				.delete(API_ROOT+"/users/{id}", 1);
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertFalse(response.jsonPath().getList("roles").get(0).toString().contains("USER"));
	}

}

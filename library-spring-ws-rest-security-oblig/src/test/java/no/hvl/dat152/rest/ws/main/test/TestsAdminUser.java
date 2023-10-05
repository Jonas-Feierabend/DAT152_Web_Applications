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
	
	private String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJiZXJpdEBlbWFpbC5jb20iLCJpc3MiOiJEQVQxNTItTGVjdHVyZXJAVERPWSIsImZpcnN0bmFtZSI6IkJlcml0IiwibGFzdG5hbWUiOiJKw7hyZ2Vuc2VuIiwicm9sZXMiOlsiQURNSU4iLCJVU0VSIiwiU1VQRVJfQURNSU4iXSwiaWF0IjoxNjk2NDk2OTY4LCJleHAiOjE2OTY5Mjg5Njh9.iT5NQLJrm3adqM5s1mWSg1rllyP-Alm9_SIldH1ppTz--zpT3xolQoOrOwf21whE4x8WCBTp9vWm-VU-C-5HYH4ZE7RKBN2M-ZVcAqer2QzMiibmrdlWbKwR4Xdtu-5Zsr-aG23jBlMcaASYjG7AJVbE9bsKA4c4-H9Gg0kHTfokDbaugosiPZCI4-MZR4RzF728KOuqsS6_J6hLs6RLhhJkmqaxku8oGRusEyWAgOKmwyDAQHDy3cj-oTKNFLy2aoD13Huw4is_2iQdUsjt7guGUKFdDsRVpT0wHw1B3mt5-GeCv9PwO17Hy33oUmyTraKuDg_GQC1J57nHEWY7lA";
	
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

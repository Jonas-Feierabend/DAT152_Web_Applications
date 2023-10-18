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
	
	private String token ="eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJiZXJpdEBlbWFpbC5jb20iLCJpc3MiOiJEQVQxNTItTGVjdHVyZXJAVERPWSIsImZpcnN0bmFtZSI6IkJlcml0IiwibGFzdG5hbWUiOiJKw7hyZ2Vuc2VuIiwicm9sZXMiOlsiU1VQRVJfQURNSU4iLCJBRE1JTiIsIlVTRVIiXSwiaWF0IjoxNjk3NjE3NDQ4LCJleHAiOjE2OTgwNDk0NDh9.eFyKBpMeOO6omcQ3AAREnb7Vf8iXgxZTXfo8cKj4IAC5PzOl2Ub0O1PC1HixZ5sLAJ1QDl1buskvhH2Y0ISINgwLHFZTkq037WJL5KDh38N0uNp6kxQOLZdX5xXYO23XKFprRuOnRHEO8nx8jwRFUL7giZFDa8FdLHXh9CqPAjNcaBuFnFUYRv4t3BxoSjYykna-fA57o-IZ8VxOe5kGH2hRLiKh-U_7mysSq27xp9AT4MRjDHH1BuZHA7UwOUXQxpVSbh6U1Wn3WG4qmFUCRv-CB5TVucra6M3j8e0ahRBK_N5wICv_zh3Ue9jt2eBouDofDPs6bKDMB7WgHmVHeA";

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
	    assertFalse(response.jsonPath().getList("roles").get(1).toString().contains("USER"));
	}

}

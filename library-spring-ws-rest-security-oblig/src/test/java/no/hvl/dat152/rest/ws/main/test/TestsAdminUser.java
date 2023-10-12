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
	
	private String token ="eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJiZXJpdEBlbWFpbC5jb20iLCJpc3MiOiJEQVQxNTItTGVjdHVyZXJAVERPWSIsImZpcnN0bmFtZSI6IkJlcml0IiwibGFzdG5hbWUiOiJKw7hyZ2Vuc2VuIiwicm9sZXMiOlsiQURNSU4iLCJTVVBFUl9BRE1JTiIsIlVTRVIiXSwiaWF0IjoxNjk3MDk5NzgxLCJleHAiOjE2OTc1MzE3ODF9.lYyBJg2qblqCuECiM7WsDoiLqzgKWmx0t5NHd5GuMW2Pj_8i80VJeGUEAjc4zgQKrnw_Sn5bAgIHVMFRoW-DPHjp9SEsc4YStwTWA6u8xcxYKAy-Eimo9LFMyUKe5XuVCt6g78EG04tnbGRn-0_Zq0W6O6U5LRfz1VU8G2CjCcYj1Y0FM4Sk7adQnAI9_6W5mdlckMJXid12oyR76P-a8O3KreFSo1grADBK2wSYKqEKxGT65Z8ksRr1L1UdT5Ome0zn16lPUeOY8DvNKqjODptquuE51YYKgUeiOaOIWs8gsiyfISasWU38sSdMbdH58oatWfkgDXXkSW5wqIWukg";

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

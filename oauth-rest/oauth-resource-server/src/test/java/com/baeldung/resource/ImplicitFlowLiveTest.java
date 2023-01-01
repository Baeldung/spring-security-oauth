package com.baeldung.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;

//Before running this live test make sure both authorization server and resource server are running   

public class ImplicitFlowLiveTest {
	private final static String AUTH_SERVER = "http://localhost:8083/auth/realms/baeldung/protocol/openid-connect";
	private final static String RESOURCE_SERVER = "http://localhost:8081/resource-server";
	private final static String REDIRECT_URL = "http://localhost:8082/new-client/login/oauth2/code/custom";
	private final static String CLIENT_ID = "newClient";
	private final static String USERNAME_WITH_CORRECT_DOMAIN = "john@test.com";
	private final static String PASSWORD_FOR_USER_WITH_CORRECT_DOMAIN = "123";

	private final static String USERNAME_WITH_INCORRECT_DOMAIN = "mike@other.com";

	private final static String PASSWORD_FOR_USER_WITH_INCORRECT_DOMAIN = "pass";
	

	@Test
	public void givenUser_whenUsesFooClient_thenOkForFooResourceOnly() {
		final String accessToken = obtainAccessToken(CLIENT_ID, USERNAME_WITH_CORRECT_DOMAIN, PASSWORD_FOR_USER_WITH_CORRECT_DOMAIN);

		final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.get(RESOURCE_SERVER + "/api/foos/1");
		assertEquals(200, fooResponse.getStatusCode());
		assertNotNull(fooResponse.jsonPath().get("name"));
	}

	@Test
	public void givenUser_whenUsesFooClient_thenFailureForNonTestDomainUser() {
		final String accessToken = obtainAccessToken(CLIENT_ID, USERNAME_WITH_INCORRECT_DOMAIN, PASSWORD_FOR_USER_WITH_INCORRECT_DOMAIN);

		final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.get(RESOURCE_SERVER + "/api/foos/1");
		assertEquals(401, fooResponse.getStatusCode());
	}

	private String obtainAccessToken(String clientId, String username, String password) {
		String authorizeUrl = AUTH_SERVER + "/auth";

		Map<String, String> loginParams = new HashMap<String, String>();
		loginParams.put("grant_type", "implicit");
		loginParams.put("client_id", clientId);
		loginParams.put("response_type", "token");
		loginParams.put("redirect_uri", REDIRECT_URL);
		loginParams.put("scope", "read write");

		// user login
		Response response = RestAssured.given().formParams(loginParams).get(authorizeUrl);
		String cookieValue = response.getCookie("AUTH_SESSION_ID");
	
		String authUrlWithCode = response.htmlPath().getString("'**'.find{node -> node.name()=='form'}*.@action");
		
		// get access token
		Map<String, String> tokenParams = new HashMap<String, String>();
		tokenParams.put("username", username);
		tokenParams.put("password", password);
		tokenParams.put("client_id", clientId);
		tokenParams.put("redirect_uri", REDIRECT_URL);
		response = RestAssured.given().cookie("AUTH_SESSION_ID", cookieValue).formParams(tokenParams)
				.post(authUrlWithCode);

		final String location = response.getHeader(HttpHeaders.LOCATION);

		assertEquals(HttpStatus.FOUND.value(), response.getStatusCode());
		final String accessToken = location.split("#|=|&")[4];
		return accessToken;

	}

}

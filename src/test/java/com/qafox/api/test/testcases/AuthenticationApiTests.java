package com.qafox.api.test.testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;

public class AuthenticationApiTests {

	@Test
	public void basicAuthTest() {
		given().auth().preemptive().basic("postman", "password").get("https://postman-echo.com/basic-auth").then()
				.statusCode(200).body("authenticated", equalTo(true));
	}

	@Test
	public void digestAuthTest() {
		given().auth()
				.digest("Authorization",
						"Digest username=\"postman\", realm=\"Users\", nonce=\"u5GXJBTHDLHEVTdbACNNJ2QGXNDI78DX\", uri=\"\\/digest-auth\", algorithm=\"MD5\", response=\"e2d3d32b2e675d7449e4ff7aeed7b8c6\"")
				.get("https://postman-echo.com/digest-auth").then().statusCode(200).body("authenticated", equalTo(true));
	}
}
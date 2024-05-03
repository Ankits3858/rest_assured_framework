package com.qafox.api.test.utilities;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import io.restassured.http.ContentType;

public class ResponseLoggingUtils {

	@Test
	public void printResponseDetails() {
		given().accept(ContentType.JSON).get("https://petstore.swagger.io/v2/pet/6").then().log()
				/* .all() */.headers().log().body().log().status();
	}
}
package com.qafox.api.test.testcases;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import io.restassured.http.ContentType;

public class HttpPutTestCase {

	@Test
	public void sampleTest() {
		given().accept(ContentType.JSON).put("https://postman-echo.com/put").then().statusCode(200).log().all();
	}

	@Test
	public void putWithBodyAsHJson() {
		given().accept(ContentType.JSON).body("{\"id\":1,\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}")
				.put("https://jsonplaceholder.typicode.com/posts/1").then().statusCode(200);
	}
}
package com.qafox.api.test.testcases;

import static io.restassured.RestAssured.given;

import org.junit.Test;
import io.restassured.http.ContentType;

public class HttpPatchTestCase {

	@Test
	public void sampleTest() {
		given().accept(ContentType.JSON).patch("https://postman-echo.com/patch").then().statusCode(200);
	}

	@Test
	public void patchRequestWithJsonAsBody() {
		given().accept(ContentType.JSON).body("{\"title\": \"foo\"}")
				.patch("https://jsonplaceholder.typicode.com/posts/1").then().statusCode(200).log().all();
	}
}
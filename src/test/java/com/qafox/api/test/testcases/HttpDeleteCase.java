package com.qafox.api.test.testcases;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import io.restassured.http.ContentType;

public class HttpDeleteCase {

	@Test
	public void sampleTest() {
		given().accept(ContentType.JSON).delete("http://httpbin.org/delete").then().statusCode(200).log().all();
	}
}
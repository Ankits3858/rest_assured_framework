package com.qafox.api.test.utilities;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Assert;
import org.junit.Test;

import com.qafox.api.test.resources.UserInfoResource;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class AssertionUtils {

	/**
	 * Validating status code ways.
	 */
	@Test
	public void verifyStatusCodeInline() {
		/* Shorter way */
		/*
		 * given().get("http://restapi.demoqa.com/utilities/weather/city").then(
		 * ).statusCode(400);
		 */

		/* explored way */
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification requestSpecification = RestAssured.given();
		Response response = requestSpecification.get("/Hyderabad");
		int statusCode = response.getStatusCode();
		Assert.assertThat(statusCode, equalTo(200));
	}

	@Test
	public void verifyStatusLine() {
		/* Shorter way */
		// given().get("http://restapi.demoqa.com/utilities/weather/city/Hyderabad").then().statusLine("HTTP/1.1
		// 200 OK");

		/* explored way */
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification requestSpecification = RestAssured.given();
		Response response = requestSpecification.get("/Hyderabad");
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}

	@Test
	public void verifyResponseHeaders() {
		// shorter way.
		// given().get("http://restapi.demoqa.com/utilities/weather/city/Hyderabad").then().log().all().header("Content-Type",
		// "application/json").header("Server",
		// "nginx/1.12.1").header("Content-Encoding", "gzip");

		// Explored way.
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// Reader header of a give name. In this line we will get
		// Header named Content-Type
		String contentType = response.header("Content-Type");
		Assert.assertEquals(contentType /* actual value */,
				"application/json" /* expected value */);

		// Reader header of a give name. In this line we will get
		// Header named Server
		String serverType = response.header("Server");
		Assert.assertEquals(serverType /* actual value */,
				"nginx/1.12.1" /* expected value */);

		// Reader header of a give name. In this line we will get
		// Header named Content-Encoding
		String contentEncoding = response.header("Content-Encoding");
		Assert.assertEquals(contentEncoding /* actual value */,
				"gzip" /* expected value */);
	}

	@Test
	public void verifyResponseBodyInAllWays() {
		// 1. In-line.
		given().get("https://jsonplaceholder.typicode.com/posts/1").then().body("id", equalTo(1));

		// 2. Using Response.
		Response response = given().get("https://jsonplaceholder.typicode.com/posts/1");
		String body = response.getBody().asString();
		Assert.assertTrue(body.contains("sunt"));

		// 3. Using JsonPath.
		Response jsonResponse = given().get("https://jsonplaceholder.typicode.com/posts/1");
		JsonPath jsonPath = jsonResponse.jsonPath();
		String id = jsonPath.getString("title");
		Assert.assertTrue(id.contains("sunt"));

		// 4. Validating array json.
		Response arrayJson = given().get("https://jsonplaceholder.typicode.com/posts");
		JsonPath arrayJsonPath = arrayJson.jsonPath();
		int userId = Integer.parseInt(arrayJsonPath.getString("[0].userId"));
		Assert.assertEquals(userId, 1);
	}

	@Test
	public void verifyResponseByConvertingItToResource() {
		ResponseBody response = given().get("https://jsonplaceholder.typicode.com/posts/1").getBody();
		UserInfoResource resource = response.as(UserInfoResource.class);
		Assert.assertEquals(resource.getId(), 1);
	}
}
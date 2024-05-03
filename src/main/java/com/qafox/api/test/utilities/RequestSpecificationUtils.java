package com.qafox.api.test.utilities;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecificationUtils {

	/**
	 * Construct request specification with all common properties of APIs'
	 * 
	 * @return request specification.
	 */
	public static RequestSpecification constructRequestSpec() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri("https://maps.googleapis.com");
		requestSpecBuilder.setBasePath("maps/api/place/textsearch/json");
		requestSpecBuilder.addParam("query", "restaurants in mumbai");
		requestSpecBuilder.addParam("key", "XYZ");
		requestSpecBuilder.setContentType(ContentType.JSON);
		requestSpecBuilder.addHeader("accept", "application/json");
		// BasicAuthScheme basic=new BasicAuthScheme();
		PreemptiveBasicAuthScheme auth = new PreemptiveBasicAuthScheme();
		auth.setPassword("qafox");
		auth.setPassword("qafox");
		requestSpecBuilder.setAuth(auth);
		return requestSpecBuilder.build();
	}

	/**
	 * It is a sample test to use or append request specification to request.
	 */
	@Test
	public void requestSpecificationTests() {
		given().spec(constructRequestSpec()).when().get().then().contentType(ContentType.JSON).statusCode(200).log()
				.all();
	}
}
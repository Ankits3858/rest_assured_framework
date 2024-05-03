package com.qafox.api.test.utilities;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecificationUtils {
	
	
	
	/**
	 * We can write multiple methods to handle different response handling methods including BAD REQUEST, 500 SERVER ERROR, 200 OK.
	 * 
	 * +ve seperate
	 * -ve seperate
	 */
	

	private static ResponseSpecBuilder responseSpecBuilder;
	private static ResponseSpecification responseSpecification;

	private static ResponseSpecification validateResponse() {
		responseSpecBuilder = new ResponseSpecBuilder();
		responseSpecBuilder.expectStatusCode(200);
		responseSpecBuilder.expectHeader("Content-Type", "application/json");
		responseSpecBuilder.expectBody("id", equalTo(9));
		responseSpecification = responseSpecBuilder.build();
		return responseSpecification;
	}

	@Test
	public void findPetById() {
		given().contentType(ContentType.JSON).get("https://petstore.swagger.io/v2/pet/9").then()
				.spec(validateResponse());
	}
}
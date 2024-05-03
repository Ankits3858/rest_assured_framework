package com.qafox.api.test.utilities;

import static io.restassured.RestAssured.given;
import org.junit.Test;
import io.restassured.http.ContentType;

public class RequestLoggingUtils {

	/**
	 * In many cases it is intended to see for what request we made. We can
	 * print whole details of request using logging.
	 * 
	 */
	@Test
	public void printRequestDetails() {
		given().accept(ContentType.JSON)
				.pathParam("test", "pet")/* .formParam("sample", "test") */
				.queryParam("sample", "test")
				.body("{body}")/* .param("test", "test") */
				.header("Content-Type", "application/json").when().log().params().log().body().log().headers()
				.post("https://petstore.swagger.io/v2/{test}/6");
	}
}
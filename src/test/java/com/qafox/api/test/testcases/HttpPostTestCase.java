package com.qafox.api.test.testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;

import org.junit.Test;

import com.qafox.api.test.resources.SampleResource;

import io.restassured.http.ContentType;

public class HttpPostTestCase {

	@Test
	public void postRequestWithPayload_asJsonString() {
		String body = "{\"title\": \"foo\",\"body\": \"bar\",\"userId\": 1}";
		given().contentType(ContentType.JSON).body(body).post("https://jsonplaceholder.typicode.com/posts").then()
				.statusCode(201).log().all();
	}

	@Test
	public void postRequstWithPayload_asJavaObject() {
		SampleResource body = new SampleResource();
		body.setBody("bar");
		body.setTitle("foo");
		body.setUserId(2);
		given().contentType(ContentType.JSON).body(body).post("https://jsonplaceholder.typicode.com/posts").then()
				.statusCode(201).log().all();
	}

	@Test
	public void postRequestWithPayload_asFileBody() {
		File file = new File("src/main/java/com/qafox/api/test/testdata/test.json");
		given().body(file).contentType(ContentType.JSON).post("https://jsonplaceholder.typicode.com/posts").then()
				.statusCode(201).log().all();
	}

	@Test
	public void postRequestWithPayload_asUrlencoded() {
		given().contentType(ContentType.URLENC.withCharset("UTF-8")).formParam("foo1", "bar1").formParam("foo2", "bar2")
				.log().all().post("https://postman-echo.com/post").then().log().all().statusCode(200)
				.body("form.foo1", equalTo("bar1"));
	}

	@Test
	public void postRequestWithPayload_asMultiPart() {
		given().formParam("additionalMetadata", "test")
				.multiPart(new File("src/main/java/com/qafox/api/test/testdata/test.json")).log().all()
				.post("https://petstore.swagger.io/v2/pet/9/uploadImage").then().statusCode(200);
	}

	@Test
	public void postRequestWithPayload_asXml() {
		given().contentType(ContentType.XML).body(new File("src/main/java/com/qafox/api/test/testdata/xmltest.xml"))
				.post("https://petstore.swagger.io/v2/pet").then().statusCode(200);
	}
}
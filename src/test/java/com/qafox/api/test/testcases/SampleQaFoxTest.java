package com.qafox.api.test.testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SampleQaFoxTest {

	@Test
	public void sampleGetRequest() {
		given().log().all().contentType(ContentType.JSON).get("https://petstore.swagger.io/v2/pet/1").then()
				.body("id", equalTo(1)).statusCode(200).log().body();
	}

	@Test
	public void samplePostRequest() {
		String body = "{\"title\": \"foo\",\"body\": \"bar\",\"userId\": 1}";
		given().log().body().log().parameters().contentType(ContentType.JSON).body(body)
				.post("https://jsonplaceholder.typicode.com/posts").then().statusCode(201).log().cookies();
	}

	@Test
	public void samplePutRequest() {
		given().contentType(ContentType.JSON).put("https://postman-echo.com/put").then().statusCode(200);
	}

	@Test
	public void samplePatchRequest() {
		given().contentType(ContentType.JSON).patch("https://postman-echo.com/patch").then().statusCode(200);
	}

	@Test
	public void samplePatchRequestWithJson() {
		String body = "{\"title\":\"qafox\"}";
		given().contentType(ContentType.JSON).body(body).patch("https://jsonplaceholder.typicode.com/posts/1").then()
				.statusCode(200);
	}

	@Test
	public void sampleDeleteRequest() {
		given().contentType(ContentType.JSON).delete("http://httpbin.org/delete").then().statusCode(200);
	}

	@Test
	public void sampleFormdataRequest() {
		given().log().parameters().contentType(ContentType.URLENC.withCharset("UTF-8")).formParam("foo1", "bar1")
				.formParam("foo2", "bar2").post("https://postman-echo.com/post").then().statusCode(200)
				.body("form.foo1", equalTo("bar1")).log().body();
	}

	@Test
	public void sampleMultiPartRequest() {
		given().formParam("additionalMetadata", "test")
				.multiPart(new File("src/main/java/com/qafox/api/test/testdata/test.json"))
				.post("https://petstore.swagger.io/v2/pet/9/uploadImage").then().statusCode(200).log().all();
	}

	@Test
	public void sampleXmlRequest() {
		given().log().body().accept(ContentType.XML).contentType(ContentType.XML)
				.body(new File("src/main/java/com/qafox/api/test/testdata/xmltest.xml"))
				.post("https://petstore.swagger.io/v2/pet").then().statusCode(200).log().body();
	}

	public RequestSpecification constructReqSpec() {
		RequestSpecBuilder reqSpec = new RequestSpecBuilder();
		reqSpec.addHeader("Content-Type", "application/json");
		reqSpec.setBody("{\"title\":\"qafox\"}");
		reqSpec.setBaseUri("https://jsonplaceholder.typicode.com");
		reqSpec.setBasePath("/posts/1");
		reqSpec.setAuth(constructAuth());
		return reqSpec.build();
	}

	public AuthenticationScheme constructAuth() {
		BasicAuthScheme basicAuth = new BasicAuthScheme();
		basicAuth.setPassword("password");
		basicAuth.setUserName("postman");
		return basicAuth;
	}

	@Test
	public void sampleRequestUsingReqSpec() {
		// given().spec(constructReqSpec()).patch().then().spec(constructResSpec());

		given(constructReqSpec(), constructResSpec()).patch();

	}

	public ResponseSpecification constructResSpec() {
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
		responseSpecBuilder.expectStatusCode(200);
		return responseSpecBuilder.build();
	}

	@Test
	public void testBasicAuthentication() {
		given().accept(ContentType.JSON).spec(constructReqSpec()).get("https://postman-echo.com/basic-auth").then()
				.statusCode(200);
	}

	@Test
	public void testDigestAuthentication() {
		given().auth().digest("", "").get("").then().statusCode(200);
	}

	@Test
	public void testOauthenticationRequest() {
		given().auth().oauth("", "", "", "").get().then().statusCode(200);
	}

	@Test
	public void testOauthentication2Request() {
		given().auth().preemptive().oauth2("").get().then().statusCode(200);
	}

	@Test
	public void formHeaders() {
		given().header(constructHeader()).delete("http://httpbin.org/delete").then().statusCode(200);
	}

	private Header constructHeader() {
		Header header = new Header("Content-Type", "application/json");
		return header;
	}

	@Test
	public void testPathParams() {
		given().pathParam("operation", "delete").pathParam("version", "v1").log().all()
				.delete("http://httpbin.org/{version}/{operation}").then().statusCode(200);
	}

	@Test
	public void validateTest() {
		given().accept(ContentType.JSON).get("https://petstore.swagger.io/v2/pet/20").then().statusCode(200).log().all()
				.body("id", equalTo(20)).body("category.id", equalTo(0)).body("name", equalTo("doggie"));
	}

	@Test
	public void validateTest2() {
		given().accept(ContentType.JSON).get("http://services.groupkt.com/country/get/all").then().statusCode(200).log()
				.body().body("RestResponse.result[2].name", equalTo("Albania")).body(containsString("India"))
				.body("RestResponse.result.name", hasItems("Zambia", "Zimbabwe"));
	}

	@Test
	public void validateTest3() {
		Response response = given().accept(ContentType.JSON).when().get("https://jsonplaceholder.typicode.com/users");
		System.out.println(response.asString());
		System.out.println("***********************************");
		String name = response.jsonPath().getString("username");
		System.out.println(name);
		System.out.println(response.jsonPath().getString("username[0]"));
		String username0 = response.jsonPath().getString("username[0]");
		Assert.assertEquals("Bret", username0);
		System.out.println(response.jsonPath().getList("username"));
	}

	@Test
	public void validateTest4() {
		given().accept(ContentType.JSON).get("https://petstore.swagger.io/v2/pet/20").then().log().all()
				.header("Connection", equalTo("close")).header("Content-Type", "application/json");
	}
}
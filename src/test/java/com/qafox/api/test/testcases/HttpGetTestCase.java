package com.qafox.api.test.testcases;

import static com.qafox.api.test.utilities.RequestUtils.constructHeaders;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.qafox.api.test.testdata.DataConstants;
import com.qafox.api.test.utilities.RequestSpecificationUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HttpGetTestCase {

	@Before
	public void runBefore() {
		RequestSpecification spec = RequestSpecificationUtils.constructRequestSpec();
	}

	@Test
	public void findPetByStatus() {
		Map<String, String> headers = new HashMap<>();
		headers.put(DataConstants.TOKEN, DataConstants.access_token);
		headers.put(DataConstants.ID, DataConstants.client_id);

		Map<String, Object> params = new HashMap<>();
		params.put("apiName", "api");
		params.put("version", "v1");
		params.put("path1", "user");

		/*
		 * given().headers(constructHeaders(headers)).get(
		 * "https://a.wunderlist.com/api/v1/user").then() .body("name", equalTo(
		 * "Vishwak Kurapati")).body("id", is(26611178)).statusCode(200);
		 */
		given().pathParams(params).headers(constructHeaders(headers))
				.get(DataConstants.URI + "/{apiName}/{version}/{path1}").then()
				.body("name", equalTo("Vishwak Kurapati"), "id", equalTo(26611178)).statusCode(200);
	}

	@Test
	public void findPetById() {
		given().contentType(ContentType.JSON).expect().then().statusCode(200)
				.body("id", equalTo(8), "category.id", equalTo(0)).when().get("https://petstore.swagger.io/v2/pet/8");
	}

	@Test
	public void findCountryInGroupKt() {
		given().accept(ContentType.JSON).expect().then().statusCode(200).body(containsString("India")).when()
				.get("http://services.groupkt.com/country/get/all");
	}

	@Test
	public void findCountryInGroupKtLogtoConsole() {
		given().accept(ContentType.JSON).expect().then().log().body().when()
				.get("http://services.groupkt.com/country/get/all");
	}

	@Test
	public void findCountryInGroupKtValidateArrayJson() {
		given().accept(ContentType.JSON).expect().then().statusCode(200)
				.body("RestResponse.result[0].name", equalTo("Afghanistan")).log().body().when()
				.get("http://services.groupkt.com/country/get/all");
	}

	@Test // Applicable for a pure Array json.
	public void findCountryInGroupKtValidatingWholeArrayJson() {
		Response response = given().accept(ContentType.JSON).expect().then().statusCode(200)
				/* .log().body() */.when().get("https://jsonplaceholder.typicode.com/users");
		// To get all username entries out of the response body.
		String usernames = response.jsonPath().getString("username");
		System.out.println(usernames);

		// To get the specified item from the list of usernames.
		System.out.println(response.jsonPath().getString("username[0]"));

		// To get the usernames as a list.
		System.out.println(response.jsonPath().getList("username"));
	}

	@Test // Applicable for a pure Array json.
	public void findCountryInGroupKtValidatingMapResultsFromArrayJson() {
		Response response = given().accept(ContentType.JSON).expect().then().statusCode(200)
				/* .log().body() */.when().get("https://jsonplaceholder.typicode.com/users/1");
		Map<String, String> companyDetails = response.jsonPath().getMap("company");
		// To get map type results from response.
		System.out.println(companyDetails.get("name"));
	}
	

	@Test
	public void stackOverflowTest() {
		given().get("http://services.groupkt.com/country/get/all").then()
				.body("RestResponse.result.name", hasItems("Austria", "Algeria")).log().all();
	}

	@Test
	public void verifyContentType() {
		given().get("http://services.groupkt.com/country/get/all").then()
				.body("RestResponse.result.name", hasItems("Austria", "Algeria")).contentType(ContentType.JSON).log()
				.all();
	}

	@Test
	public void verifyResponseHeaders() {
		given().get("http://services.groupkt.com/country/get/all").then()
				.body("RestResponse.result.name", hasItems("Austria", "Algeria")).contentType(ContentType.JSON).and()
				.header("Connection", "Keep-Alive").log().all();
	}

	@Test
	public void verifyBasicAuth() {
		given().auth().preemptive().basic("postman", "password").when().accept(ContentType.JSON)
				.get("https://postman-echo.com/basic-auth").then().statusCode(200);
	}

	@Test
	public void verifyOauth2() {
		given().auth().oauth2("").accept(ContentType.JSON).when().get("").then().statusCode(200);
	}

	@Test 
	public void deleteRequest() {
		Map<String, String> map = new HashMap<>();
		map.put("X-Access-Token", "6790b86c2afb6e801a736fa6e97f4ca7d60afbbcec55c37ee03c3be68c0d");
		map.put("X-Client-ID", "2cbab48016d117bb3e17");
		given().headers(map).accept(ContentType.JSON).when()
				.delete("https://a.wunderlist.com/api/v1/lists/365778133?revision=1").then().statusCode(204);
	}

	@Test
	public void postRequest() {
		Map<String, String> map = new HashMap<>();
		map.put("X-Access-Token", "6790b86c2afb6e801a736fa6e97f4ca7d60afbbcec55c37ee03c3be68c0d");
		map.put("X-Client-ID", "2cbab48016d117bb3e17");
		given().headers(map).contentType(ContentType.JSON).body("{\"title\": \"QaFox\"}").accept(ContentType.JSON)
				.then().statusCode(201).and().body("title", equalTo("QaFox"));
	}
}
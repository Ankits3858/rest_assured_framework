package com.qafox.api.test.utilities;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.qafox.api.test.resources.ExcelResource;

import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class ExecuteApi {

	private ExcelResource resource;
	private Logger log = Logger.getLogger(ExecuteApi.class);

	public ExecuteApi(ExcelResource resource) {
		this.resource = resource;
	}

	public void execute() {
		RequestSpecification spec = constructRequestSpecification(resource);
		switch (resource.getHttpVerb()) {
		case "GET":
			given().accept(resource.getHeaders()).get(resource.getEndPoint()).then().statusCode(200);
			break;
		case "POST":
			given().accept(resource.getHeaders()).contentType(resource.getHeaders()).body(resource.getRequestBody())
					.post(resource.getEndPoint()).then().statusCode(200);
			break;
		case "PUT":
			given().accept(resource.getHeaders()).contentType(resource.getHeaders()).body(resource.getRequestBody())
					.put(resource.getEndPoint()).then().statusCode(200);
			break;
		case "PATCH":
			given().accept(resource.getHeaders()).contentType(resource.getHeaders()).body(resource.getRequestBody())
					.patch(resource.getEndPoint()).then().statusCode(200);
			break;
		case "DELETE":
			given().accept(resource.getHeaders()).contentType(resource.getHeaders()).body(resource.getRequestBody())
					.delete(resource.getEndPoint()).then().statusCode(200);
			break;
		default:
			Assert.fail("No such http verb defined.");
			log.error("No such http verb defined.");
		}
	}

	private RequestSpecification constructRequestSpecification(ExcelResource resource) {
		RequestSpecBuilder reqSpec = new RequestSpecBuilder();
		reqSpec.setBaseUri(resource.getEndPoint());
		reqSpec.addHeaders(constructHeaders(resource));
		reqSpec.addParams(constructParams(resource));
		reqSpec.setAuth(constructAuthentication(resource));
		reqSpec.setBody(resource.getRequestBody());
		return reqSpec.build();
	}

	private AuthenticationScheme constructAuthentication(ExcelResource resource2) {
		BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
		String auth = resource2.getAuth();
		String[] a = auth.split(",");
		Map<String, String> map = extractParams(a);
		map.forEach((K, V) -> {
			basicAuthScheme.setProperty(K, V);
		});
		return basicAuthScheme;
	}

	private Map<String, ?> constructParams(ExcelResource resource2) {
		String queryParams = resource2.getQueryParameters();
		String[] a = queryParams.split(",");
		return extractParams(a);
	}

	private Map<String, String> constructHeaders(ExcelResource resource2) {
		String header = resource2.getHeaders();
		String[] a = header.split(",");
		return extractParams(a);
	}

	private Map<String, String> extractParams(String[] a) {
		Map<String, String> headers = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			headers.put(a[i].split(":")[0], a[i].split(":")[1]);
		}
		return headers;
	}
}

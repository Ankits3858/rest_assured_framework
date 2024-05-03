package com.qafox.api.test.parameterization;

import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.qafox.api.test.resources.ExcelResource;
import com.qafox.api.test.utilities.ExcelManager;

@RunWith(value = Parameterized.class)
public class ExcelParameterTests {

	private static final String sheet = "Sheet1";
	private static String filePath = "src/main/java/com/qafox/api/test/testdata/test.xlsx";
	private Logger log = Logger.getLogger(ExcelParameterTests.class);
	private static ExcelManager excelManager = new ExcelManager(filePath);
	private String httpVerb;
	private String endPoint;
	private String headers;
	private String queryParameters;
	private String requestBody;
	private String auth;
	private String multiparts;
	private String formdata;

	public ExcelParameterTests(String httpVerb, String endPoint, String queryParameters, String headers, String auth,
			String requestBody, String multiparts, String formdata) {
		this.endPoint = endPoint;
		this.headers = headers;
		this.httpVerb = httpVerb;
		this.queryParameters = queryParameters;
		this.requestBody = requestBody;
		this.auth = auth;
		this.multiparts = multiparts;
		this.formdata = formdata;
	}

	@Parameters
	public static Collection testData() {
		return Arrays.asList(excelManager.getExcelSheetData(sheet));
	}

	@Test
	public void test() {
		ExcelResource excelResource = new ExcelResource();
		excelResource.setHttpVerb(httpVerb);
		excelResource.setAuth(auth);
		excelResource.setHeaders(headers);
		excelResource.setEndPoint(endPoint);
		excelResource.setQueryParameters(queryParameters);
		excelResource.setRequestBody(requestBody);
		excelResource.setFormdata(formdata);
		excelResource.setMultiparts(multiparts);
		switch (excelResource.getHttpVerb()) {
		case "GET":
			given().accept(excelResource.getHeaders()).get(excelResource.getEndPoint()).then().statusCode(200);
			break;
		case "POST":
			given().accept(excelResource.getHeaders()).contentType(excelResource.getHeaders())
					.body(excelResource.getRequestBody()).post(excelResource.getEndPoint()).then().statusCode(200);
			break;
		case "PUT":
			given().accept(excelResource.getHeaders()).contentType(excelResource.getHeaders())
					.body(excelResource.getRequestBody()).put(excelResource.getEndPoint()).then().statusCode(200);
			break;
		case "PATCH":
			given().accept(excelResource.getHeaders()).contentType(excelResource.getHeaders())
					.body(excelResource.getRequestBody()).patch(excelResource.getEndPoint()).then().statusCode(200);
			break;
		case "DELETE":
			given().accept(excelResource.getHeaders()).contentType(excelResource.getHeaders())
					.body(excelResource.getRequestBody()).delete(excelResource.getEndPoint()).then().statusCode(200);
			break;
		default:
			Assert.fail("No such http verb defined.");
			log.error("No such http verb defined.");
		}
	}
}
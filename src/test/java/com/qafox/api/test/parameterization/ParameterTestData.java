package com.qafox.api.test.parameterization;

import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.qafox.api.test.resources.SampleResource;

import io.restassured.http.ContentType;

@RunWith(value = Parameterized.class)
public class ParameterTestData {

	private String title;
	private String body;
	private int userId;

	public ParameterTestData(String title, String body, int userId) {
		this.title = title;
		this.body = body;
		this.userId = userId;
	}

	@Parameters
	public static Collection testData() {
		return Arrays.asList(
				new Object[][] { { "qafox1", "test1", 1 }, { "qafox2", "test2", 2 }, { "qafox3", "test3", 3 } });
	}

	@Test
	public void postRequstWithPayload_asJavaObject() {
		SampleResource resource = new SampleResource();
		resource.setBody(body);
		resource.setTitle(title);
		resource.setUserId(userId);
		given().contentType(ContentType.JSON).body(resource).post("https://jsonplaceholder.typicode.com/posts").then()
				.statusCode(201).log().all();
	}
}
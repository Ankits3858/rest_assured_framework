package com.qafox.api.test.resources;

public class ExcelResource {

	private String httpVerb;
	private String endPoint;
	private String headers;
	private String queryParameters;
	private String requestBody;
	private String auth;
	private String multiparts;
	private String formdata;

	public String getMultiparts() {
		return multiparts;
	}

	public void setMultiparts(String multiparts) {
		this.multiparts = multiparts;
	}

	public String getFormdata() {
		return formdata;
	}

	public void setFormdata(String formdata) {
		this.formdata = formdata;
	}

	public String getHttpVerb() {
		return httpVerb;
	}

	public void setHttpVerb(String httpVerb) {
		this.httpVerb = httpVerb;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getQueryParameters() {
		return queryParameters;
	}

	public void setQueryParameters(String queryParameters) {
		this.queryParameters = queryParameters;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}
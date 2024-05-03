package com.qafox.api.test.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.restassured.http.Header;
import io.restassured.http.Headers;

/**
 * 
 * @author vishwak.
 *
 */
public class RequestUtils {

	public static Header header;
	public static Headers headers;
	public static List<Header> list;

	/**
	 * Constructing headers from map of headers.
	 * 
	 * @param mapHeaders
	 *            mapHeaders.
	 * @return headers.
	 */
	public static Headers constructHeaders(Map<String, String> mapHeaders) {
		list = new ArrayList<>();
		mapHeaders.forEach((K, V) -> {
			header = new Header(K, V);
			list.add(header);
		});
		headers = new Headers(list);
		return headers;
	}
}
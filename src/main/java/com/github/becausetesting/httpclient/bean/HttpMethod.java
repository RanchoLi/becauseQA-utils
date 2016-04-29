package com.github.becausetesting.httpclient.bean;

import org.apache.log4j.Logger;

public enum HttpMethod {
	GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS, TRACE;

	private static final Logger LOG = Logger.getLogger(HttpMethod.class.getName());

	public static HttpMethod get(final String method) {
		if ("GET".equals(method)) {
			return GET;
		} else if ("POST".equals(method)) {
			return POST;
		} else if ("PUT".equals(method)) {
			return PUT;
		} else if ("PATCH".equals(method)) {
			return PATCH;
		} else if ("DELETE".equals(method)) {
			return DELETE;
		} else if ("HEAD".equals(method)) {
			return HEAD;
		} else if ("OPTIONS".equals(method)) {
			return OPTIONS;
		} else if ("TRACE".equals(method)) {
			return TRACE;
		} else {
			LOG.warn("Unknown HTTP method encountered: " + method);
			LOG.warn("Setting default HTTP method: GET");
			return GET;
		}
	}
}
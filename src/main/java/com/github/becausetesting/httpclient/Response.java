package com.github.becausetesting.httpclient;

import java.net.HttpCookie;
import java.util.Arrays;
import java.util.List;

import com.github.becausetesting.apache.commons.CollectionUtils;
import com.github.becausetesting.collections.MultiValueMap;
import com.github.becausetesting.collections.MultiValueMapArrayList;
import com.github.becausetesting.encrypt.Base64Utils;

public class Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = -871306924021357582L;
	private int statusCode;
	private String statusLine;
	private MultiValueMap<String, String> headers;
	private byte[] responseBody;
	// private TestResult testResult;
	private long executionTime;
	
	private List<HttpCookie> cookiesList;

	public Response() {
		headers = new MultiValueMapArrayList<String, String>();
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public MultiValueMap<String, String> getHeaders() {
		return null;
	}
	/*
	 * public void setHeaders(Map<String, String> headers) { this.headers =
	 * headers; }
	 */

	public void setHeader(final String key, final String value) {
		this.headers.put(key, value);
	}

	public void setCookies(List<HttpCookie> cookiesList){
		this.cookiesList=cookiesList;
	}
	public List<HttpCookie> getCookies(){
		return this.cookiesList;
	}
	
	
	public String getContentType() {

		return null;
	}

	public byte[] getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(byte[] responseBody) {
		this.responseBody = responseBody;
	}

	public String getStatusLine() {
		return statusLine;
	}

	public void setStatusLine(String statusLine) {
		this.statusLine = statusLine;
	}

	public Object clone() {
		Response response = new Response();
		response.executionTime = executionTime;
		response.statusCode = statusCode;
		response.statusLine = statusLine;
		response.responseBody = responseBody;
		if (!headers.isEmpty()) {
			for (String header : headers.keySet()) {
				for (String value : headers.get(header)) {
					response.setHeader(header, value);
				}
			}
		}
		return response;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Response) {
			final Response bean = (Response) o;
			boolean isEqual = true;
			// Do not check executionTime: because when constructing
			// ResponseBean
			// from the UI, it is not possible to get this value:
			// isEqual = isEqual && (this.executionTime == bean.executionTime);
			isEqual = isEqual && (this.statusCode == bean.getStatusCode());
			isEqual = isEqual && (this.statusLine == null ? bean.getStatusLine() == null
					: this.statusLine.equals(bean.getStatusLine()));
			isEqual = isEqual
					&& (this.headers == null ? bean.getHeaders() == null : this.headers.equals(bean.getHeaders()));
			isEqual = isEqual && (this.responseBody == null ? bean.getResponseBody() == null
					: Arrays.equals(this.responseBody, bean.getResponseBody()));
			/*
			 * isEqual = isEqual && (this.testResult == null ?
			 * bean.getTestResult() == null :
			 * this.testResult.equals(bean.getTestResult()));
			 */
			return isEqual;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		// hash = 53 * hash + (int)this.executionTime;
		hash = 53 * hash + this.statusCode;
		hash = 53 * hash + (this.statusLine != null ? this.statusLine.hashCode() : 0);
		hash = 53 * hash + (this.headers != null ? this.headers.hashCode() : 0);
		hash = 53 * hash + (this.responseBody != null ? this.responseBody.hashCode() : 0);
		/*
		 * hash = 53 * hash + (this.testResult != null ?
		 * this.testResult.hashCode() : 0);
		 */
		return hash;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("@Response[");
		/*
		 * sb.append(statusLine).append(", ").append(headers).append(", "
		 * ).append(Base64Utils.encryptBasic(new String(responseBody, "UTF-8")))
		 * .append(", ");
		 */
		sb.append("]");
		return sb.toString();
	}
}

package com.github.becausetesting.httpclient;

import java.net.HttpCookie;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.github.becausetesting.collections.MultiValueMap;
import com.github.becausetesting.collections.MultiValueMapArrayList;
import com.github.becausetesting.httpclient.bean.Auth;
import com.github.becausetesting.httpclient.bean.HttpMethod;
import com.github.becausetesting.httpclient.bean.HttpVersion;
import com.github.becausetesting.httpclient.bean.RequestEntity;
import com.github.becausetesting.httpclient.bean.SSLRequest;

public class Request {

	private URL url;
	private HttpMethod method;
	private Auth auth;

	private RequestEntity body;
	private final MultiValueMap<String, String> headers = new MultiValueMapArrayList<>();
	private final List<HttpCookie> cookies = new ArrayList<HttpCookie>();
	private HttpVersion httpVersion = HttpVersion.getDefault(); // Initialize to
																// the default
																// version

	private SSLRequest sslReq = new SSLRequest();

	private boolean isFollowRedirect = true;
	private boolean isIgnoreResponseBody = false;

	
	public URL getUrl() {
		// TODO Auto-generated method stub
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public HttpMethod getMethod() {
		// TODO Auto-generated method stub
		return method;
	}

	public void setMethod(final HttpMethod method) {
		this.method = method;
	}

	public void addHeader(final String key, final String value) {
		this.headers.put(key, value);
	}

	public MultiValueMap getHeaders() {
		// TODO Auto-generated method stub
		return headers;
	}

	public void addCookie(List<HttpCookie> cookiesList) {
		cookies.addAll(cookiesList);
	}

	public List<HttpCookie> getCookies() {
		// TODO Auto-generated method stub
		return cookies;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public Auth getAuth() {
		// TODO Auto-generated method stub
		return auth;
	}

	public void setBody(final RequestEntity body) {
		this.body = body;
	}

	public RequestEntity getBody() {
		// TODO Auto-generated method stub
		return body;
	}

	public void setHttpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
	}

	public HttpVersion getHttpVersion() {
		// TODO Auto-generated method stub
		return httpVersion;
	}

	public SSLRequest getSslRequest() {
		// TODO Auto-generated method stub
		return sslReq;
	}

	public void setSslRequest(SSLRequest sslReq) {
		this.sslReq = sslReq;
	}

	public boolean isFollowRedirect() {
		// TODO Auto-generated method stub
		return isFollowRedirect;
	}

	public void setFollowRedirect(boolean isFollowRedirect) {
		this.isFollowRedirect = isFollowRedirect;
	}

	public boolean isIgnoreResponseBody() {
		// TODO Auto-generated method stub
		return isIgnoreResponseBody;
	}

	public void setIgnoreResponseBody(boolean isIgnoreResponseBody) {
		this.isIgnoreResponseBody = isIgnoreResponseBody;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("@Request[");
		sb.append(url).append(", ");
		sb.append(method).append(", ");
		sb.append(headers).append(", ");
		sb.append(cookies).append(", ");
		sb.append(body).append(", ");
		sb.append(auth).append(", ");
		sb.append(sslReq).append(", ");
		sb.append(httpVersion).append(", ");
		sb.append(isFollowRedirect).append(", ");
		sb.append(isIgnoreResponseBody).append(", ");
		// sb.append(testScript);
		sb.append("]");
		return sb.toString();
	}
}

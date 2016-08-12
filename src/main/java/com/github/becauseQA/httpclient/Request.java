package com.github.becauseQA.httpclient;

import java.net.HttpCookie;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.github.becauseQA.collections.MultiValueMap;
import com.github.becauseQA.collections.MultiValueMapArrayList;
import com.github.becauseQA.httpclient.bean.HttpMethod;
import com.github.becauseQA.httpclient.bean.HttpVersion;
import com.github.becauseQA.httpclient.bean.IRequestAuth;
import com.github.becauseQA.httpclient.bean.IRequestEntity;
import com.github.becauseQA.httpclient.bean.RequestSSL;

public class Request {

	private URL url;
	private RequestSSL sslReq = new RequestSSL();   //Default ignore all the cert issue ,trust all certs
	private HttpMethod method;
	private HttpVersion httpVersion = HttpVersion.getDefault(); 
	
	private IRequestAuth auth;
	private final MultiValueMap<String, String> headers = new MultiValueMapArrayList<>();
	private final List<HttpCookie> cookies = new ArrayList<HttpCookie>();
	
	private IRequestEntity body;
	
	private boolean isFollowRedirect = true;  //302
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

	@SuppressWarnings("rawtypes")
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

	public void setAuth(IRequestAuth auth) {
		this.auth = auth;
	}

	public IRequestAuth getAuth() {
		// TODO Auto-generated method stub
		return auth;
	}

	public void setBody(final IRequestEntity body) {
		this.body = body;
	}

	public IRequestEntity getBody() {
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

	public RequestSSL getSslRequest() {
		// TODO Auto-generated method stub
		return sslReq;
	}

	public void setSslRequest(RequestSSL sslReq) {
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

	@Override
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

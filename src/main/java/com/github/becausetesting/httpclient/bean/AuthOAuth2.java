package com.github.becausetesting.httpclient.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthOAuth2 implements IRequestAuth {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fullTokenValue;
	private final static Pattern p = Pattern.compile("Bearer\\s(.*)");

	public AuthOAuth2(String token) {
		// TODO Auto-generated constructor stub
		setAuthorizationHeaderValue("Bearer " + token);
	}

	public String getOAuth2BearerToken() {
		String authorizationHeaderValue = getAuthorizationHeaderValue();
		Matcher matcher = p.matcher(authorizationHeaderValue);
		if (matcher.matches()) {
			return matcher.group(1);
		}
		throw new IllegalStateException("OAuth2 Header does not match pattern: " + p);
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPreemptive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getWorkstation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDomain() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAuthorizationHeaderValue(String value) {
		this.fullTokenValue = value;
	}

	@Override
	public String getAuthorizationHeaderValue() {
		// TODO Auto-generated method stub
		return this.fullTokenValue;
	}

}

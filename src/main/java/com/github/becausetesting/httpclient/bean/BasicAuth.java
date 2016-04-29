package com.github.becausetesting.httpclient.bean;

import com.github.becausetesting.encrypt.Base64Utils;

public class BasicAuth implements Auth {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private boolean isPreemptive=false;  //not stored the credentail by default in httpcontext
	
	public BasicAuth(String username,String password,boolean isPreemptive) {
		// TODO Auto-generated constructor stub
		this.username=username;
		this.password=password;
		this.isPreemptive=isPreemptive;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public boolean isPreemptive() {
		// TODO Auto-generated method stub
		return this.isPreemptive;
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

	@Override
	public String getAuthorizationHeaderValue() {
		// TODO Auto-generated method stub
//		return Base64Utils.encryptBasic(this.username+":"+this.password);
		return "";
	}

}

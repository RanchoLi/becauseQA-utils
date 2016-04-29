package com.github.becausetesting.httpclient.bean;

public class NTLMAuth  implements Auth{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String domain;
	private String workstation;
	
	public NTLMAuth(String username,String password,String domain,String workstation) {
		// TODO Auto-generated constructor stub
		this.username=username;
		this.password=password;
		this.domain=domain;
		this.workstation=workstation;
		
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
		return false;
	}

	@Override
	public String getWorkstation() {
		// TODO Auto-generated method stub
		return this.workstation;
	}

	@Override
	public String getDomain() {
		// TODO Auto-generated method stub
		return this.domain;
	}

	@Override
	public String getAuthorizationHeaderValue() {
		// TODO Auto-generated method stub
		return "";
	}

}

package com.github.becausetesting.httpclient.bean;

import java.io.Serializable;

public interface Auth extends Serializable {

	String getUsername();

	String getPassword();
	boolean isPreemptive();
	
	
	String getWorkstation();
	String getDomain();
	
	String getAuthorizationHeaderValue();
}

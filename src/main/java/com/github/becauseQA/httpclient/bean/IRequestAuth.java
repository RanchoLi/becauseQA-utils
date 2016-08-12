package com.github.becauseQA.httpclient.bean;

import java.io.Serializable;

public interface IRequestAuth extends Serializable {

	String getUsername();

	String getPassword();
	boolean isPreemptive();
	
	
	String getWorkstation();
	String getDomain();
	
	String getAuthorizationHeaderValue();
}

package com.github.becausetesting.httpclient.bean;

import java.io.InputStream;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;

public class RequestEntityInputStream extends InputStreamEntity implements RequestEntitySimple  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4882297529167199253L;

	public RequestEntityInputStream(InputStream instream, ContentType contentType) {
		super(instream, contentType);
		// TODO Auto-generated constructor stub
	}

	public RequestEntityInputStream(InputStream instream, long length, ContentType contentType) {
		super(instream, length, contentType);
		// TODO Auto-generated constructor stub
	}

	public RequestEntityInputStream(InputStream instream, long length) {
		super(instream, length);
		// TODO Auto-generated constructor stub
	}

	public RequestEntityInputStream(InputStream instream) {
		super(instream);
		// TODO Auto-generated constructor stub
	}

	
	
}

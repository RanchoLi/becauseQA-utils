package com.github.becauseQA.httpclient.bean;

import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;

public class RequestEntityByteArray  extends ByteArrayEntity implements IRequestEntitySimple {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1411344002540616649L;

	public RequestEntityByteArray(byte[] b, ContentType contentType) {
		super(b, contentType);	
		// TODO Auto-generated constructor stub
	}

	public RequestEntityByteArray(byte[] b, int off, int len, ContentType contentType) {
		super(b, off, len, contentType);
		// TODO Auto-generated constructor stub
	}

	public RequestEntityByteArray(byte[] b, int off, int len) {
		super(b, off, len);
		// TODO Auto-generated constructor stub
	}

	public RequestEntityByteArray(byte[] b) {
		super(b);
		// TODO Auto-generated constructor stub
	}

	
	
}

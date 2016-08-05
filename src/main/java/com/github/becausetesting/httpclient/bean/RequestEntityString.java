package com.github.becausetesting.httpclient.bean;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

public class RequestEntityString extends StringEntity implements IRequestEntitySimple {

	/**
	 * send the request body content
	 */
	private static final long serialVersionUID = -7011737982426984773L;

	public RequestEntityString(String string, Charset charset) {
		super(string, charset);
		// TODO Auto-generated constructor stub
	}

	public RequestEntityString(String string, ContentType contentType) throws UnsupportedCharsetException {
		super(string, contentType);
		// TODO Auto-generated constructor stub
	}

	public RequestEntityString(String string, String charset) throws UnsupportedCharsetException {
		super(string, charset);
		// TODO Auto-generated constructor stub
	}

	/*
	 * text/plain Default is json data
	 */
	public RequestEntityString(String string) throws UnsupportedEncodingException {
		super(string, ContentType.APPLICATION_JSON);
		// TODO Auto-generated constructor stub
	}

}

package com.github.becausetesting.httpclient.bean;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

public class RequestEntityFormData extends UrlEncodedFormEntity implements RequestEntitySimple {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7011737982426984773L;

	public RequestEntityFormData(Iterable<? extends NameValuePair> parameters, Charset charset) {
		super(parameters, charset);
		// TODO Auto-generated constructor stub
	}

	public RequestEntityFormData(Iterable<? extends NameValuePair> parameters) {
		super(parameters);
		// TODO Auto-generated constructor stub
	}

	public RequestEntityFormData(List<? extends NameValuePair> parameters, String charset)
			throws UnsupportedEncodingException {
		super(parameters, charset);
		// TODO Auto-generated constructor stub
	}

	public RequestEntityFormData(List<? extends NameValuePair> parameters) throws UnsupportedEncodingException {
		super(parameters,Charset.forName("UTF-8"));
		// TODO Auto-generated constructor stub
	}

}

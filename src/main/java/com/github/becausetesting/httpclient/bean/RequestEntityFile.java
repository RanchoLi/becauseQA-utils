package com.github.becausetesting.httpclient.bean;

import java.io.File;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;

public class RequestEntityFile extends FileEntity implements RequestEntitySimple {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6669598679387306399L;

	public RequestEntityFile(File file, ContentType contentType) {
		super(file, contentType);
		// TODO Auto-generated constructor stub
	}

	public RequestEntityFile(File file) {
		super(file);
		// TODO Auto-generated constructor stub
	}

	
	
}
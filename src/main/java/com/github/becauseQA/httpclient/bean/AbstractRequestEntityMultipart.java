package com.github.becauseQA.httpclient.bean;

import java.util.List;

public class AbstractRequestEntityMultipart implements IRequestEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2682961872599839381L;

	public enum MultipartMode {
		BROWSER_COMPATIBLE, RFC_6532, STRICT;
	}

	public enum MultipartSubtype {
		FORM_DATA, MIXED, DIGEST, MESSAGE, ALTERNATIVE, RELATED, REPORT, SIGNED, ENCRYPTED, X_MIXED_REPLACE, BYTERANGE;
		@Override
		public String toString() {
			return this.name().toLowerCase().replaceAll("_", "-");
		}
	}

	public MultipartSubtype getSubtype() {
		return null;
	}

	public MultipartMode getMode() {
		return null;
	}
	
	public List<IRequestEntitySimple> getBody(){
		
		return null;
	}
}

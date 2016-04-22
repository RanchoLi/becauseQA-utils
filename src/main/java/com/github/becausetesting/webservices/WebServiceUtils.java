package com.github.becausetesting.webservices;

import java.net.URL;
import java.util.Map;

public interface WebServiceUtils {

	public void doget(URL url,Map<String, String> headers);
	public void doPost(URL url,Map<String, String> headers,Map<String, Object> data) ;
	
}

package com.github.becausetesting.webservices;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import com.github.becausetesting.http.HttpUtils;

public class RESTFulUtils extends HttpUtils implements WebServiceUtils {

	@Override
	public void doget(URL url,Map<String,String> headers) {
		// TODO Auto-generated method stub
		try {
			getRequest(url, headers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(URL url,Map<String, String> headers,Map<String, Object> data) {
		// TODO Auto-generated method stub
		try {
			postRequest(url, headers, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	

}

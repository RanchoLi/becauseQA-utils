package com.github.becausetesting.httpclient;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.github.becausetesting.httpclient.bean.BasicAuth;
import com.github.becausetesting.httpclient.bean.HttpMethod;
import com.github.becausetesting.httpclient.bean.RequestEntityFormData;

public class HttpClientRequesterTest {

	@Test
	public void testExecute() throws IOException {
		HttpClientUtils clientRequester=new HttpClientUtils();
		
		Request request = new Request();
		request.setUrl(new URL("https://www.attheregister.com/moneypak/login"));
		request.setMethod(HttpMethod.GET);		
		clientRequester.getResponse(request);
		
		request.setMethod(HttpMethod.POST);
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();	
		nameValuePairs.add(new BasicNameValuePair("__RequestVerificationToken", "Xo5qlYqnsbYMkPovIQ792WrxArwfPxU_WyqBPYW-fD7KLSezu21D0zEz_Re95drCHiWusRcvbBtwz-54tR2CJo2gTT41"));
		nameValuePairs.add(new BasicNameValuePair("MobileNumber", "(626) 586-1514"));
		nameValuePairs.add(new BasicNameValuePair("Password", "MPTest@123"));
		nameValuePairs.add(new BasicNameValuePair("BlackBoxData", ""));
		nameValuePairs.add(new BasicNameValuePair("BlackBoxData", ""));
		RequestEntityFormData formData=new RequestEntityFormData(nameValuePairs);
		request.setBody(formData);
		
		Response response = clientRequester.getResponse(request);
		
		System.out.println(new String(response.getResponseBody(),Charset.forName("UTF-8")));
		
		
		
	}

}

package com.github.becausetesting.httpclient;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.github.becausetesting.apache.commons.ArrayUtils;
import com.github.becausetesting.apache.commons.IOUtils;
import com.github.becausetesting.apache.commons.ListUtils;
import com.github.becausetesting.apache.commons.StringEscapeUtils;
import com.github.becausetesting.apache.commons.StringUtils;
import com.github.becausetesting.httpclient.bean.BasicAuth;
import com.github.becausetesting.httpclient.bean.HttpMethod;
import com.github.becausetesting.httpclient.bean.RequestEntityFormData;
import com.github.becausetesting.httpclient.bean.RequestEntityString;

public class HttpClientRequesterTest {

	private static Logger logger = Logger.getLogger(HttpClientRequesterTest.class);

	@Test
	public void testFormData302Response() throws IOException {
		HttpClientUtils clientRequester = new HttpClientUtils();

		Request request = new Request();
		request.setUrl(new URL("https://www.attheregister.com/moneypak/login"));
		request.setMethod(HttpMethod.GET);
		// clientRequester.getResponse(request);

		request.setMethod(HttpMethod.POST);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("__RequestVerificationToken",
				"Xo5qlYqnsbYMkPovIQ792WrxArwfPxU_WyqBPYW-fD7KLSezu21D0zEz_Re95drCHiWusRcvbBtwz-54tR2CJo2gTT41"));
		nameValuePairs.add(new BasicNameValuePair("MobileNumber", "(626) 586-1514"));
		nameValuePairs.add(new BasicNameValuePair("Password", "MPTest@123"));
		nameValuePairs.add(new BasicNameValuePair("BlackBoxData", ""));
		nameValuePairs.add(new BasicNameValuePair("BlackBoxData", ""));
		RequestEntityFormData formData = new RequestEntityFormData(nameValuePairs);
		request.setBody(formData);

		Response response = HttpClientUtils.getResponse(request);

		System.out.println(new String(response.getResponseBody(), Charset.forName("UTF-8")));

	}

	@Test
	public void testRestFul() throws FileNotFoundException, IOException {
		Request request = new Request();
		request.setUrl(new URL("https://partners.greendotcorp.com/Disbursements/api/v1/AuthorizedPing"));
		request.setMethod(HttpMethod.GET);

		request.addHeader("accept", "application/json");
		request.addHeader("Host", "partners.greendotcorp.com");
		request.addHeader("Authorization", "Basic ZDJjdGVzdHVzZXI6WFBPN2QzQjAzTVFmMFN6bTFWa0RJNkVVM1djPQ==");
		request.addHeader("endusersecurityid", "100tester");
		request.addHeader("enduserip", "127.0.0.1");
		request.addHeader("requestid", "2015232323");
		request.addHeader("partneridentifier", "Direct2CashTest");

		InputStream openStream = getClass().getResource("/jsonData.json").openStream();
		String jsonData = IOUtils.toString(openStream);

		ContentType contentType = ContentType.APPLICATION_JSON;
		RequestEntityString entityString = new RequestEntityString(jsonData, contentType);
		// request.setBody(entityStrin和g);
		Response response = HttpClientUtils.getResponse(request);
	}

	@Test
	public void testSoap() throws IOException {

		Request request = new Request();

		request.setUrl(new URL("http://gdcdevipscom01/PANSerialNumberConverter/Converter.svc"));
		request.setMethod(HttpMethod.POST);
		request.addHeader("SOAPAction", "http://tempuri.org/IPanSerialNumberConverter/Decrypt");
		request.addHeader("Host", "gdcdevipscom01");
		ContentType contentType = ContentType.create("text/xml", Consts.UTF_8);
		InputStream resourceAsStream = getClass().getResourceAsStream("/xmlData.xml");
		String xmlData = IOUtils.toString(resourceAsStream);

		RequestEntityString entityString = new RequestEntityString(xmlData, contentType);

		request.setBody(entityString);

		Response response = HttpClientUtils.getResponse(request);

		byte[] responseBody = response.getResponseBody();
		String string = new String(responseBody, Charset.forName("UTF-8"));
		logger.info(string);

	}

}

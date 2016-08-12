package com.github.becauseQA.httpclient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import com.github.becauseQA.apache.commons.IOUtils;
import com.github.becauseQA.httpclient.bean.AuthOAuth2;
import com.github.becauseQA.httpclient.bean.HttpMethod;
import com.github.becauseQA.httpclient.bean.RequestEntityFormData;
import com.github.becauseQA.httpclient.bean.RequestEntityString;

public class HttpClientRequesterTest {

	private static Logger log = Logger.getLogger(HttpClientRequesterTest.class);

	@Test
	@Ignore
	public void testFormData302Response() throws IOException {
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

		request.setFollowRedirect(true);
		Response response = HttpClientUtils.execute(request);

		System.out.println(new String(response.getResponseBody(), Charset.forName("UTF-8")));

	}

	@Test
	public void testSSLRestFulAuth2AndJsonData() throws FileNotFoundException, IOException {
		Request request = new Request();
		request.setUrl(new URL("https://api.greendotonline.com/SecuredCard/CreateRegistration"));
		request.setMethod(HttpMethod.POST);
	
		request.addHeader("content-type", "application/json");
		request.addHeader("GDCorrelationId", "awBne7qZbaLqaKsV1j37e2WVwEN1K54oGzKHqcpvF4tzIfvYRWMeK3KAqWusP37w");
		request.addHeader("GDSessionId", "VxnYyFHHRMAdd28COy8dTWYt7GsWe7YQhymWl1jLZpQWxu7NXkdg848b3swjWUA5");

		AuthOAuth2 oAuth2 = new AuthOAuth2(
				"yMbMKQhlPi2UcoZ9Xjk7Et-M2q5cEMm4XAN9Dxoml7zeXidQ9egEJhy7c2bjaeS-wBFB_au27b08wrLas7_J5wUcSzvOMSTDncPb4kt-8sE2m_gM7SuZ1gxLQJbQhwLQp-gpH2guKnPaJbhSbAptFM0zO2uENyUXs2IC4sKTsTFILfQxx-aJVIl7MA0A6Ez8n2yDv9NTR2LtZAx0D9AvkD1Hwaqu-vvMT0DjO1BGHcOHilY0WXdo13aZ4t20JzFe9vpdy7FlQEBO0dWQXoCDWXyNRnJDz99B2_EiMOLk7UhsSIsAb6PwxIDbA4K_hB55tylZASRj1-22LG-dZZkM13vMhzdVysYpr50o6V0FNK9iKbY5idcVyLU_PEzKcSfmKEQaVa63WltcUk0cz_jvEA");
		request.setAuth(oAuth2);
		
		InputStream openStream = getClass().getResource("/jsonData.json").openStream();
		String jsonData = IOUtils.toString(openStream);

		ContentType contentType = ContentType.APPLICATION_JSON;
		RequestEntityString requestEntityString = new RequestEntityString(jsonData, contentType);
		
		request.setBody(requestEntityString);
		Response response = HttpClientUtils.execute(request);
		log.info(new String(response.getResponseBody(),Charset.forName("UTF-8")));

	}

	@Test
	@Ignore
	public void testRestFul() throws FileNotFoundException, IOException {
		Request request = new Request();
		request.setUrl(new URL("https://partners.greendotcorp.com/Disbursements/api/v1/AuthorizedPing"));
		request.setMethod(HttpMethod.POST);

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
		new RequestEntityString(jsonData, contentType);
		// request.setBody(entityStrin和g);
		HttpClientUtils.execute(request);
	}

	@Test
	@Ignore
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

		Response response = HttpClientUtils.execute(request);

		byte[] responseBody = response.getResponseBody();
		String string = new String(responseBody, Charset.forName("UTF-8"));
		log.info(string);

	}

}

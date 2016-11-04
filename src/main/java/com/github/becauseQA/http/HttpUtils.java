/**
 * Project Name:commons
 * File Name:HttpUtils.java
 * Package Name:com.github.becauseQA.url
 * Date:2016年4月16日下午6:52:01
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becauseQA.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.becauseQA.encrypt.Base64Utils;
import com.github.becauseQA.httpclient.bean.HttpMethod;
import com.github.becauseQA.json.JSONUtils;

/**
 * ClassName:HttpUtils Date: 2016年4月16日 下午6:52:01
 * 
 * @author Administrator
 * @version 1.0.0
 * @since JDK 1.8
 */
public class HttpUtils {

	private static Logger log = Logger.getLogger(HttpUtils.class);
	private static HttpURLConnection connection;

	public static String getRequestAsString(URL url, Map<String, String> headers) throws IOException {
		HttpsCert.ignoreCert();
		getConnection(url, HttpMethod.GET);
		setHeaders(headers);
		String response = getResponseString();
		return response;

	}

	public static InputStream getRequestAsInputStream(URL url, Map<String, String> headers) throws IOException {
		HttpsCert.ignoreCert();
		getConnection(url, HttpMethod.GET);
		setHeaders(headers);
		InputStream response = getResponseStream();
		return response;

	}

	public static String postRequestAsString(URL url, Map<String, String> headers, Map<String, Object> data)
			throws IOException {
		HttpsCert.ignoreCert();
		getConnection(url, HttpMethod.POST);
		setHeaders(headers);
		postFormData(data);
		String response = getResponseString();
		return response;

	}

	public static String postRequestAsString(URL url, Map<String, String> headers, Object data) throws IOException {
		HttpsCert.ignoreCert();
		getConnection(url, HttpMethod.POST);
		setHeaders(headers);
		postJsonData(data);
		String response = getResponseString();
		return response;

	}

	public static InputStream postRequestAsInputstream(URL url, Map<String, String> headers, Map<String, Object> data)
			throws IOException {
		HttpsCert.ignoreCert();
		getConnection(url, HttpMethod.POST);
		setHeaders(headers);
		postFormData(data);
		InputStream response = getResponseStream();
		return response;

	}

	public static InputStream postRequestAsInputstream(URL url, Map<String, String> headers, Object data)
			throws IOException {
		HttpsCert.ignoreCert();
		getConnection(url, HttpMethod.POST);
		setHeaders(headers);
		postJsonData(data);
		InputStream response = getResponseStream();
		return response;

	}

	public static void getConnection(URL url, HttpMethod method) {
		// HttpURLConnection connection = null;
		try {
			//log.info("Request url: " + url.toString());
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method.name());
			// connection.setDoOutput(true);
			// connection.setDoInput(true);
			connection.setUseCaches(false);

			connection.setConnectTimeout(50000);
			connection.setReadTimeout(6000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setHeaders(Map<String, String> headers) {
		if (headers != null) {
			for (String key : headers.keySet()) {
				String value = headers.get(key);
				connection.setRequestProperty(key, value);
			}
		}
	}

	/**
	 * setAuthorizationHeader:
	 * 
	 * @author alterhu2020@gmail.com
	 * @param username
	 *            username.
	 * @param password
	 *            password.
	 * @since JDK 1.8
	 */
	public static void setAuthorizationHeader(Map<String, String> header, String username, String password) {
		// Base64Utils base64 = new Base64Utils();
		String userpass = username + ":" + password;
		String basicAuth = Base64Utils.encryptBasic(userpass);
		header.put("Authorization", "Basic " + basicAuth);
	}

	public static void postFormData(Map<String, Object> data) {
		connection.setDoOutput(true);
		try {
			OutputStream outputStream = connection.getOutputStream();
			if (data instanceof Map) {

			} else {

			}
			StringBuffer sb = new StringBuffer();
			for (String key : data.keySet()) {
				Object parametervalue = data.get(key);
				sb.append(key).append("=").append(parametervalue).append("&");
			}
			String params = sb.substring(0, sb.length() - 1);
			byte[] bytes = params.toString().getBytes("UTF-8");
			outputStream.write(bytes);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public static void postJsonData(Object data) {
		connection.setDoOutput(true);
		try {
			OutputStream outputStream = connection.getOutputStream();
			String jsonString = JSONUtils.fromObject(data);
			//log.info("HTTP Sending Json Data: \n" + jsonString);
			byte[] bytes = jsonString.getBytes("UTF-8");

			outputStream.write(bytes);
			outputStream.flush();
			// outputStream.close();
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public static InputStream getResponseStream() {
		int responseCode = 0;
		InputStream inputStream = null;
		//String responseMessage = null;
		try {
			//responseMessage = connection.getResponseMessage();
			inputStream = connection.getInputStream();
			responseCode = connection.getResponseCode();
			// if (responseCode !=
			// HttpURLConnection.HTTP_OK||responseCode!=HttpURLConnection.HTTP_CREATED)
			// {
			if (inputStream == null) {
				inputStream = connection.getErrorStream();
				if (inputStream != null) {
					throw new Exception("Http Response Inputstream,response code: " + responseCode);
				}
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Http Response Exception:",e);
		}
		return inputStream;

	}

	public static String getResponseString() {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		InputStream responseStream = null;
		responseStream = getResponseStream();
		if (responseStream != null) {
			reader = new BufferedReader(new InputStreamReader(responseStream));
			String tempLine = null;
			try {
				while ((tempLine = reader.readLine()) != null) {
					sb.append(tempLine + System.getProperty("line.separator"));
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("Http Server Response Exception:", e);
			}finally {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		String responseContent = sb.toString();
		//log.info("Response Content is:\n" + responseContent);
		return responseContent;
	}
}

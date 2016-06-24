/**
 * Project Name:commons
 * File Name:HttpUtils.java
 * Package Name:com.github.becausetesting.url
 * Date:2016年4月16日下午6:52:01
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpConnection;
import org.apache.log4j.Logger;

import com.github.becausetesting.encrypt.Base64Utils;
import com.github.becausetesting.json.JSONUtils;

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

	public static String getRequest(URL url, Map<String, String> headers) throws IOException {
		HttpsCert.ignoreCert();
		getConnection(url, "GET");
		setHeaders(headers);
		String response = getResponse();
		return response;

	}

	public static InputStream getResponse(URL url, Map<String, String> headers) throws IOException {
		HttpsCert.ignoreCert();
		getConnection(url, "GET");
		setHeaders(headers);
		InputStream response = getResponseStream();
		return response;

	}

	public static String postRequest(URL url, Map<String, String> headers, Map<String, Object> data)
			throws IOException {
		HttpsCert.ignoreCert();
		getConnection(url, "POST");
		setHeaders(headers);
		postData(data);
		String response = getResponse();
		return response;

	}

	public static void getConnection(URL url, String method) {
		// HttpURLConnection connection = null;
		try {
			log.info("Request url: " + url.toString());
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
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
	public static void setAuthorizationHeader(String username, String password) {
		// Base64Utils base64 = new Base64Utils();
		String userpass = username + ":" + password;
		String basicAuth = Base64Utils.encryptBasic(userpass);
		Map<String, String> header = new HashMap<>();
		header.put("Authorization", "Basic " + basicAuth);
		setHeaders(header);
	}

	private static void postData(Map<String, Object> data) {
		connection.setDoOutput(true);
		try {
			OutputStream outputStream = connection.getOutputStream();
			StringBuffer sb = new StringBuffer();
			for (String key : data.keySet()) {
				Object parametervalue = data.get(key);
				sb.append(key).append("=").append(parametervalue).append("&");
			}
			String params = sb.substring(0, sb.length() - 1);
			byte[] bytes = params.toString().getBytes();
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
			JSONUtils jsonUtils = new JSONUtils();
			String jsonString = JSONUtils.fromObject(data);
			log.info("Json Data: \n" + jsonString);
			byte[] bytes = jsonString.getBytes("UTF-8");

			outputStream.write(bytes);
			outputStream.flush();
			//outputStream.close();
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public static InputStream getResponseStream() {
		int responseCode = 0;
		InputStream inputStream = null;
		String responseMessage = null;
		try {
			responseMessage = connection.getResponseMessage();
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Response message:\n" + responseMessage, e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputStream;

	}

	public static String getResponse() {
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
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String responseContent = sb.toString();
		log.info("Response Content is:\n" + responseContent);
		return responseContent;
	}
}

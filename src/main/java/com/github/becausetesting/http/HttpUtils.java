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

import com.github.becausetesting.encrypt.Base64Utils;
import com.github.becausetesting.json.JSONUtils;

/**
 * ClassName:HttpUtils Function: TODO ADD FUNCTION. Reason: TODO ADD REASON.
 * Date: 2016年4月16日 下午6:52:01
 * 
 * @author Administrator
 * @version
 * @since JDK 1.8
 * @see
 */
public class HttpUtils {

	private HttpURLConnection connection;

	public String getRequest(URL url,Map<String, String> headers) throws IOException {
		HttpsCert.ignoreCert();
		getConnection(url, "GET");
		setHeaders(headers);
		String response = getResponse();
		return response;

	}

	public String postRequest(URL url,Map<String, String> headers, Map<String, Object> data) throws IOException {
		HttpsCert.ignoreCert();
		getConnection(url, "POST");
		setHeaders(headers);
		postData(data);
		String response = getResponse();
		return response;

	}

	public void getConnection(URL url, String method) {
		// HttpURLConnection connection = null;
		try {
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			this.connection = connection;
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

	public void setHeaders(Map<String, String> headers) {
		for (String key : headers.keySet()) {
			String value = headers.get(key);
			connection.setRequestProperty(key, value);
		}

	}
	public void setAuthorizationHeader(String username, String password) {
		Base64Utils base64 = new Base64Utils();
		String userpass = username + ":" + password;
		String basicAuth = base64.encrypt(userpass);
		Map<String, String> header = new HashMap<>();
		header.put("Authorization", "Basic " + basicAuth);
		setHeaders(header);
	}

	public void postData(Map<String, Object> data) {
		connection.setDoOutput(true);
		try {
			OutputStream outputStream = this.connection.getOutputStream();
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
	public void postJsonData(Object data) {
		connection.setDoOutput(true);
		try {
			OutputStream outputStream = connection.getOutputStream();
			JSONUtils jsonUtils = new JSONUtils();
			byte[] bytes = jsonUtils.fromObject(data).getBytes("UTF-8");
			
			outputStream.write(bytes);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	

	public String getResponse(){
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;

		int responseCode = 0;
		try {
			responseCode = connection.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}

		InputStream inputStream = null;
		try {
			inputStream = connection.getInputStream();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				inputStream = connection.getErrorStream();
				if (inputStream != null) {
					throw new Exception("Http Response Inputstream,response code : " + responseCode);
				}
			} else {
				reader = new BufferedReader(new InputStreamReader(inputStream));
				String tempLine = null;
				while ((tempLine = reader.readLine()) != null) {
					sb.append(tempLine + System.getProperty("line.separator"));
				}
				reader.close();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} 
		return sb.toString();
	}
}

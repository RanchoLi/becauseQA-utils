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
import java.net.URL;
import java.util.Map;

import com.github.becausetesting.encrypt.Base64Utils;

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
	
	public String doGetResponse(URL url) throws IOException {
		HttpsCert.ignoreCert();
		getConnection(url, "GET");
		String response = getResponse();
		return response;

	}
	public String doPostResponse(URL url, Map<String,String> data) throws IOException {
		HttpsCert.ignoreCert();
		getConnection(url, "POST");
		setParameters(data);
		String response = getResponse();
		return response;

	}

	
	protected void getConnection(URL url, String method) {
		//HttpURLConnection connection = null;
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			//connection.setDoOutput(true);
//			connection.setDoInput(true);
			connection.setUseCaches(false);

			connection.setRequestProperty("Accept-Charset", "utf-8");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Connection", "Keep-Alive");
			
			connection.setConnectTimeout(50000);
			connection.setReadTimeout(6000);
			this.connection=connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void setParameters(Map<String,String> data){
		this.connection.setDoOutput(true);
		try {
			OutputStream outputStream = this.connection.getOutputStream();
			StringBuffer sb = new StringBuffer();
			for(String key:data.keySet()){
				String parametervalue = data.get(key);
				sb.append(key).append("=").append(parametervalue).append("&");
			}
			
			String params=sb.substring(0, sb.length()-1);
			byte[] bytes = params.toString().getBytes();
			outputStream.write(bytes);
			outputStream.close();
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	private void setAuthorizationHeader(String username,String password){
		
		String userpass = username + ":" + password;	
		Base64Utils base64 = new Base64Utils();
		String basicAuth =base64.encrypt(userpass); 
		connection.addRequestProperty("Authorization", "Basic " + basicAuth);
		/*
		 * 
		byte[] userpassbytes = userpass.getBytes();
		final char[] map = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
				'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
				'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
				'6', '7', '8', '9', '+', '/' };

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < userpassbytes.length; i++) {
			byte b0 = userpassbytes[i++], b1 = 0, b2 = 0;

			int bytes = 3;
			if (i < buffer.length) {
				b1 = buffer[i++];
				if (i < buffer.length) {
					b2 = buffer[i];
				} else {
					bytes = 2;
				}
			} else {
				bytes = 1;
			}

			int total = (b0 << 16) | (b1 << 8) | b2;

			switch (bytes) {
			case 3:
				sb.append(map[(total >> 18) & 0x3f]);
				sb.append(map[(total >> 12) & 0x3f]);
				sb.append(map[(total >> 6) & 0x3f]);
				sb.append(map[total & 0x3f]);
				break;

			case 2:
				sb.append(map[(total >> 18) & 0x3f]);
				sb.append(map[(total >> 12) & 0x3f]);
				sb.append(map[(total >> 6) & 0x3f]);
				sb.append('=');
				break;

			case 1:
				sb.append(map[(total >> 18) & 0x3f]);
				sb.append(map[(total >> 12) & 0x3f]);
				sb.append('=');
				sb.append('=');
				break;
			}

		}
		*/
		
		
	}
	protected String getResponse() throws IOException {
		StringBuilder sb = new StringBuilder();

		BufferedReader reader = null;
		InputStream inputStream = null;
		try {

			int responseCode = this.connection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new Exception("HTTP Request is not success, Response code is " + responseCode);
			}

			// get the response content
			inputStream = this.connection.getInputStream();

			reader = new BufferedReader(new InputStreamReader(inputStream));
			String tempLine = null;
			while ((tempLine = reader.readLine()) != null) {
				sb.append(tempLine + "\n");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {

			if (reader != null) {
				reader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}

		}
		return sb.toString();
	}
}

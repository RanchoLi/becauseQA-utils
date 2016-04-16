package com.github.becausetesting.encrypt;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Utils {

	public String encrypt(String password) {
		// String to bytes array
		String encryptstr = null;
		try {
			encryptstr = Base64.getEncoder().encodeToString(password.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptstr;
	}
	
	public String decrypt(String base64password){
		String decryptstr=null;
		byte[] decodebyte = Base64.getDecoder().decode(base64password);
		try {
			decryptstr=new String(decodebyte,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decryptstr;
	}
	
	public String encryptURL(String url){
		String encryptstr=null;
		try {
			encryptstr=Base64.getUrlEncoder().encodeToString(url.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptstr;
	}
	public String decryptURL(String urlencoded){
		String decryptstr=null;
		try {
			byte[] decode = Base64.getUrlDecoder().decode(urlencoded);
			decryptstr=new String(decode,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decryptstr;
	}
}

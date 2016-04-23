package com.github.becausetesting.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * ClassName: Base64Utils  
 * Function: TODO ADD FUNCTION.  
 * Reason: TODO ADD REASON 
 * date: Apr 22, 2016 11:14:20 PM  
 *
 * @author Administrator
 * @version 
 * @since JDK 1.8
 */
public class Base64Utils {

	/**
	 * encrypt: 
	 * TODO
	 * TODO
	 * TODO 
	 * TODO 
	 *
	 * @author Administrator
	 * @param password
	 * @return
	 * @since JDK 1.8
	 */
	public static String encryptBasic(String password) {
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
	
	/**
	 * decrypt: 
	 * TODO
	 * TODO
	 * TODO 
	 * TODO 
	 *
	 * @author Administrator
	 * @param base64password
	 * @return
	 * @since JDK 1.8
	 */
	public static String decryptBasic(String base64password){
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
	
	public static String encryptURL(String url){
		String encryptstr=null;
		try {
			encryptstr=Base64.getUrlEncoder().encodeToString(url.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptstr;
	}
	public static String decryptURL(String urlencoded){
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
	
	/*
	 * @author Administrator
	 * @param type
	 * @param originstr
	 * @return
	 * @since JDK 1.8
	 */
	protected static String Encrypt(String type,String originstr){
		
		MessageDigest messageDigest = null;
		StringBuffer hexValue = new StringBuffer();
		try {
			byte[] bytes = originstr.getBytes("UTF-8");
			messageDigest = MessageDigest.getInstance(type);
			messageDigest.update(bytes);
			
			byte[] md5Bytes = messageDigest.digest(bytes);
			for (int i = 0; i < md5Bytes.length;) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
				return hexValue.toString();
			}

		} catch (NoSuchAlgorithmException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (UnsupportedEncodingException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return "";
		
	}
	/**
	 * md5: 
	 * TODO
	 * TODO
	 * TODO 
	 * TODO 
	 *
	 * @author Administrator
	 * @param originstr
	 * @return
	 * @since JDK 1.8
	 */
	public static String encryptMD5(String originstr) {
		return Encrypt("MD5", originstr);
	}

	/*
	 * sha1 >md5
	 */
	public static String EncryptSHA1(String originstr) {
		return Encrypt("SHA-1", originstr);
	}

	public String descryptMD5(String md5) {
		char[] a = md5.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}
}

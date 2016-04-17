/**
 * Project Name:commons
 * File Name:Md5Utils.java
 * Package Name:com.github.becausetesting.encrypt
 * Date:Apr 17, 20165:46:05 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName:Md5Utils Function: TODO ADD FUNCTION. Reason: TODO ADD REASON.
 * Date: Apr 17, 2016 5:46:05 PM
 * 
 * @author Administrator
 * @version
 * @since JDK 1.8
 * @see
 */
public class Md5Utils {

	/*
	 * char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	 * 'a', 'b', 'c', 'd', 'e', 'f' };
	 */
	
	protected String Encrypt(String type,String originstr){
		
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
	public String md5(String originstr) {
		return Encrypt("MD5", originstr);
	}

	/*
	 * sha1 >md5
	 */
	public String sha1(String originstr) {
		return Encrypt("SHA-1", originstr);
	}

	public String md5descrypt(String md5) {
		char[] a = md5.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}


}

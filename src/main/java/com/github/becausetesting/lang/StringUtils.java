package com.github.becausetesting.lang;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtils {

	/**
	 * Determines if the string is null or empty.
	 *
	 * @param s
	 *            The string to test
	 * @return true if the string is null or empty.
	 */
	public static boolean isNullOrEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	private static String byte2String(byte[] bytes){
		return new String(bytes);
	}
	public static byte[] string2Bytes(String str) {
		return str.getBytes(Charset.forName("UTF-8"));
	}

	public static char[] String2Array(String str) {
		return str.toCharArray();
	}

	public static String stringFormat(String str, String format) {
		return String.format(format, str);
	}

	
	public static String getRandomString(int len) {
		String result = "";
		String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // 9
		int n = alphabet.length(); // 10
		for (int k = 0; k < len; k++) {
			result = result + alphabet.charAt(new SecureRandom().nextInt(n)); // 13
		}
		return result;

	}

	public static List<String> split(String str, String seperate) {
		List<String> list=new ArrayList<String>();
		StringTokenizer stringTokenizer = new StringTokenizer(str, seperate);
		int count = 0;
		while (stringTokenizer.hasMoreTokens()) {
			String seperateStr = stringTokenizer.nextToken();
			list.add(seperateStr);
		}
	
		return list;
	}
	/*
	 * StringBuilder >1.5 StringBuffer before StringBuilder StringBuilder not
	 * thread safe no synchronized
	 * 
	 * 
	 * StringBuilder is faster than StringBuffer if not mulitply thread use
	 * StringBuffer
	 * 
	 * String<StringBuffer<StringBuilder
	 * 
	 * 
	 * 
	 */
}

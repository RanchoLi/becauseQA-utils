package com.github.becausetesting.string;

import java.nio.charset.Charset;
import java.security.SecureRandom;

public class StringUtils {

	
	

    /**
     * Determines if the string is null or empty.
     *
     * @param s
     *            The string to test
     * @return true if the string is null or empty.
     */
    public static boolean isNullOrEmpty(String s)
    {
        return s == null ||s.trim().length()==0;
    }

    public byte[] string2Bytes(String str){
    	return str.getBytes(Charset.forName("UTF-8"));
    }
    
    public char[] String2Array(String str){
    	return str.toCharArray();
    }
    
    public String stringFormat(String str,String format){
    	return String.format(format, str);
    }
    
    public String getRandomString(int len){
		String result="";
		String alphabet ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; //9
		int n = alphabet.length(); //10
		for(int k=0;k<len;k++){
			 result = result + alphabet.charAt(new SecureRandom().nextInt(n)); //13
		}
		return result;
		
	}
    /*
     *  StringBuilder >1.5
     *  StringBuffer before StringBuilder
     *  StringBuilder not thread safe no synchronized
     *  
     *  
     *  StringBuilder is faster than StringBuffer
     *  if not mulitply thread use StringBuffer
     *  
     *  String<StringBuffer<StringBuilder
     *  
     *  
     * 
     */
}

/**
 * Project Name:commons
 * File Name:RegexpUtils.java
 * Package Name:com.github.becausetesting.regexp
 * Date:Apr 16, 201610:52:32 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.regexp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:RegexpUtils Function: http://www.vogella.com/tutorials/JavaRegularExpressions/article.html#regex-examples
 * Date: Apr 16, 2016 10:52:32 PM
 * 
 * @author Administrator
 * @version 1.0.0
 * @since JDK 1.8
 */
public class RegexpUtils {

	private static Pattern pattern;
	private static Matcher matcher;
	/*
	 * Dotall mode can also be enabled via the embedded flag expression (?s). 
	 * (The s is a mnemonic for "single-line" mode, which is what this is called in Perl.)
	 */

	public static boolean validate(final String inputtester, String patterns) {
		pattern = Pattern.compile(patterns, Pattern.DOTALL);
		matcher = pattern.matcher(inputtester);
		return matcher.matches();

	}

	public static String validateString(final String inputtester, String patterns) {
		pattern = Pattern.compile(patterns, Pattern.DOTALL);
		matcher = pattern.matcher(inputtester);
		String matchedstr = "";
		while (matcher.find()) {
			matchedstr = matchedstr + matcher.group();
			// System.out.println("Matched content is:"+matcher.group());
		}

		return matchedstr;
	}

	public static List<String> validateStrings(final String inputtester,String patterns) {
		pattern=Pattern.compile(patterns, Pattern.DOTALL);
		matcher = pattern.matcher(inputtester);
		List<String> matchers=new ArrayList<String>();
		while (matcher.find()) {
			String matchedstr = matcher.group();
			matchers.add(matchedstr);
			// System.out.println("Matched content is:"+matcher.group());
		}

		return matchers;
	}
}

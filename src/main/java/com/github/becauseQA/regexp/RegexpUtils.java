/**
 * Project Name:commons
 * File Name:RegexpUtils.java
 * Package Name:com.github.becauseQA.regexp
 * Date:Apr 16, 201610:52:32 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becauseQA.regexp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:RegexpUtils Function:
 * http://www.vogella.com/tutorials/JavaRegularExpressions/article.html#regex-examples
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
	 * oneline modal
	 * Dotall mode can also be enabled via the embedded flag expression (?s).
	 * (The s is a mnemonic for "single-line" mode, which is what this is called
	 * 
	 * in Perl.)
	 */

	public static boolean validate(final String inputtester, String expressionPatterns) {
		pattern = Pattern.compile(expressionPatterns);
		matcher = pattern.matcher(inputtester);
		return matcher.matches();

	}

	public static String matchText(final String content, String expressionPatterns) {
		pattern = Pattern.compile(expressionPatterns);
		matcher = pattern.matcher(content);
		String matchedstr = "";
		while (matcher.find()) {
			matchedstr = matchedstr + matcher.group();
			// System.out.println("Matched content is:"+matcher.group());
		}

		return matchedstr;
	}

	public static List<String> matchTextList(final String content, String expressionPatterns) {
		pattern = Pattern.compile(expressionPatterns, Pattern.DOTALL);
		matcher = pattern.matcher(content);
		List<String> matcherList = new ArrayList<String>();
		int size = matcher.groupCount();
		while (matcher.find()) {
			for (int k = 0; k <= size; k++) {
				String groupContent = matcher.group(k);
				matcherList.add(groupContent);
			}
		}
		return matcherList;
	}
}

package com.github.becausetesting.string;

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

}

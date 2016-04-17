/**
 * Project Name:commons
 * File Name:DOM4JUtilsTest.java
 * Package Name:com.github.becausetesting.xml
 * Date:Apr 16, 20169:57:06 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.xml;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * ClassName:DOM4JUtilsTest  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     Apr 16, 2016 9:57:06 PM 
 * @author   Administrator
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class DOM4JUtilsTest {

	private DOM4JUtils dom4jUtils;

	@Before
	public void setUp() throws Exception {
		dom4jUtils = new DOM4JUtils("sample.xml");
	}

	@Test
	public void testWriteSampleXml() {
		dom4jUtils.writeSampleXml();
		
		String sample="sample.xml";
		String node = dom4jUtils.getNode(sample, "hello");
		System.out.println("get node value is: "+node);
	}

}


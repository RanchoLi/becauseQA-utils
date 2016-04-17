/**
 * Project Name:commons
 * File Name:Md5UtilsTest.java
 * Package Name:com.github.becausetesting.encrypt
 * Date:Apr 17, 20166:01:08 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.encrypt;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * ClassName:Md5UtilsTest  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     Apr 17, 2016 6:01:08 PM 
 * @author   Administrator
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class Md5UtilsTest {

	private Md5Utils md5Utils;

	@Before
	public void setUp() throws Exception {
		md5Utils = new Md5Utils();
	}

	@Test
	public void testMd5() {
		String md5 = md5Utils.md5("MPTest@123");
		String md5descrypt = md5Utils.md5descrypt(md5);
		System.out.println(md5);
		//System.out.println(md5descrypt);
	}

}


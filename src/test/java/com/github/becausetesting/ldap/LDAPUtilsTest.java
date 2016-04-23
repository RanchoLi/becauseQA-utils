/**
 * Project Name:commons
 * File Name:LDAPUtilsTest.java
 * Package Name:com.github.becausetesting.ldap
 * Date:Apr 23, 20163:44:42 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.ldap;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * ClassName:LDAPUtilsTest  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     Apr 23, 2016 3:44:42 PM 
 * @author   Administrator
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class LDAPUtilsTest {

	//private LDAPUtils ldapUtils;

	@Before
	public void setUp() throws Exception {
		//ldapUtils = new LDAPUtils();
		String server="ldap://ldapclient.com:389/dc=test,dc=com";
		String username=null;
		String password=null;
		LDAPUtils.getContext(server, username, password);
	}

	
	@Test
	public void testGetCurrentUserEmail() {
		//ldapUtils.getCurrentUserEmail();
	}

	@Test
	public void testSearchByUserNameAndAuthenticate() {
		LDAPUtils.searchByUserNameAndAuthenticate("testuser1", "pwd");
	}

	@Test
	public void testSearch() {
		fail("Not yet implemented");
	}

}


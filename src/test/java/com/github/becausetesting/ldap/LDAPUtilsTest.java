/**
 * Project Name:commons
 * File Name:LDAPUtilsTest.java
 * Package Name:com.github.becausetesting.ldap
 * Date:Apr 23, 20163:44:42 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.ldap;

import static org.junit.Assert.fail;

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
		String server="ldap://gdcad01.nextestate.com/DC=NEXTESTATE,DC=COM";
		String username="ahu";
		String password="gu.chan-102633";
		LDAPUtils.getContext(server, username, password);
	}

	
	@Test
	public void testGetCurrentUserEmail() {
		//ldapUtils.getCurrentUserEmail();
	}

	@Test
	public void testSearchByUserNameAndAuthenticate() {
		LDAPUtils.searchByUserNameAndAuthenticate("ahu", "gu.chan-102633");
	}

	@Test
	public void testSearch() {
		fail("Not yet implemented");
	}

}


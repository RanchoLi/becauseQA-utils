package com.github.becausetesting.email;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailUtilsTest {

	private EmailUtils emailUtils;
	@Before
	public void setUp() throws Exception {
		emailUtils=new EmailUtils();
	}

	@After
	public void tearDown() throws Exception {
		emailUtils.sendEmail("ahu@greendotcorp.com", "ahu@greendotcorp.com", "hellow body", "<body><b>test</b></body>");
	}

	@Test
	public void testSendEmail() {
		//fail("Not yet implemented");
	}

}

package com.github.becausetesting.email;

import static org.junit.Assert.*;

import java.io.File;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.becausetesting.apache.commons.IOUtils;
import com.github.becausetesting.apache.commons.StringUtils;

public class EmailUtilsTest {

	private EmailUtils emailUtils;
	@Before
	public void setUp() throws Exception {
		emailUtils=new EmailUtils();
	}

	@After
	public void tearDown() throws Exception {
		InputStream resourceAsStream = getClass().getResourceAsStream("/index2.html");
		
		String emailContent = IOUtils.toString(resourceAsStream);
		File emailfile = new File(getClass().getClassLoader().getResource("email.properties").getPath());
		EmailUtils.setEmailfile(emailfile);
		emailUtils.sendEmail("ahu@greendotcorp.com", "ahu@greendotcorp.com", "First Designer", emailContent);
	}

	@Test
	public void testSendEmail() {
		//fail("Not yet implemented");
	}

}

package com.github.becauseQA.email;

import java.io.File;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.becauseQA.apache.commons.IOUtils;

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
		emailUtils.sendEmail("email@gamillc.o", "alterhu20202@gmail.coms", "First Designer", emailContent);
	}

	@Test
	public void testSendEmail() {
		//fail("Not yet implemented");
	}

}

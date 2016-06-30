package com.github.becausetesting.encrypt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Base64UtilsTest {

	//private Base64Utils base64;
	@Before
	public void setUp() throws Exception {
		//base64=new Base64Utils();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEncrypt() {
		String encrypt = Base64Utils.encryptBasic("abcd");
		assertNotNull(encrypt);
	}

	@Test
	public void testEncrypturl() {
		String encrypt = Base64Utils.encryptURL("TutorialsPoint?java8");
		assertEquals("VHV0b3JpYWxzUG9pbnQ_amF2YTg=", encrypt);
		//assertNotNull(encrypt.equals("VHV0b3JpYWxzUG9pbnQ_amF2YTg="));
	}

}

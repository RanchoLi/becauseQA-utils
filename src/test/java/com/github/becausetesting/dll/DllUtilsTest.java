package com.github.becausetesting.dll;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DllUtilsTest {

	//private DllUtils dllUtils;

	@Before
	public void setUp() throws Exception {
	//	dllUtils = new DllUtils();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadDll() {
		String path = DllUtils.class.getClassLoader().getResource("jtds/x64/SSO").getPath();
		DllUtils.loadDll("mtlmauth", path);
	}

}

package com.github.becauseQA.reflections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RefelectionUtilsTest {

	@Before
	public void setUp() throws Exception {
		Object contractorInstance = RefelectionUtils.getContractorInstance(Class.forName("com.github.becauseQA.reflections.SampleCode"), new Object[] {});
		
		RefelectionUtils.getMethod(contractorInstance, "setvalue", new Object[] {});
		RefelectionUtils.getMethod(contractorInstance, "getvalue", new Object[] {});
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetStepDefPaths() {
		//refelectionUtils.getStepDefPaths();
	}

}

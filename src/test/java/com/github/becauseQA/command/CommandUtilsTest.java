package com.github.becauseQA.command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandUtilsTest {

	@Before
	public void setUp() throws Exception {
		new CommandUtils();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecuteCommandString() {
		//fail("Not yet implemented");
		//commandUtils.runCommand("cmd.exe","/c","java.exe","-jar","C:\\Selenium-Server\\selenium-server-standalone-2.53.0.jar" );
		int asc='-';
		int unicode='-';
		String hexString = Integer.toHexString('-');
		
		System.out.println(asc+"\n"+unicode+"\n"+hexString);
		
	}

	@Test
	public void testExecuteCommandListOfString() {
		//fail("Not yet implemented");
	}

	

}

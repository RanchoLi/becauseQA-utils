package com.github.becausetesting.autoit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ApplicationTest {

	private Application application;

	@Before
	public void setUp() throws Exception {
		application = new Application();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		application.run("calc");
		String title="Calculator";

		application.winActivate(title);
		application.winWaitActive(title);
		application.controlClick(title, "", "[CLASS:Button;Text:9]");
		application.controlClick(title, "", "[CLASS:Button;Text:+]");
		application.controlClick(title, "", "[CLASS:Button;Text:6]");
		application.controlClick(title, "", "[CLASS:Button;Text:=]");
		
		//application.winClose(title);
	}

}

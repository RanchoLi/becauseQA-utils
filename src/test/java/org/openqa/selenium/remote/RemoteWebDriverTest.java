package org.openqa.selenium.remote;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.github.becauseQA.cucumber.selenium.BaseSteps;
import com.github.becauseQA.cucumber.selenium.SeleniumCore;

public class RemoteWebDriverTest {

	private BaseSteps baseSteps;
	@Before
	public void Setup(){
		 SeleniumCore.startBrowser("localhost", "firefox", true, false);
		 baseSteps = new BaseSteps();
	}
	@Test
	public void test() {
		
		baseSteps.pageVisit("https://www.google.com");
		WebElement webElement = baseSteps.elementFindByID("lst-ib");
		baseSteps.elementInput(webElement, "test firefox");
	}

}

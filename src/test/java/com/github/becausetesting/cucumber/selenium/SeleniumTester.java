package com.github.becausetesting.cucumber.selenium;

import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTester {

	@Test
	public void test() {
		System.setProperty("webdriver.chrome.driver", "D:\\Downloads\\swd-recorder-master\\SwdPageRecorder\\SwdPageRecorder.WebDriver\\chromedriver.exe");
		
		ChromeDriver chromeDriver=new ChromeDriver();
		
		chromeDriver.get("http://www.baidu.com");
		chromeDriver.get("http://www.sohu.com");
	}

}

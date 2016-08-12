package com.github.becauseQA.cucumber.selenium;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTester {

	@Test
	@Ignore
	public void test() {
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium-Server\\chromedriver-2.21.exe");
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\ahu\\Downloads\\geckodriver-v0.9.0-win64\\geckodriver.exe");
		
		ChromeDriver chromeDriver=new ChromeDriver();
		BaseSteps.driver=chromeDriver;
		//PageRecorder.InjectPageRecorder(chromeDriver);
		/*PageRecorderThread pageRecorderThread=new PageRecorderThread(chromeDriver);
		pageRecorderThread.start();*/
		chromeDriver.manage().window().maximize();
		chromeDriver.get("http://www.attheregister.com");
		//PageRecorder.InjectPageRecorder(chromeDriver);
		chromeDriver.get("http://www.google.com");
	}
	
	@Test
	public void test2(){
		SeleniumCore.startSeleniumDriver("localhost", "Chrome Emulation-Apple iPhone 6 Plus", true);
	}

}

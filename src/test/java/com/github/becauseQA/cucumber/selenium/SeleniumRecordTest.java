package com.github.becauseQA.cucumber.selenium;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumRecordTest {

	@Test
	public void test() {
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium-Server\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\ahu\\Downloads\\geckodriver-v0.9.0-win64\\geckodriver.exe");
		
		ChromeDriver chromeDriver=new ChromeDriver();
		BaseSteps.driver=chromeDriver;
		//PageRecorder.startRecord(chromeDriver);
		chromeDriver.manage().window().maximize();
		chromeDriver.get("https://tools.pingdom.com/");
		//PageRecorder.InjectPageRecorder(chromeDriver);
		//PageRecorder.showMessage("open this browser now good", null);
		PageRecorder.InjectJQuery();
		PageRecorder.InjectPageRecorder();
		PageRecorder.InjectAlertifyFile();
		PageRecorder.showMessage("Given I begin to sleep this whole night1", null,"test title");
		

		chromeDriver.get("http://www.google.com");
		PageRecorder.InjectJQuery();
		PageRecorder.InjectPageRecorder();
		PageRecorder.InjectAlertifyFile();
		PageRecorder.showMessage("Given I begin to sleep this whole night1", null,"test title");
		


	}
	
	@Test
	@Ignore
	public void test2(){
		SeleniumCore.startSeleniumDriver("localhost", "Chrome Emulation-Apple iPhone 6 Plus", true);
	}

}

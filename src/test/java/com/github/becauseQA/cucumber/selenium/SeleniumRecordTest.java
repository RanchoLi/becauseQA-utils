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
		chromeDriver.get("https://www.attheregister.com/moneypak");
		


	}
	
	@Test
	@Ignore
	public void test2(){
		SeleniumCore.startBrowser("localhost", "Chrome Emulation-Apple iPhone 6 Plus", true,false);
	}

}

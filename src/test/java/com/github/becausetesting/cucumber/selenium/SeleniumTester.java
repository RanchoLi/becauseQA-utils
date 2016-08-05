package com.github.becausetesting.cucumber.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

public class SeleniumTester {

	@Test
	//@Ignore
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
		WebElement findElement = chromeDriver.findElement(By.className("test"));
		
		//PageRecorder.InjectPageRecorder(chromeDriver);
		chromeDriver.get("http://www.google.com");
	}
	
	
	public void test2(){
		EventHandler eventHandler=new EventHandler();
		
	}

}

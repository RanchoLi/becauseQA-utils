package com.github.becauseQA.cucumber.selenium;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class EventHandler implements WebDriverEventListener {

	private static final Logger log = Logger.getLogger(EventHandler.class);

	@Override
	public void afterChangeValueOf(WebElement webElement, WebDriver driver, CharSequence[] arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterClickOn(WebElement webElement, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterFindBy(By arg0, WebElement webElement, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
		log.info("after navigate forward: " + driver);
	}

	@Override
	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
		log.info("nagivate page:" + url);
	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeChangeValueOf(WebElement webElement, WebDriver driver, CharSequence[] value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeClickOn(WebElement webElement, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeFindBy(By by, WebElement webElement, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
		log.info("before navigate to:" + url);

	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {

		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\ahu\\Downloads\\geckodriver-v0.9.0-win64\\geckodriver.exe");

		FirefoxDriver driver = new FirefoxDriver();

		EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(driver);
		EventHandler eventHandler = new EventHandler();
		eventFiringWebDriver.register(eventHandler);

		// driver.get("");
		// eventFiringWebDriver.
		eventFiringWebDriver.get("http://www.baidu.com");
		
	}
}

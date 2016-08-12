package com.github.becauseQA.cucumber.selenium;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.google.gson.JsonObject;

public class PageRecorderThread extends Thread {

	private WebDriver driver;
	private static final Logger log = Logger.getLogger(PageRecorderThread.class);
	private boolean pageRecorderStarted = false;
	private long pageRecorderTimeMills = 777; // The time interval to check the
	private static String elementName;
	// page element

	public PageRecorderThread(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		pageRecorderStarted = true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// process to get the last command id in page
		while (pageRecorderStarted) {
			try {
				PageRecorder.InjectPageRecorder(driver);
				JsonObject seleniumCommand = PageRecorder.getSeleniumCommand(driver);
				if (seleniumCommand != null) {
					String commandName = seleniumCommand.get("Command").getAsString();
					if (commandName.equals("rightClickHandler")) {
						// when you right click the element
					} else if (commandName.equals("AddElement")) {
						// add the command to any place you want
						if (!elementName.equals(seleniumCommand.get("ElementName").getAsString())) { // not record the duplicated elements
							String elementId = seleniumCommand.get("ElementId").getAsString();
							String cssPath = seleniumCommand.get("CSS").getAsString();
							String XPath = seleniumCommand.get("XPath").getAsString();
							log.info("Add Element: " + elementName + "," + elementId + "," + cssPath + "," + XPath);
							elementName = seleniumCommand.get("ElementName").getAsString();
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				pageRecorderStarted = false;
				log.error(e);				
			}
			try {
				Thread.currentThread();
				Thread.sleep(pageRecorderTimeMills);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}

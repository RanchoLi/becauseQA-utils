package com.github.becausetesting.cucumber.selenium;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.github.becausetesting.apache.commons.FileUtils;
import com.github.becausetesting.apache.commons.StringUtils;
import com.github.becausetesting.json.JSONUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PageRecorder {

	private static final Logger log = Logger.getLogger(PageRecorder.class);

	private static String javaScript_GetCommand = "return document.selenium_data === undefined ? '' : document.selenium_data;";

	private static String lastCommandid = null;

	private static void InjectJSON2ObjectForWierdBrowsers(WebDriver driver) {
		String jsonFilePath = PageRecorder.class.getResource("/js/json2.js").getFile();
		try {
			String jsonContent = FileUtils.readFileToString(new File(jsonFilePath));
			String IsJson2ObjectExists = "return typeof JSON === 'object';";
			boolean jsonSupport = (boolean) BaseSteps.runJS(IsJson2ObjectExists);
			if (!jsonSupport) {
				BaseSteps.runJS(jsonContent);
			} else {
				log.info("Current browser had support JSON object,it's modern browser");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void InjectJQuery(WebDriver driver) {
		String jqueryFilePath = PageRecorder.class.getResource("/js/jquery-3.1.0.min.js").getFile();
		try {
			String jqueryContent = FileUtils.readFileToString(new File(jqueryFilePath));
			String IsJqueryObjectExists = "return typeof jQuery  === 'undefined';";
			boolean jqueryNotSupport = (boolean) BaseSteps.runJS(IsJqueryObjectExists);
			if (jqueryNotSupport) {
				BaseSteps.runJS(jqueryContent);
			} else {
				log.info("Current browser had support JQuery object,it's modern browser");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static boolean IsVisualSearchScriptInjected(WebDriver driver) {
		String jsCheckScript = "return document.PageRecorder === undefined ? false : true;";
		boolean IsVisualSearchScriptInjected = (boolean) BaseSteps.runJS(jsCheckScript);
		return IsVisualSearchScriptInjected;
	}

	public static void InjectPageRecorder(WebDriver driver) {

		String jsonFilePath = PageRecorder.class.getResource("/js/PageRecorder.js").getFile();
		try {
			boolean isVisualSearchScriptInjected = IsVisualSearchScriptInjected(driver);
			if (!isVisualSearchScriptInjected) {
				String jsonContent = FileUtils.readFileToString(new File(jsonFilePath));
				InjectJQuery(driver);
				InjectJSON2ObjectForWierdBrowsers(driver);
				BaseSteps.runJS(jsonContent);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void destoryPageRecorder(WebDriver driver) {
		String[] jsObjectArray = { "document.PageRecorder", "document.pr_prevActiveElement", "document.selenium_data" };
		StringBuilder deathBuilder = new StringBuilder();

		for (String sentencedToDeath : jsObjectArray) {

			String formatStr = MessageFormat.format(
					" try {{ delete {0}; }} catch (e) {{ if (console) {{ console.log('ERROR: |{0}| --> ' + e.message)}} }} ",
					sentencedToDeath);
			deathBuilder.append(formatStr);
		}

		if (IsVisualSearchScriptInjected(driver)) {
			log.info("DestroyVisualSearch: Scripts have been injected previously. Kill'em all!");
			BaseSteps.runJS(deathBuilder.toString());
		}

	}

	public static JsonObject getSeleniumCommand(WebDriver driver) {
		String seleniumDataStr = "";
		JsonObject seleniumData = null;
		try {
			seleniumDataStr = (String) BaseSteps.runJS(javaScript_GetCommand);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (StringUtils.isNotEmpty(seleniumDataStr)) {
			JsonElement jsonElement = JSONUtils.toJsonElement(seleniumDataStr);
			String commandId = jsonElement.getAsJsonObject().get("CommandId").getAsString();
			String commandName = jsonElement.getAsJsonObject().get("Command").getAsString();
			if (lastCommandid == commandId)
				return null;
			if (commandName.equals("rightClickHandler")) {
				seleniumData = jsonElement.getAsJsonObject();
			} else if (commandName.equals("AddElement")) {
				seleniumData = jsonElement.getAsJsonObject();
			}
			lastCommandid = commandId;

		}
		return seleniumData;
	}
}

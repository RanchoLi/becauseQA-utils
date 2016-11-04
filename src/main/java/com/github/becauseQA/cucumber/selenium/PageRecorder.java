package com.github.becauseQA.cucumber.selenium;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.github.becauseQA.apache.commons.FileUtils;
import com.github.becauseQA.apache.commons.StringEscapeUtils;
import com.github.becauseQA.apache.commons.StringUtils;
import com.github.becauseQA.json.JSONUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PageRecorder {

	private static final Logger log = Logger.getLogger(PageRecorder.class);

	private static String javaScript_GetCommand = "return document.selenium_data === undefined ? '' : document.selenium_data;";

	private static String lastCommandid = null;
	private static String jsFolder = "/js/";

	private static File copyResource2TempFolder(String filename) {
		String tempdllpath = System.getProperty("java.io.tmpdir");
		String destinationPath = tempdllpath + filename;
		File jsFile = new File(destinationPath);
		if (!jsFile.exists()) {
			String resourceFilePath = jsFolder + filename;
			InputStream inputStreamJS = PageRecorder.class.getResourceAsStream(resourceFilePath);
			try {
				FileUtils.copyInputStreamToFile(inputStreamJS, jsFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsFile;
	}

	public static void InjectAlertifyFile() {
		try {
			String alertifyFound = "return typeof toastr=='object'; ";
			boolean alertSupport = (boolean) BaseSteps.javascript(alertifyFound);
			if (!alertSupport) {
				File alertifyJS = copyResource2TempFolder("toastr.min.js");
				File alertifyCSS = copyResource2TempFolder("toastr.min.css");
				String alertifyJSContent = FileUtils.readFileToString(alertifyJS);
				String toastrJSContent = FileUtils.readFileToString(alertifyCSS, "UTF-8");
				BaseSteps.javascript(alertifyJSContent);
				BaseSteps.PageCssStyleAdd(toastrJSContent);
			} else {
				log.info("Current browser had support toastr object,it's modern browser");
			}

			// String toastrCSS =
			// PageRecorder.class.getResource("/js/toastr.min.css").getFile();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void InjectJSON2ObjectForWierdBrowsers() {
		// String jsonFilePath =
		// PageRecorder.class.getResource("/js/json2.js").getFile();
		try {
			String IsJson2ObjectExists = "return typeof JSON === 'object';";
			boolean jsonSupport = (boolean) BaseSteps.javascript(IsJson2ObjectExists);
			if (!jsonSupport) {
				File jsonFilePath = copyResource2TempFolder("json2.js");
				String jsonContent = FileUtils.readFileToString(jsonFilePath);
				BaseSteps.javascript(jsonContent);
			} else {
				log.info("Current browser had support JSON object,it's modern browser");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void InjectJQuery() {
		// String jqueryFilePath =
		// PageRecorder.class.getResource("/js/jquery-3.1.0.min.js").getFile();
		try {
			String IsJqueryObjectExists = "return typeof jQuery  === 'undefined';";
			boolean jqueryNotSupport = (boolean) BaseSteps.javascript(IsJqueryObjectExists);
			if (jqueryNotSupport) {
				File jqueryFilePath = copyResource2TempFolder("jquery-3.1.0.min.js");
				String jqueryContent = FileUtils.readFileToString(jqueryFilePath);
				BaseSteps.javascript(jqueryContent);
			} else {
				log.info("Current browser had support JQuery object,it's modern browser");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean IsVisualSearchScriptInjected() {
		String jsCheckScript = "return document.PageRecorder === undefined ? false : true;";
		boolean IsVisualSearchScriptInjected = (boolean) BaseSteps.javascript(jsCheckScript);
		return IsVisualSearchScriptInjected;
	}

	public static void InjectPageRecorder() {
		try {
			File jsonFilePath = copyResource2TempFolder("PageRecorder.js");
			boolean isVisualSearchScriptInjected = IsVisualSearchScriptInjected();
			if (!isVisualSearchScriptInjected) {
				InjectJQuery();
				InjectJSON2ObjectForWierdBrowsers();
				InjectAlertifyFile();
				String jsonContent = FileUtils.readFileToString(jsonFilePath);
				BaseSteps.javascript(jsonContent);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void destoryPageRecorder() {
		String[] jsObjectArray = { "document.PageRecorder", "document.pr_prevActiveElement", "document.selenium_data" };
		StringBuilder deathBuilder = new StringBuilder();

		for (String sentencedToDeath : jsObjectArray) {

			String formatStr = MessageFormat.format(
					" try {{ delete {0}; }} catch (e) {{ if (console) {{ console.log('ERROR: |{0}| --> ' + e.message)}} }} ",
					sentencedToDeath);
			deathBuilder.append(formatStr);
		}

		if (IsVisualSearchScriptInjected()) {
			log.info("DestroyVisualSearch: Scripts have been injected previously. Kill'em all!");
			BaseSteps.javascript(deathBuilder.toString());
		}

	}

	public static JsonObject getSeleniumCommand() {
		String seleniumDataStr = "";
		JsonObject seleniumData = null;
		try {
			seleniumDataStr = (String) BaseSteps.javascript(javaScript_GetCommand);
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

	public static void inspector(TimerTask task, long timeMillSeconds) {
		Timer timer = new Timer();
		timer.schedule(task, 0, timeMillSeconds);
	}

	public static void startRecord(WebDriver driver) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					PageRecorder.InjectPageRecorder();
					JsonObject seleniumCommand = PageRecorder.getSeleniumCommand();
					if (seleniumCommand != null) {
						String commandName = seleniumCommand.get("Command").getAsString();
						// elementName =
						// seleniumCommand.get("ElementName").getAsString();
						if (commandName.equals("rightClickHandler")) {
							// when you right click the element
						} else if (commandName.equals("AddElement")) {
							// add the command to any place you want

							String elementId = seleniumCommand.get("ElementId").getAsString();
							String cssPath = seleniumCommand.get("CSS").getAsString();
							String XPath = seleniumCommand.get("XPath").getAsString();
							String content = seleniumCommand.get("Text").getAsString();
							String elementName = seleniumCommand.get("ElementName").getAsString();
							log.info("Add Element: " + elementName + "," +content+","+ elementId + "," + cssPath + "," + XPath);

						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					log.error(e);
					cancel();
				}

			}
		}, 0, 3 * 1000);
	}

	/**
	 * Show a non-blocking messagebox to indicate the step execution
	 * 
	 * @param message
	 *            the message need to show in the page
	 * @param type
	 *            type should be success,info,warning,error
	 */
	public static void showMessage(String message, String type, String title) {
		String msgType = "info"; //
		int hidetimeout = 3000;
		if (StringUtils.isNotEmpty(type)) {
			msgType = type;
			if (msgType.equalsIgnoreCase("error")) {
				hidetimeout = 0;
			}
		}
		message = StringEscapeUtils.escapeEcmaScript(message);
		String notifyJS = "toastr.options.closeButton= true;toastr.options.debug= false;"
				+ "toastr.options.newestOnTop=true;toastr.options.progressBar=false;"
				+ "toastr.options.positionClass='toast-top-left';toastr.options.preventDuplicates= false;"
				+ "toastr.options.showDuration=300;toastr.options.hideDuration=" + hidetimeout + ";"
				+ "toastr.options.timeOut=8000;toastr.options.extendedTimeOut=3000;"
				+ "toastr.options.showEasing='swing';toastr.options.hideEasing='linear';"
				+ "toastr.options.showMethod='fadeIn';toastr.options.hideMethod='slideUp';toastr['" + msgType + "'](\""
				+ message + "\", '" + title + "').css('width','400px');";
		try

		{
			BaseSteps.javascript(notifyJS);
		} catch (WebDriverException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		showMessage("test", null, "");
	}

}

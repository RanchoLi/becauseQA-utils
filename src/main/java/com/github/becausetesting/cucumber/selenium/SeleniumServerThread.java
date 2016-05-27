package com.github.becausetesting.cucumber.selenium;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.sun.jna.Platform;

/**
 * @deprecated see com.github.becausetesting.cucumber.selenium.startSeleniumServer
 * @author ahu
 *
 */
public class SeleniumServerThread implements Runnable {

	private static Logger logger = LogManager.getLogger(SeleniumServerThread.class);

	private SeleniumServer seleniumServer;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String[] args = new String[2];

		// RemoteControlLauncher had deprecated from 2.49.0
		// RemoteControlLauncher.class totally removed from
		// org.openqa.selenium.server.cli package
		// RemoteControlConfiguration rcc =
		// RemoteControlLauncher.parseLauncherOptions(args);
		// set the chrome and ie driver using this
		// method:SeleniumServer.setSystemProperty();
		String iedriver = System.getProperty("webdriver.ie.driver");
		String userdir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "resources";
		if (iedriver == null) {
			SeleniumDownloader.downloadIEDriverResources(userdir);
			// ClassLoader loader = SeleniumCore.class.getClassLoader();
			String iedriverPath = userdir + File.separator + "IEDriverServer.exe";
			args[0] = "-Dwebdriver.ie.driver=" + iedriverPath;
			// System.setProperty("webdriver.ie.driver", iedriverPath);
			logger.info("Set IE Driver in this location:" + iedriverPath);
		}

		String chromeDriver = System.getProperty("webdriver.chrome.driver");
		if (chromeDriver == null) {
			SeleniumDownloader.downloadChromeResources(userdir);
			String chromedriverPath = null;
			// ClassLoader loader = SeleniumCore.class.getClassLoader();
			boolean windows = Platform.isWindows();
			if (windows) {
				chromedriverPath = userdir + File.separator + "chromedriver.exe";
			} else {
				chromedriverPath = userdir + File.separator + "chromedriver";
			}
			args[1] = "-Dwebdriver.chrome.driver=" + chromedriverPath;
			// System.setProperty("webdriver.chrome.driver",
			// chromedriverPath);
			logger.info("Chrome Driver location: " + chromedriverPath);
			logger.info("webdriver.chrome.driver is:" + System.getProperty("webdriver.chrome.driver"));
		}

		RemoteControlConfiguration rcc = SeleniumServer.parseLauncherOptions(args);
		rcc.setPort(RemoteControlConfiguration.DEFAULT_PORT);
		rcc.setTrustAllSSLCertificates(true);
		rcc.setCaptureLogsOnQuit(true);
		rcc.setBrowserSideLogEnabled(true);
		// rcc.setReuseBrowserSessions(true);
		rcc.setUserJSInjection(true);

		rcc.setDebugMode(true);
		try {
			seleniumServer = new SeleniumServer(true, rcc);

			seleniumServer.boot();
			logger.info("Start selenium remote server with configuration: " + rcc);
		} catch (Exception e) {
			// TODO Auto-generated catch block

		}
	}

	public void stopServer() {
		// it will automatically stop if start the selenium server when the
		// thread exit
		if (seleniumServer != null) {
			seleniumServer.stop();
		}
	}

}

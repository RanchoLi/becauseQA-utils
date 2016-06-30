package com.github.becausetesting.cucumber.selenium;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

import com.github.becausetesting.apache.commons.FileUtils;
import com.github.becausetesting.host.HostUtils;
import com.github.becausetesting.http.HttpUtils;
import com.github.becausetesting.xml.XMLUtils;
import com.sun.jna.Platform;

public class SeleniumDownloader {

	private static Logger logger = Logger.getLogger(SeleniumDownloader.class);
	
	private static String SELENIUM_URL = "https://selenium-release.storage.googleapis.com/";
	private static String CHROME_DRIVER_URL = "https://chromedriver.storage.googleapis.com/";
	
	public static String seleniumstandaloneName=null;
	public static String iedriverFilePath=null;
	public static String chromedriverFilePath=null;

	public static void downloadSeleniumResources(String destinationFolder) {
		String xml_versions_url = SELENIUM_URL + "?delimiter=/&prefix=";

		List<String> result = XMLUtils.SAXParse(xml_versions_url, "Prefix");
		String latestVersionStr = result.get(result.size() - 2);
		latestVersionStr = latestVersionStr.substring(0, latestVersionStr.length() - 1);
		logger.info("latest selenium version is: " + latestVersionStr);

		String seleniumName = "selenium-server-standalone-" + latestVersionStr + ".0.jar";
		String latest_selenium_url = SELENIUM_URL + latestVersionStr + "/" + seleniumName;

		try {
			seleniumstandaloneName = destinationFolder + File.separator + seleniumName;
			if (!new File(seleniumstandaloneName).exists()) {
				FileUtils.copyURLToFile(new URL(latest_selenium_url), new File(seleniumstandaloneName));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void downloadIEDriverResources(String destinationFolder) {
		String xml_versions_url = SELENIUM_URL + "?delimiter=/&prefix=";

		List<String> result = XMLUtils.SAXParse(xml_versions_url, "Prefix");
		String latestVersionStr = result.get(result.size() - 2);
		latestVersionStr = latestVersionStr.substring(0, latestVersionStr.length() - 1);
		logger.info("latest selenium version is: " + latestVersionStr);

		String iedriver_32bit_name = "IEDriverServer_Win32_" + latestVersionStr + ".0.zip";
		String iedriver_64bit_name = "IEDriverServer_x64_" + latestVersionStr + ".0.zip";
		String latest_iedriver_32bit_url = SELENIUM_URL + latestVersionStr + "/" + iedriver_32bit_name;
		String latest_iedriver_64bit_url = SELENIUM_URL + latestVersionStr + "/" + iedriver_64bit_name;

		boolean is64Bit = HostUtils.is64Bit();
		String iedriver_fromurl = latest_iedriver_64bit_url;
		String iedriver_destination_name = iedriver_64bit_name;
		iedriverFilePath = destinationFolder + File.separator + "IEDriverServer.exe";
		if (!is64Bit) {
			iedriver_fromurl = latest_iedriver_32bit_url;
			iedriver_destination_name = iedriver_32bit_name;
		}
		try {
			if (!new File(iedriverFilePath).exists()) {
				logger.info("Begin to download the ie driver from server: " + SELENIUM_URL);
				String iedriverPath = destinationFolder + File.separator + iedriver_destination_name;
				FileUtils.copyURLToFile(new URL(iedriver_fromurl), new File(iedriverPath));

				FileUtils.unZipIt(iedriverPath, destinationFolder);
				FileUtils.forceDelete(new File(iedriverPath));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void downloadChromeResources(String destinationFolder) {

		String chrome_note_url = CHROME_DRIVER_URL + "LATEST_RELEASE";
		String chromedriver_win_zipname = "chromedriver_win32.zip";
		String chromedriver_linux_zipname = "chromedriver_linux64.zip";
		String chromedriver_mac_zipname = "chromedriver_mac32.zip";

		try {
			String latest_chromedriver_version = HttpUtils.getRequest(new URL(chrome_note_url), null).trim();
			logger.info("latest chrome driver version is: " + latest_chromedriver_version);

			String chromedriver_path = "";
			String destination_chromedriver_path = "";
			boolean linux = Platform.isLinux();
			boolean win = Platform.isWindows();
			boolean mac = Platform.isMac();
			String chromedriver_name = "chromedriver";
			if (linux) {
				chromedriver_path = CHROME_DRIVER_URL + latest_chromedriver_version + "/" + chromedriver_linux_zipname;
				destination_chromedriver_path = destinationFolder + File.separator + chromedriver_linux_zipname;
			}
			if (win) {
				chromedriver_path = CHROME_DRIVER_URL + latest_chromedriver_version + "/" + chromedriver_win_zipname;
				destination_chromedriver_path = destinationFolder + File.separator + chromedriver_win_zipname;
				chromedriver_name = "chromedriver.exe";
			}
			if (mac) {
				chromedriver_path = CHROME_DRIVER_URL + latest_chromedriver_version + "/" + chromedriver_mac_zipname;
				destination_chromedriver_path = destinationFolder + File.separator + chromedriver_mac_zipname;
			}

			chromedriverFilePath = destinationFolder + File.separator + chromedriver_name;
			if (!new File(chromedriverFilePath).exists()) {
				logger.info("Begin to download the chrome driver from server: " + CHROME_DRIVER_URL);
				FileUtils.copyURLToFile(new URL(chromedriver_path), new File(destination_chromedriver_path));

				FileUtils.unZipIt(destination_chromedriver_path, destinationFolder);
				FileUtils.forceDelete(new File(destination_chromedriver_path));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

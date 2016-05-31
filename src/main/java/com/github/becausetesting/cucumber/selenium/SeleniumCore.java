package com.github.becausetesting.cucumber.selenium;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.github.becausetesting.cucumber.selenium.appium.AppiumCommonArgs;
import com.github.becausetesting.cucumber.selenium.appium.AppiumServer;
import com.github.becausetesting.cucumber.selenium.appium.ServerArguments;
import com.sun.jna.Platform;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;

public class SeleniumCore {

	private static Logger logger = Logger.getLogger(SeleniumCore.class);
	private static SeleniumServer seleniumServer;
	private static AppiumServer appiumServer;

	public static RemoteWebDriver driver;

	public final static String PROJECT_DIR = new File("").getAbsolutePath() + File.separator; // like
																								// "C:\workspace\Micro2_Automation_Selenium\"

	public static final int DEFAULT_PAGE_LOADING_TIME = 150;
	public static final int DEFAULT_WEBELEMENT_LOADING_TIME = 6;
	public static final long DEFAULT_JS_TIMEOUT = 30000L;
	public static long DEFAULT_TIMEOUT = 60;
	public static String DEFAULT_DOWNLOAD_DIR = PROJECT_DIR + "Downloads";

	enum PLATFORM {
		PC, ANDROID, IPAD, IPHONE;
	}

	/**
	 * start the selenium driver fro RemoteWebDriver instance.
	 * 
	 * @param servername
	 *            the selenium hub name, like http://localhost:4444/wd/hub, then
	 *            the parameter should be localhost.
	 * @param devicename
	 *            the device name you want to use ,like
	 *            ie,firefox,chrome,safari.etc
	 * @param useSession
	 *            if you need to use the existing opened selenium webdriver
	 *            session ,this parameter should better use in develop code .
	 * @return the selenium web driver instance.
	 */
	public static void startSeleniumDriver(String servername, String devicename, boolean useSession) {
		// the default platform is pc .
		PLATFORM platform = PLATFORM.PC;

		String pchub = "http://" + servername + ":4444/wd/hub";
		String mobilehub = "http://" + servername + ":4723/wd/hub";

		DesiredCapabilities capabilities = null;

		if (devicename != null) {
			devicename = devicename.trim();
			// PC Platform Browser Testing
			if (devicename.equalsIgnoreCase("ie")) {
				capabilities = IE();
			} else if (devicename.equalsIgnoreCase("firefox")) {
				capabilities = Firefox();
			} else if (devicename.equalsIgnoreCase("chrome")) {
				capabilities = Chrome();
			} else if (devicename.equalsIgnoreCase("safari")) {
				capabilities = Safari();
			} else if (devicename.equalsIgnoreCase("opera")) {
				capabilities = Opera();
			}
			// PC Platform Chrome Browser Emulator Testing
			else if (devicename.toLowerCase().startsWith("chrome") && devicename.length() > 8) {
				devicename = devicename.split("-")[1];
				capabilities = ChromeEmulator(devicename);
			}
			// Android Browser Testing
			else if (devicename.toLowerCase().startsWith("andriod")) {
				platform = PLATFORM.ANDROID;
				capabilities = mobile("", "Android", "Chrome", "Chrome");

			}
			// IPhone Browser Testing
			else if (devicename.toLowerCase().startsWith("iphone")) {
				platform = PLATFORM.IPHONE;
				mobile("iPhone 6", "iOS", "safari", null);

			}
			// Android Application Testing
			else if (devicename.toLowerCase().startsWith("andriod")) {
				platform = PLATFORM.ANDROID;
				mobile("", "Android", "Chrome", "Chrome");

			}
			// IPhone Application Testing
			else if (devicename.toLowerCase().startsWith("iphone")) {
				platform = PLATFORM.IPHONE;
				mobile("", "Android", "Chrome", "Chrome");

			}
			// IPAD Application Testing
			else if ((devicename.toLowerCase().startsWith("ipad")
					|| devicename.toLowerCase().startsWith("apple ipad"))) {
				platform = PLATFORM.IPAD;

			}
			// Default running in PC platform using Firefox browser
			else { // default using pc firefox browser
				capabilities = Firefox();
			}

		} else {
			capabilities = Firefox();
		}

		switch (platform) {
		case PC:
			// start the web driver
			if (servername.trim().equalsIgnoreCase("localhost") || servername.trim().equalsIgnoreCase("127.0.0.1")) {
				startSeleniumServer();
				if (useSession) {
					System.err.println(
							"As Selenium standalone start from existing page session ,you need to run thread from command line:");
					System.err.println("java -jar " + SeleniumDownloader.seleniumstandaloneName
							+ " -trustAllSSLCertificates -browserSessionReuse -debug" + " -Dwebdriver.chrome.driver="
							+ SeleniumDownloader.chromedriverFilePath + " -Dwebdriver.ie.driver="
							+ SeleniumDownloader.iedriverFilePath);
				}
			}
			try {
				String property = System.getProperty("webdriver.chrome.driver");
				driver = new RemoteWebDriverEx(new URL(pchub), capabilities, useSession);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			// init the web driver
			// start the appium server
			ServerArguments arguments = new ServerArguments();
			arguments.setArgument(AppiumCommonArgs.IP_ADDRESS, servername);
			arguments.setArgument(AppiumCommonArgs.PORT_NUMBER, 4723);
			arguments.setArgument(AppiumCommonArgs.ENABLE_FULL_RESET, true);
			boolean appiumserverRunning = false;
			appiumServer = new AppiumServer(arguments);
			appiumserverRunning = appiumServer.isServerRunning();
			if (!appiumserverRunning && (servername.trim().toLowerCase().equals("localhost")
					|| servername.trim().toLowerCase().equals("127.0.0.1"))) {
				appiumServer.startServer();
			}
			try {
				driver = new IOSDriver<>(new URL(mobilehub), capabilities);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		Capabilities actualCapabilities = driver.getCapabilities();
		String browser = actualCapabilities.getBrowserName();
		// boolean emulation = (boolean)
		// actualCapabilities.getCapability("mobileEmulationEnabled");
		logger.info("Server Capabilities are :\n" + actualCapabilities.toString() + "\nBrowser Name:" + browser
				+ " , is mobile emulation:?? ");
		seleniumDriverManager(driver);

	}

	/**
	 * close the selenium driver.
	 */
	public void closeSeleniumDriver() {
		if (driver != null) {
			driver.close();
			// factory.killDriverProcesses();
		}
	}

	/**
	 * @Description: TODO
	 * @author alterhu2020@gmail.com
	 * @param @param
	 *            driver selenium driver instance.
	 *
	 */

	public static void seleniumDriverManager(RemoteWebDriver driver) {
		try {
			driver.manage().deleteAllCookies();
			// page load time but safari don't support it not yet Nope, the
			// SafariDriver does not support page load timeouts yet.
			String browserName = driver.getCapabilities().getBrowserName();
			if (!browserName.equalsIgnoreCase("safari")) {
				driver.manage().timeouts().pageLoadTimeout(DEFAULT_PAGE_LOADING_TIME, TimeUnit.SECONDS);
				// the web element to find time we need to wait
				driver.manage().timeouts().implicitlyWait(DEFAULT_WEBELEMENT_LOADING_TIME, TimeUnit.SECONDS);
				// the js executor timeout
				driver.manage().timeouts().setScriptTimeout(DEFAULT_JS_TIMEOUT, TimeUnit.SECONDS);
			}

			driver.manage().window().maximize();

		} catch (TimeoutException e) {
			logger.error("The page or the webelment had been waited for 120 second,it cannot showed ,so the test failed"
					+ ",Exception:" + e.getMessage());
		}

	}

	/**
	 * @Title: browserCommonSettings @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param capability @return void
	 *         return type @throws
	 */

	@SuppressWarnings("deprecation")
	public static DesiredCapabilities CommonSettings(DesiredCapabilities capability) {
		capability.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true);
		capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		capability.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
		capability.setCapability("ignoreZoomSetting", true);
		capability.setCapability("ignoreProtectedModeSettings", true);
		capability.setCapability(CapabilityType.ENABLE_PERSISTENT_HOVERING, false); // prevent
		capability.setCapability("EnableNativeEvents", false);

		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		capability.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "accept");
		capability.setCapability(CapabilityType.SUPPORTS_ALERTS, "true");

		capability.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, true);
		capability.setCapability(CapabilityType.SUPPORTS_NETWORK_CONNECTION, true);

		// log
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.ALL);
		capability.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

		return capability;
	}

	/**
	 * @Title: browserProxySettings @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param capability @param @param
	 *         proxysettings @return void return type @throws
	 */

	public static void ProxySettings(DesiredCapabilities capability, String proxysettings) {
		org.openqa.selenium.Proxy httpproxy = new org.openqa.selenium.Proxy();
		httpproxy.setHttpProxy(proxysettings);
		httpproxy.setSslProxy(proxysettings);

		httpproxy.setNoProxy("localhost");
		capability.setCapability(CapabilityType.PROXY, httpproxy);
	}

	/**
	 * @Title: BrowserIESettings
	 * @Description: TODO
	 * @author alterhu2020@gmail.com
	 * @param @param
	 *            capability
	 * @return void return type
	 * @throws @refer
	 *             http://selenium.googlecode.com/git/docs/api/java/org/openqa/
	 *             selenium /ie/InternetExplorerDriver.html
	 */

	public static DesiredCapabilities IE() {
		DesiredCapabilities capability = DesiredCapabilities.internetExplorer();

		// SSL url setting
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
		capability.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
		capability.setCapability("requireWindowFocus", true);
		/*
		 * should set to true :
		 * http://jimevansmusic.blogspot.com/2012/08/youre-doing
		 * -it-wrong-protected-mode-and.html
		 */
		capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capability.setCapability(InternetExplorerDriver.LOG_LEVEL, "TRACE");
		capability.setCapability(InternetExplorerDriver.LOG_FILE, "logs");
		// clear session
		capability.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);

		return capability;
	}

	/**
	 * set the firefox profile ,like download file automatically .
	 * 
	 * 
	 * @return FirefoxProfile
	 * @throws IOException
	 * 
	 */
	public static DesiredCapabilities Firefox() {
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		FirefoxProfile fp = new FirefoxProfile();
		// fp.addExtension(extensionToInstall);
		// http://stackoverflow.com/questions/15292972/auto-download-pdf-files-in-firefox
		// http://www.webmaster-toolkit.com/mime-types.shtml
		// for config list see this :
		// http://kb.mozillazine.org/About:config_entries#Profile.
		fp.setAcceptUntrustedCertificates(true);
		fp.setAssumeUntrustedCertificateIssuer(true);
		fp.setEnableNativeEvents(false);
		// fp.setProxyPreferences(proxy);
		// for the download bar to automatically download it

		fp.setPreference("browser.download.manager.showWhenStarting", false);

		fp.setPreference("browser.download.useDownloadDir", true);
		fp.setPreference("browser.download.dir", DEFAULT_DOWNLOAD_DIR);
		fp.setPreference("browser.download.lastDir", DEFAULT_DOWNLOAD_DIR);
		fp.setPreference("browser.download.folderList", 2);

		// fp.setPreference("browser.helperApps.alwaysAsk.force", false);
		fp.setPreference("security.default_personal_cert", "Select Automatically");
		fp.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/octet-stream,application/x-compressed,application/x-zip-compressed,application/zip,multipart/x-zip");
				// File sslerror=new
				// File(SeleniumCore.getProjectWorkspace()+"resources"+File.separator+"remember_certificate_exception-1.0.0-fx.xpi");
				// fp.addExtension(sslerror);

		// the submits HTTP authentication dialogs ,your proxy is blocking the
		// WebSocket protocol
		fp.setPreference("network.websocket.enabled", false);
		fp.setPreference("signon.autologin.proxy", true);
		// this is the popup show url
		fp.setPreference("network.automatic-ntlm-auth.trusted-uris", "" + "paypal-attheregister.com,"
				+ "attheregister.com," + "moneypak.com," + "nextestate.com," + "necla");
		fp.setPreference("network.automatic-ntlm-auth.allow-proxies", true);
		fp.setPreference("network. negotiate-auth. allow-proxies", true);
		fp.setPreference("browser.ssl_override_behavior", 1);
		fp.setPreference("security.fileuri.strict_origin_policy", false);
		fp.setPreference("security. default_personal_cert", "Select Automatically");
		capabilities.setCapability(FirefoxDriver.PROFILE, fp);
		// logger.info("Had set the firefox profile for current selenium run
		// session");

		return capabilities;
	}

	/**
	 * @Title: browserChromeSettings @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return @return ChromeOptions return
	 *         type @throws
	 */

	public static DesiredCapabilities Chrome() {

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		Map<String, Object> prefs = new HashMap<String, Object>();

		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("download.default_directory", DEFAULT_DOWNLOAD_DIR);
		prefs.put("download.directory_upgrade", true);
		prefs.put("download.extensions_to_open", "");

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("prefs", prefs);
		chromeOptions.addArguments("start-maximized");
		chromeOptions.addArguments("--test-type");
		chromeOptions.addArguments("--disable-translate");
		chromeOptions.addArguments("--ignore-certificate-errors");
		chromeOptions.addArguments("allow-running-insecure-content");
		chromeOptions.addArguments("--always-authorize-plugins=true");

		capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

		return capabilities;

	}

	/*
	 * 
	 * https://sites.google.com/a/chromium.org/chromedriver/mobile-emulation
	 * 
	 */
	public static DesiredCapabilities ChromeEmulator(String devicename) {

		DesiredCapabilities chrome = Chrome();
		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", devicename);

		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("mobileEmulation", mobileEmulation);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("mobileEmulation", String.valueOf(mobileEmulation));
		// options.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS
		// 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko)
		// Version/8.0 Mobile/12A4345d Safari/600.1.4");
		// options.setExperimentalOption("mobileEmulation", mobileEmulation);
		chrome.setCapability(ChromeOptions.CAPABILITY, prefs);

		return chrome;

	}

	public static DesiredCapabilities Safari() {
		DesiredCapabilities capability = DesiredCapabilities.safari();
		SafariOptions option = new SafariOptions();
		option.setUseCleanSession(true);
		// ClassLoader classLoader = SeleniumCore.class.getClassLoader();
		// URL resource =
		// classLoader.getResource("safaridriver/SafariDriver.safariextz");
		String driverExtension = SeleniumCore.class.getResource("safaridriver/SafariDriver.safariextz").getPath();
		logger.warn("the safari Configuration driver path is:" + driverExtension
				+ ",the driver you need to install from safari manually,otherwise it will throw exception");
		capability.setCapability("safari.cleanSession", true);
		// not install safari plugin again
		System.setProperty("webdriver.safari.noinstall", "true");
		return capability;
	}

	@SuppressWarnings("deprecation")
	public static DesiredCapabilities Opera() {
		DesiredCapabilities capability = DesiredCapabilities.opera();
		capability.setCapability("opera.arguments", "-nowin -nomail");
		return capability;
	}

	public static DesiredCapabilities mobile(String devicename, String platform, String browsername, String app) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, devicename);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
		if (browsername != null) {
			capabilities.setCapability(CapabilityType.BROWSER_NAME, browsername);
		}
		if (app != null) {
			capabilities.setCapability(MobileCapabilityType.APP, app);
		}
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
		return capabilities;
	}

	public static DesiredCapabilities iphone(String devicename, String platform, String browsername, String app) {
		DesiredCapabilities capabilities = mobile(devicename, platform, browsername, app);
		return capabilities;
	}

	public static DesiredCapabilities ipad(String devicename, String platform, String browsername, String app) {
		DesiredCapabilities capabilities = mobile(devicename, platform, browsername, app);
		return capabilities;
	}

	private static void startSeleniumServer() {
		List<String> args = new ArrayList<String>();

		// RemoteControlLauncher had deprecated from 2.49.0
		// RemoteControlLauncher.class totally removed from
		// org.openqa.selenium.server.cli package
		// RemoteControlConfiguration rcc =
		// RemoteControlLauncher.parseLauncherOptions(args);
		// set the chrome and ie driver using this
		// method:SeleniumServer.setSystemProperty();
		String userdir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "resources";
		SeleniumDownloader.downloadSeleniumResources(userdir);

		String iedriver = System.getProperty("webdriver.ie.driver");
		if (iedriver == null) {
			SeleniumDownloader.downloadIEDriverResources(userdir);
			// ClassLoader loader = SeleniumCore.class.getClassLoader();
			String iedriverPath = userdir + File.separator + "IEDriverServer.exe";
			args.add("-Dwebdriver.ie.driver=" + iedriverPath);
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
			args.add("-Dwebdriver.chrome.driver=" + chromedriverPath);
			// System.setProperty("webdriver.chrome.driver",
			// chromedriverPath);
			logger.info("Chrome Driver location: " + chromedriverPath);
			logger.info("webdriver.chrome.driver is:" + System.getProperty("webdriver.chrome.driver"));
		}

		RemoteControlConfiguration rcc = SeleniumServer.parseLauncherOptions(args.toArray(new String[args.size()]));
		rcc.setPort(RemoteControlConfiguration.DEFAULT_PORT);
		rcc.setTrustAllSSLCertificates(true);
		rcc.setCaptureLogsOnQuit(true);
		rcc.setBrowserSideLogEnabled(true);
		rcc.setReuseBrowserSessions(true);
		rcc.setUserJSInjection(true);

		rcc.setDebugMode(true);

		try {
			seleniumServer = new SeleniumServer(true, rcc);
			seleniumServer.boot();
			logger.info("Start selenium remote server with configuration: " + rcc);
		} catch (java.net.BindException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.warn("Selenium Server already started before: " + rcc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

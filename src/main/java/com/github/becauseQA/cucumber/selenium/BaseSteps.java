package com.github.becauseQA.cucumber.selenium;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Class Generate from Cucumber Tool
 * 
 * https://techblog.polteq.com/en/injecting-the-sizzle-css-selector-library/
 * 
 */

/**
 * @ClassName: BaseSteps
 * @Description: TODO
 * @author ahu@greendotcorp.com
 * @date Jun 25, 2015 11:17:17 PM
 * 
 */

public class BaseSteps {

	public static WebDriver driver = SeleniumCore.driver;
	public static final Logger log = Logger.getLogger(BaseSteps.class);

	/**
	 * @Title: visitPage @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @return void return
	 *         type @throws
	 */

	public void visitPage(String params1) {
		// driver = SeleniumCore.driver;
		driver.get(params1);
	}

	/**
	 * http://www.w3schools.com/jsref/dom_obj_style.asp
	 * 
	 * @Title: highLight @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 * 
	 */

	public void highLight(WebElement e) {
		if (driver instanceof JavascriptExecutor) {
			String jsString = "element = arguments[0];\r\n" + "original_style = element.getAttribute('style');"
					+ "element.setAttribute('style', original_style + \"; background: yellow; border: 2px solid red;outline:2px dashed #00F;\");"
					+ "setTimeout(function(){" + "element.setAttribute('style', original_style);" + "        }, 300);";

			runJS(jsString, e);
		}

	}

	/**
	 * @Title: currentUrlIs @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param url @return void return
	 *         type @throws
	 */

	public void currentUrlIs(String url) {
		pageUrlIs(url);
	}

	/**
	 * @Title: clearBrowserData @Description: TODO @author
	 *         ahu@greendotcorp.com @param @return void return type @throws
	 */

	public void clearBrowserCookies() {
		driver.manage().deleteAllCookies();
		// driver.quit();
	}

	/**
	 * @Title: runJS @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param script @param @return @return
	 *         Object return type @throws
	 *//*
		 * 
		 * public static Object runJS(String script) { //
		 * logger.info("Run the javascript from page ,the java script is:" // +
		 * script); JavascriptExecutor je = (JavascriptExecutor) driver; return
		 * je.executeScript(script);
		 * 
		 * }
		 */

	/**
	 * Run js not sync simple thread
	 * 
	 * @param script
	 *            js script
	 * @return the js object
	 */
	public static Object runJSAsync(String script, Object... element) {
		if (driver == null)
			driver = SeleniumCore.driver;
		JavascriptExecutor je = (JavascriptExecutor) driver;
		return je.executeAsyncScript(script, element);

	}

	/**
	 * @Title: runJS @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param script @param @param e @return
	 *         void return type @throws
	 */

	public static Object runJS(String script, Object... e) {
		// logger.info("Run the javascript from page ,the java script is:"
		// + script);
		// highLight(e);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		return je.executeScript(script, e);

	}

	/**
	 * add the css style file content
	 * 
	 * @param css
	 *            css file content
	 * @return the return value
	 */
	public static Object addCssStyle(String css) {
		// logger.info("Run the javascript from page ,the java script is:"
		// + script);
		// highLight(e);
		String cssFunction = "var pageRecordHeader = document.getElementsByTagName('head')[0];\n"
				+ "var pageRecordStyle=document.createElement('style');\n"
				+ "pageRecordStyle.setAttribute('type', 'text/css');\n";
		String injectCSSAlreadyContent = "function supportCSSType(){" + cssFunction
				+ "if(pageRecordStyle.styleSheet){console.log('pageRecord:true');return true;}else{console.log('pageRecord:true');return false;}};\n"
				+ "return supportCSSType();";
		boolean hasCssStyleAlready = (boolean) BaseSteps.runJS(injectCSSAlreadyContent);
		if (hasCssStyleAlready) {
			cssFunction += "pageRecordStyle.styleSheet.cssText = '" + css + "';";
			// log.info(CSSFunctionInjectedObject.toString());
		} else {
			cssFunction += "pageRecordStyle.appendChild(document.createTextNode('" + css + "'));";
		}
		cssFunction += "\n pageRecordHeader.appendChild(pageRecordStyle);";

		return BaseSteps.runJS(cssFunction);

	}

	/**
	 * @Title: clickElement
	 * @Description: TODO
	 * @author ahu@greendotcorp.com
	 * @param @param
	 *            e
	 * @return void return type
	 * @throws @fixing
	 *             https://code.google.com/p/selenium/issues/detail?id=2766
	 *             Element is not clickable at point (378.5, 595.5). Other
	 *             element would receive the click:
	 */

	public void clickElement(WebElement e) {
		// throw new PendingException();
		highLight(e);
		scrollPagetoElement(e);
		e.click();
	}

	/**
	 * @Title: clickElementJS @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void clickElementJS(WebElement e) {
		// log.info("Click elements in page-clicked this element:"
		// + e.getTagName() + ",the text is:" + e.getText());
		// In chrome browser this function didn't work ,so give a solution to
		// load the page correctly
		highLight(e);
		runJS("arguments[0].click();", e);
	}

	/**
	 * @Title: rightClickElement @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void rightClickElement(WebElement e) {
		// throw new PendingException();
		// log.info("Highlight the element ,the object is:" + e.getTagName()
		// + ",the text in this object is:" + e.getText());
		Actions action = new Actions(driver);
		action.contextClick(e).perform();
		// log.info("Had right click the object ,then press the escape key");
		e.sendKeys(Keys.ESCAPE);
	}

	/**
	 * @Title: clickElementUsingjavascriptWithId @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param id @return void return
	 *         type @throws
	 */

	public void clickElementViaId(String id) {
		// throw new PendingException();
		// log.info("Click elements in page-clicked this element html id
		// is"+id);
		// In chrome browser this function didn't work ,so give a solution to
		// load the page correctly
		// highLight(e);
		runJS("document.getElementById('" + id + "').click();");
		// log.info("Clicked element's html ID is:"+id);
	}

	/**
	 * @Title: clickElementUsingMouse @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void clickElementViaMouse(WebElement e) {
		// throw new PendingException();
		// log.info("Click elements in page-clicked this element:"
		// + e.getTagName() + ",the text is:" + e.getText());
		// In chrome browser this function didn't work ,so give a solution to
		// load the page correctly
		// ((JavascriptExecutor)
		// driver).executeScript("window.scrollTo(0,"+e.getLocation().y+")");
		highLight(e);
		new Actions(driver).moveToElement(e).clickAndHold().release().build().perform();

	}

	/**
	 * @Title: clickOKButtonInAlertDialog @Description: TODO @author
	 *         ahu@greendotcorp.com @param @return void return type @throws
	 */

	public void clickOKButtonInAlertDialog() {
		// throw new PendingException();

		boolean isclicked = false;
		WebDriverWait wait = new WebDriverWait(driver, SeleniumCore.DEFAULT_WEBELEMENT_LOADING_TIME);
		try {
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			// log.info("Pop up Alert text is:"+alert.getText());
			alert.authenticateUsing(new UserAndPassword("ahu", "gu.chan-10266"));
			alert.accept();
			isclicked = true;
		} catch (NoAlertPresentException e) {
			// log.info("the Alert didn't pop up currently:"+e.getMessage());
		} catch (TimeoutException e) {
			log.error("Time out we cannot find this OK button:" + e.getMessage());
		}
		Assert.assertTrue(isclicked);

	}

	/**
	 * @Title: clickCancelButtonInAlertDialog @Description: TODO @author
	 *         ahu@greendotcorp.com @param @return void return type @throws
	 */

	public void clickCancelButtonInAlertDialog() {
		// throw new PendingException();

		boolean isclicked = false;
		WebDriverWait wait = new WebDriverWait(driver, SeleniumCore.DEFAULT_WEBELEMENT_LOADING_TIME);
		try {
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			// log.info("Pop up Alert text is:"+alert.getText());
			alert.dismiss();
			isclicked = true;
		} catch (NoAlertPresentException e) {
			// log.info("the Alert didn't pop up currently:"+e.getMessage());
		} catch (TimeoutException e) {
			log.error("Time out we cannot find this OK button:" + e.getMessage());
		}
		Assert.assertTrue(isclicked);

	}

	/**
	 * @Title: pageTitleIs @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @return void return
	 *         type @throws
	 */

	public void pageTitleIs(String params1) {
		// throw new PendingException();
		String title = driver.getTitle();
		Assert.assertEquals(params1, title);
	}

	/**
	 * @Title: pageUrlIs @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @return void return
	 *         type @throws
	 */

	public void pageUrlIs(String params1) {
		// throw new PendingException();
		String url = driver.getCurrentUrl();
		Assert.assertEquals(params1, url);
	}

	/**
	 * @Title: pageContains @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @return void return
	 *         type @throws
	 */

	public void pageContains(String params1) {
		// throw new PendingException();
		boolean sourcecontent = driver.getPageSource().contains(params1);
		Assert.assertTrue(sourcecontent);
	}

	/**
	 * @Title: pageRefresh @Description: TODO @author
	 *         ahu@greendotcorp.com @param @return void return type @throws
	 */

	public void pageRefresh() {
		refreshPage();
	}

	/**
	 * @Title: pageContainText @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param text @return void return
	 *         type @throws
	 */

	public void pageContainText(String text) {
		// throw new PendingException();
		boolean textpresent = driver.getPageSource().contains(text);
		// log.info("Verify the element text present in the page,the text found
		// is:"+textpresent);
		Assert.assertTrue(textpresent);
	}

	/**
	 * @Title: refreshPage @Description: TODO @author
	 *         ahu@greendotcorp.com @param @return void return type @throws
	 */

	public void refreshPage() {
		// throw new PendingException();
		// log.info("Now refresh the page to keep the session valid");
		// or blow
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.F5).perform();
	}

	/**
	 * @Title: assertElementIsPresented @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void assertElementIsPresented(WebElement e) {
		// throw new PendingException();
		elementIsPresented(e);
	}

	/**
	 * @Title: assertElementIsNotPresented @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void assertElementNotPresented(WebElement e) {
		elementIsNotPresented(e);
	}

	/**
	 * @Title: assertDropdownListContainOptions @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         options @return void return type @throws
	 */

	public void assertDropdownListContainOptions(WebElement e, List<String> options) {
		dropdownListContainOptions(e, options);
	}

	/**
	 * @Title: assertElementTextIs @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param element @param @param
	 *         expectedtext @return void return type @throws
	 */

	public void assertElementTextIs(WebElement element, String expectedtext) {
		elementTextIs(element, expectedtext);
	}

	/**
	 * @Title: assertElementContentIs @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param element @param @param
	 *         expectedtext @return void return type @throws
	 */

	public void assertElementContentIs(WebElement element, String expectedtext) {
		elementTextIs(element, expectedtext);
	}

	/**
	 * @Title: assertElementIsEnabled @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         params2 @return void return type @throws
	 */

	public void assertElementIsEnabled(WebElement e) {
		// throw new PendingException();
		elementIsEnabled(e);

	}

	/**
	 * @Title: assertElementIsSelected @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void assertElementIsSelected(WebElement e) {
		elementIsSelected(e);
	}

	/**
	 * @Title: assertRadioButtonIsSelected @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void assertRadioButtonIsSelected(WebElement e) {
		elementIsSelected(e);
	}

	/**
	 * @Title: assertElementIsNotSelected @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void assertElementIsNotSelected(WebElement e) {
		elementIsNotSelected(e);
	}

	/**
	 * @Title: assertElementAttributeValueIs @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         attributename @param @param params3 @return void return
	 *         type @throws
	 */

	public void assertElementAttributeValueIs(WebElement e, String attributename, String params3) {
		elementAttributeValueIs(e, attributename, params3);
	}

	/**
	 * @Title: assertPageUrlIs @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param url @return void return
	 *         type @throws
	 */

	public void assertPageUrlIs(String url) {
		pageUrlIs(url);
	}

	/**
	 * @Title: assertPageTitleIs @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param title @return void return
	 *         type @throws
	 */

	public void assertPageTitleIs(String title) {
		pageTitleIs(title);
	}

	/**
	 * @Title: elementIsPresented @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementIsPresented(WebElement e) {
		// throw new PendingException();
		boolean displayed = e.isDisplayed();
		Assert.assertTrue(displayed);
	}

	/**
	 * @Title: elementIsPresented @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementIsNotPresented(WebElement e) {
		// throw new PendingException();
		boolean displayed = e.isDisplayed();
		Assert.assertFalse(displayed);
	}

	/**
	 * @Title: waitForSeconds @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param s @return void return
	 *         type @throws
	 */

	public static void waitForSeconds(int s) {
		// throw new PendingException();
		// int seconds=Integer.parseInt(s);
		try {
			Thread.sleep(s * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("Sleep current step met an error:" + e.getMessage());
		}
	}

	/**
	 * @return the WebDriverWait element.
	 */
	protected WebDriverWait waitforElement() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, SeleniumCore.DEFAULT_WEBELEMENT_LOADING_TIME);
		return webDriverWait;
	}

	/**
	 * @Title: waitForSeconds @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void waitForElementPresent(WebElement e) {
		// throw new PendingException();
		// int seconds=Integer.parseInt(s);
		waitforElement().until(ExpectedConditions.visibilityOf(e));
	}

	/**
	 * @Title: findElementByXpath @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param xpath @param @return @return
	 *         WebElement return type @throws
	 */

	public WebElement waitForElementByXpath(String xpath) {
		WebElement e = null;
		try {
			waitforElement().until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

		} catch (TimeoutException ex) {
			// TODO: handle exception
			// e.printStackTrace();
		}
		return e;
	}

	/**
	 * @Title: waitForStableElement @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param locator @param @return @return
	 *         WebElement return type @throws
	 */

	public WebElement waitForStableElement(final By locator, Object page) {
		// resolve the pagefactory issue
		pageElementsRelocatored(page);

		waitforElement().until(ExpectedConditions.presenceOfElementLocated(locator));
		try {
			return driver.findElement(locator);
		} catch (StaleElementReferenceException e) {
			System.out.println("Attempting to recover from StaleElementReferenceException ..." + locator);
			return waitForStableElement(locator, page);
		}
	}

	/**
	 * @Title: resolveStableElementException @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param locator @param @param
	 *         page @param @return @return WebElement return type @throws
	 */

	public WebElement resolveStableElementException(final By locator, Object page) {
		return waitForStableElement(locator, page);
	}

	/**
	 * @Title: waitForStableElements @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param locator @param @return @return
	 *         List<WebElement> return type @throws
	 */

	public List<WebElement> waitForStableElements(final By locator, Object page) {
		// resolve the pagefactory issue
		pageElementsRelocatored(page);
		return waitforElement().until(new ExpectedCondition<List<WebElement>>() {
			@Override
			public List<WebElement> apply(WebDriver d) {
				try {
					return d.findElements(locator);
				} catch (StaleElementReferenceException ex) {
					return null;
				}
			}
		});
	}

	/**
	 * @Title: waitElementbePresent @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @param @param
	 *         e @return void return type @throws
	 */

	public void waitforElementbePresent(WebElement e) {
		// throw new PendingException();
		int waitcount = 0;
		boolean isDisplayed = e.isDisplayed();
		while (!isDisplayed) {
			waitcount = waitcount + 1;
			waitForSeconds(3);
			isDisplayed = e.isDisplayed();
			if (waitcount == 60) {
				log.error("Waitting for the object displayed status-the object cannot show in the page:"
						+ e.getTagName() + ",exit the identify the object ");
				break;
			}

		}
		Assert.assertTrue(isDisplayed);
	}

	/**
	 * @Title: waitAjaxbePresent @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @param @param
	 *         e @return void return type @throws
	 */

	public void waitAjaxbePresent(String params1, final WebElement e) {
		// throw new PendingException();

		boolean findobject = false;
		long time = Long.parseLong(params1);
		WebDriverWait wait = new WebDriverWait(driver, time);

		try {
			wait.until(new ExpectedCondition<Boolean>() {

				@Override
				public Boolean apply(WebDriver driver) {
					// log.info("Enter the waitForObjectDisplay method to wait
					// for the object displayed in the page ");
					return e.isDisplayed();
				}
			});
			findobject = true;
		} catch (TimeoutException te) {
			// log.info("throw expection ,cannot find the web
			// element:"+te.getMessage());
			// logger.info("the time out is 120 ,we cannot find this
			// webelment:"+xpathExpression);
			Assert.fail("Cannot find this web element in the page:" + e.getText());
		}

		Assert.assertTrue(findobject);
	}

	/**
	 * @Title: selectValue @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         params1 @return void return type @throws
	 */

	public void selectValue(WebElement e, String params1) {
		// throw new PendingException();
		// log.info("selectValue: Select option index from the list ,the option
		// value is:"
		// + params1);
		highLight(e);
		Select select = new Select(e);
		select.selectByValue(params1);
	}

	/**
	 * @Title: selectDropDownValue @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param value @return
	 *         void return type @throws
	 */

	public void selectDropDownValue(WebElement e, String value) {
		selectValue(e, value);
	}

	/**
	 * @Title: selectVisibleText @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         params1 @return void return type @throws
	 */

	public void selectVisibleText(WebElement e, String params1) {
		// throw new PendingException();
		// log.info("selectValue: Select option index from the list ,the option
		// value is:"
		// + params1);
		// highLight(e);
		Select select = new Select(e);
		select.selectByVisibleText(params1);
	}

	public void selectDropDownVisibleValue(WebElement e, String value) {
		selectVisibleText(e, value);
	}

	/**
	 * @Title: dropdownListContainOptions @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         dropdownoptions @return void return type @throws
	 */

	public void dropdownListContainOptions(WebElement e, List<String> dropdownoptions) {
		// throw new PendingException();
		List<String> elements = new ArrayList<String>();
		// log.info("Select option index from the list ,list element option
		// value is:"
		// + e);
		highLight(e);
		Select select = new Select(e);
		List<WebElement> options = select.getOptions();
		for (WebElement item : options) {
			elements.add(item.getText());
		}

		Assert.assertEquals(dropdownoptions, elements);

	}

	/**
	 * @Title: clearAndInputValue @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param value @return
	 *         void return type @throws
	 */

	public void clearAndInputValue(WebElement e, String value) {
		// throw new PendingException();
		highLight(e);
		e.clear();
		e.sendKeys(value);

	}

	/**
	 * @Title: elementFocus @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementFocus(WebElement e) {
		focusElment(e);
	}

	/**
	 * @Title: focusElment @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void focusElment(WebElement e) {
		if ("input".equals(e.getTagName())) {
			e.sendKeys("");
		} else {
			new Actions(driver).moveToElement(e).perform();

		}
	}

	/**
	 * @Title: inputValue @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         params1 @return void return type @throws
	 */

	public void inputValue(WebElement e, String params1) {
		// throw new PendingException();
		// e.clear();
		highLight(e);
		e.sendKeys(params1);

	}

	/**
	 * @Title: setValue @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param value @return
	 *         void return type @throws
	 */

	public void setValue(WebElement e, String value) {
		inputValue(e, value);
	}

	/**
	 * @Title: inputValueJS @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         params1 @return void return type @throws
	 */

	public void inputValueViaJS(WebElement e, String value) {
		// throw new PendingException();
		// e.clear();
		runJS("arguments[0].setAttribute('value', '" + value + "')", e);
	}

	/**
	 * @Title: inputValueJS @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param elementlocator @param @param
	 *         value @return void return type @throws
	 */

	public void inputValueViaJS(String elementlocator, String value) {
		// throw new PendingException();
		// e.clear();
		runJS("$('" + elementlocator + "').val('" + value + "')");
	}

	/**
	 * @Title: inputKey @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         params1 @return void return type @throws
	 */

	public void inputKey(WebElement e, Keys params1) {
		// throw new PendingException();
		// e.clear();
		highLight(e);
		e.sendKeys(params1);

	}

	/**
	 * @Title: checkElement @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void checkedElement(WebElement e) {
		// throw new PendingException();
		highLight(e);
		if (!(e.isSelected())) {
			// log.info("Check the checkbox,the webelment :" + e.getTagName()
			// + e.getText() + ",had been selected now");
			e.click();
		} else {
			// log.info("Check the checkbox,the webelment :" + e.getTagName()
			// + e.getText() + ",had been selected by default");
		}

	}

	/**
	 * @Title: checkBoxElement @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void checkBoxElement(WebElement e) {
		// throw new PendingException();
		checkedElement(e);

	}

	/**
	 * @Title: checkRadioBox @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void checkRadioBox(WebElement e) {
		checkedElement(e);
	}

	/**
	 * @Title: elementTextIs @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         params2 @return void return type @throws
	 */

	public void elementTextIs(WebElement e, String params2) {
		// throw new PendingException();
		// log.info("Get the element text.The webelement is:" + e.getTagName()
		// + ",the text in the webelement is:" + e.getText().trim());
		highLight(e);
		String visibletext = e.getText().trim();
		Assert.assertEquals(params2.trim(), visibletext);
	}

	/**
	 * @Title: elementWithText @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params2 @param @return @return
	 *         WebElement return type @throws
	 */

	public WebElement elementWithText(String params2) {
		// throw new PendingException();
		// log.info("Get the element text.The webelement is:" + e.getTagName()
		// + ",the text in the webelement is:" + e.getText().trim());
		WebElement findElementContainText = findElementContainText(params2);
		return findElementContainText;
	}

	/**
	 * @Title: elementTextIs @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @return @return
	 *         String return type @throws
	 */

	public String elementTextIs(WebElement e) {
		// throw new PendingException();
		// log.info("Get the element text.The webelement is:" + e.getTagName()
		// + ",the text in the webelement is:" + e.getText().trim());
		highLight(e);
		String visibletext = e.getText().trim();
		return visibletext;
	}

	/**
	 * @Title: getElementText @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @return @return
	 *         String return type @throws
	 */

	public String getElementText(WebElement e) {
		return elementTextIs(e);
	}

	/**
	 * @Title: getAlertMessage @Description: TODO @author
	 *         ahu@greendotcorp.com @param @return @return String return
	 *         type @throws
	 */

	public String getAlertMessage() {
		Object objmsg = runJS("return window.message;");
		return objmsg.toString();
	}

	/**
	 * @Title: pageElementsRelocatored @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param page @return void return
	 *         type @throws
	 */

	public void pageElementsRelocatored(Object page) {
		PageFactory.initElements(driver, page);
	}

	/**
	 * @Title: elementCSSValue @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         attr @param @return @return String return type @throws
	 */

	public String elementCSSValue(WebElement e, String attr) {
		return e.getCssValue(attr);
	}

	/**
	 * @Title: getCSSValue @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         attr @param @return @return String return type @throws
	 */

	public String getCSSValue(WebElement e, String attr) {
		return elementCSSValue(e, attr);
	}

	/**
	 * @Title: elementStatusIs @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         params2 @return void return type @throws
	 */

	public void elementIsEnabled(WebElement e) {
		// throw new PendingException();
		highLight(e);
		boolean enabled = e.isEnabled();
		Assert.assertTrue(enabled);

	}

	/**
	 * @Title: elementIsSelected @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementIsSelected(WebElement e) {
		// throw new PendingException();
		highLight(e);
		boolean selected = e.isSelected();
		Assert.assertTrue(selected);
	}

	/**
	 * @Title: elementIsNotSelected @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementIsNotSelected(WebElement e) {
		// throw new PendingException();
		highLight(e);
		boolean selected = e.isSelected();
		Assert.assertFalse(selected);
	}

	/**
	 * @Title: elementAttributeValueIs @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @param
	 *         attributename @param @param params3 @return void return
	 *         type @throws
	 */

	public void elementAttributeValueIs(WebElement e, String attributename, String params3) {
		// throw new PendingException();
		String attributevalue = e.getAttribute(attributename);
		// log.info("Get the webelement Attribute-the webelement's attribute:"
		// + attributename + " value is:" + attributevalue);
		Assert.assertEquals(params3, attributevalue);
	}

	/**
	 * @Title: findElementsWithTagname @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @param @return @return
	 *         List<WebElement> return type @throws
	 */

	public List<WebElement> findElementsWithTagname(String params1) {
		// throw new PendingException();
		List<WebElement> findElements = driver.findElements(By.tagName(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithCSS @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @param @return @return
	 *         WebElement return type @throws
	 */

	public WebElement findElementWithCSS(String params1) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.cssSelector(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithXPath @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @param @return @return
	 *         WebElement return type @throws
	 */

	public WebElement findElementWithXPath(String params1) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.xpath(params1));
		return findElements;
	}

	/**
	 * @Title: findElementsWithXPath @Description: TODO @author
	 *         Judy.Zhu@greendotcorp.com @param @param
	 *         params1 @param @return @return List<WebElement> return
	 *         type @throws
	 */

	public List<WebElement> findElementsWithXPath(String params1) {
		// throw new PendingException();
		List<WebElement> findElements = driver.findElements(By.xpath(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithID @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @param @return @return
	 *         WebElement return type @throws
	 */

	public WebElement findElementWithID(String params1) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.id(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithClassName @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @param @return @return
	 *         WebElement return type @throws
	 */

	public WebElement findElementWithClassName(String params1) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.className(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithLinkText @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @param @return @return
	 *         WebElement return type @throws
	 */

	public WebElement findElementWithLinkText(String params1) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.linkText(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithName @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @param @return @return
	 *         WebElement return type @throws
	 */

	public WebElement findElementWithName(String params1) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.name(params1));
		return findElements;
	}

	/**
	 * @param params1
	 * @return List<WebElement>
	 */
	public List<WebElement> findElementsWithLinkText(String params1) {
		List<WebElement> findElements = driver.findElements(By.linkText(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithpartialLinkText @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @param @return @return
	 *         WebElement return type @throws
	 */

	public WebElement findElementWithpartialLinkText(String params1) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.partialLinkText(params1));

		return findElements;
	}

	/**
	 * @Title: findElementContainText @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param params1 @param @return @return
	 *         WebElement return type @throws
	 */

	public WebElement findElementContainText(String text) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.xpath("//*[contains(text(), '" + text + "')]"));

		return findElements;
	}

	/**
	 * @Title: findElementWithButtonText @Description: TODO @author
	 *         Judy.Zhu@greendotcorp.com @param @param
	 *         params1 @param @return @return WebElement return type @throws
	 */

	public WebElement findElementWithButtonText(String text) {
		// throw new PendingException();
		WebElement findElement = driver.findElement(By.xpath("//button[contains(text(),'" + text + "')]"));

		return findElement;
	}

	/**
	 * @Title: findElementWithTextboxPlaceholder @Description: TODO @author
	 *         Judy.Zhu@greendotcorp.com @param @param
	 *         params1 @param @return @return WebElement return type @throws
	 */

	public WebElement findElementWithTextboxPlaceholder(String text) {
		WebElement textbox = findElementWithXPath("//input[@placeholder=\"" + text + "\"]");

		return textbox;
	}

	/**
	 * @Title: waitForPageFullsync @Description: TODO @author
	 *         ahu@greendotcorp.com @param @return void return type @throws
	 */

	public void waitForPageFullsync() {
		// log.info("waitForPageFullsync: Current browser state
		// is:"+currentbowserstate);
		// wait for jQuery to load
		ExpectedCondition<Boolean> jqueryload = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				// TODO Auto-generated method stub
				Long newpagestate = (Long) runJS("return jQuery.active;");
				log.debug("the new page state is:" + newpagestate);
				return (newpagestate == 0);
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsload = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				// TODO Auto-generated method stub
				String newpagestate = (String) runJS("return document.readyState;");
				log.debug("the new page state is:" + newpagestate);
				return (newpagestate.equals("complete"));
			}
		};
		try {
			WebDriverWait waitforElement = waitforElement();
			boolean loaded = waitforElement.until(jqueryload) && waitforElement.until(jsload);
			log.debug("finally the load is loading with completed status is:" + loaded);
		} catch (org.openqa.selenium.WebDriverException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @Title: waitForPageFullVisible @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param element @return void return
	 *         type @throws
	 */

	public void waitForPageFullVisible(WebElement element) {
		// log.info("waitForPageFullsync: Current browser state
		// is:"+currentbowserstate);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(SeleniumCore.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
				.pollingEvery(SeleniumCore.DEFAULT_WEBELEMENT_LOADING_TIME, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);

		wait.until(ExpectedConditions.visibilityOf(element));

	}

	/**
	 * @Title: switchToWindowWithTitle @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param windowTitle @return void
	 *         return type @throws
	 */

	public void switchToWindowWithTitle(String windowTitle) {
		// throw new PendingException();

		try {
			Robot robot = new Robot();
			Set<String> allwindows = driver.getWindowHandles();
			for (String window : allwindows) {
				driver.switchTo().window(window);
				if (driver.getTitle().contains(windowTitle)) {
					robot.delay(5000);
					// robot.keyPress(keycode);
				}
			}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @Title: elementHtmlCodeIs @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @param @return @return
	 *         String return type @throws
	 */

	public String elementHtmlCodeIs(WebElement e) {
		// throw new PendingException();
		String contents = (String) runJS("return arguments[0].innerHTML;", e);
		return contents;
	}

	/**
	 * @Title: scrollPagetoElement @Description: TODO @author
	 *         ahu@greendotcorp.com @param @param e @return void return
	 *         type @throws
	 */

	public void scrollPagetoElement(WebElement e) {
		// throw new PendingException();
		highLight(e);
		runJS("window.scrollTo(0," + e.getLocation().y + ")");
		runJS("arguments[0].scrollIntoView(true);", e);
		// log.info("Now we scroll the view to the position we can see");

	}

	/**
	 * @Title: hideAlert @Description: TODO @author
	 *         ahu@greendotcorp.com @param @return void return type @throws
	 */

	public void hideAlert() {
		runJS("window.alert=function(msg){window.message=msg};");
	}

	/**
	 * @Title: isAlertPresent @Description: TODO @author
	 *         ahu@greendotcorp.com @param @return @return boolean return
	 *         type @throws
	 */

	public boolean isAlertPresent() {

		boolean presentFlag = false;

		try {
			Alert alert = waitforElement().until(ExpectedConditions.alertIsPresent());
			// Check the presence of alert
			// Alert alert = driver.switchTo().alert();
			// Alert present; set the flag
			alert.authenticateUsing(new UserAndPassword("ahu", "gu.chan-10266"));
			presentFlag = true;
			// if present consume the alert
			alert.accept();

		} catch (NoAlertPresentException ex) {
			// Alert not present
			// ex.printStackTrace();
		} catch (TimeoutException e) {
			// TODO: handle exception
			// e.printStackTrace();
		}

		// or
		// Don't use firefox profile and try below code:
		// window.alert=function(msg){window.test=msg};
		/*
		 * window.alert(msg) alert("dddd") undefined window.test "dddd"
		 */

		// driver.get("http://UserName:Password@Example.com");
		return presentFlag;

	}

	/**
	 * @Title: isChromeEmulation @Description: TODO @author
	 *         ahu@greendotcorp.com @param @return @return boolean return
	 *         type @throws
	 */

	public boolean isChromeEmulationBrowser() {
		Capabilities actualCapabilities = ((RemoteWebDriver) driver).getCapabilities();
		String browser = actualCapabilities.getBrowserName();
		Object isemulator = actualCapabilities.getCapability("mobileEmulationEnabled");
		if (isemulator != null) {
			boolean isemulatorbool = (boolean) isemulator;
			if (browser.equalsIgnoreCase("chrome") && isemulatorbool) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @Title: getOutputlog @Description: TODO @author
	 *         ahu@greendotcorp.com @param @return void return type @throws
	 */

	public void getOutputlog() {
		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
		for (LogEntry entry : logEntries) {
			log.info(Calendar.getInstance().getTime() + " " + entry.getLevel() + " " + entry.getMessage());
			// do something useful with the data
		}
	}

	public void switchtoDesktopPage() {

	}

	/**
	 * @Title: switchToIframe @Description: TODO @author
	 *         Judy.Zhu@greendotcorp.com @param @return void return type @throws
	 */

	public void switchToIframe(int iframeSequence) {
		driver.switchTo().frame(driver.findElements(By.tagName("iframe")).get(iframeSequence));
	}

	/**
	 * getBrowserType:(get the current running browser type and version number).
	 * 
	 * @param driver
	 *            ---the web driver instance
	 * 
	 * @since JDK 1.6
	 * @return String --- the browser type and version number
	 */
	public static String getBrowserType(RemoteWebDriver driver) {
		Capabilities caps = driver.getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();
		// String platform=caps.getPlatform().toString();
		log.info("Get the current running browser is:" + browserName + ",the browser version is:" + browserVersion);
		return browserName + " " + browserVersion;
	}

	/**
	 * getBrowserType:(get the current running browser type and version number).
	 * 
	 * @param driver
	 *            ---the web driver instance
	 * 
	 * @since JDK 1.6
	 * @return String --- the browser type and version number
	 */
	public static String BrowserType(RemoteWebDriver driver) {
		return getBrowserType(driver);
	}

}
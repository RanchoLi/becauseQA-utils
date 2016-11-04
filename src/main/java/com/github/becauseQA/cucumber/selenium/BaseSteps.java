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
 * @author alterhu2020@gmail.com
 * @date Jun 25, 2015 11:17:17 PM
 * 
 */

public class BaseSteps {

	public static WebDriver driver = SeleniumCore.driver;
	public static final Logger log = Logger.getLogger(BaseSteps.class);

	/**
	 * Run java script in the page using selenium
	 * 
	 * @param script
	 *            The java script need to use
	 * @param e
	 *            the web element which will use this script
	 * @return the java script return's object
	 */
	public static Object javascript(String script, Object... e) {
		// logger.info("Run the javascript from page ,the java script is:"
		// + script);
		// highLight(e);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		return je.executeScript(script, e);

	}

	/**
	 * Run js not sync simple thread
	 * 
	 * @param script
	 *            js script
	 * @return the js object
	 */
	public static Object javescriptAsync(String script, Object... element) {
		if (driver == null)
			driver = SeleniumCore.driver;
		JavascriptExecutor je = (JavascriptExecutor) driver;
		return je.executeAsyncScript(script, element);

	}

	/**
	 * http://www.w3schools.com/jsref/dom_obj_style.asp
	 * 
	 * @Title: highLight @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 * 
	 */

	public void elementHighLight(WebElement e) {
		elementHighLight(e, 300);
	}

	/**
	 * http://www.w3schools.com/jsref/dom_obj_style.asp
	 * 
	 * @Title: highLight @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 * 
	 */

	public void elementHighLight(WebElement e, long timeInMillseconds) {
		if (driver instanceof JavascriptExecutor) {
			String jsString = "element = arguments[0];\r\n" + "original_style = element.getAttribute('style');"
					+ "element.setAttribute('style', original_style + \"; backgroundColor: yellow; border: 2px solid red;outline:#8f8 solid 3px;\");"
					+ "setTimeout(function(){try {if (!element.parentNode) {return;} element.setAttribute('style', original_style);}catch (e) {} }, "
					+ timeInMillseconds + ");";

			javascript(jsString, e);
		}

	}

	/**
	 * @Title: clickElement
	 * @Description: TODO
	 * @author alterhu2020@gmail.com
	 * @param @param
	 *            e
	 * @return void return type
	 * @throws @fixing
	 *             https://code.google.com/p/selenium/issues/detail?id=2766
	 *             Element is not clickable at point (378.5, 595.5). Other
	 *             element would receive the click:
	 */

	public void elementClick(WebElement e) {
		// throw new PendingException();
		elementHighLight(e);
		elementScrollIntoView(e);
		e.click();
	}

	/**
	 * @Title: clickElementJS @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementClickViaJavascript(WebElement e) {
		// log.info("Click elements in page-clicked this element:"
		// + e.getTagName() + ",the text is:" + e.getText());
		// In chrome browser this function didn't work ,so give a solution to
		// load the page correctly
		elementHighLight(e);
		javascript("arguments[0].click();", e);
	}

	/**
	 * @Title: rightClickElement @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementRightClick(WebElement e) {
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
	 *         alterhu2020@gmail.com @param @param id @return void return
	 *         type @throws
	 */

	public void elementClickById(String id) {
		// throw new PendingException();
		// log.info("Click elements in page-clicked this element html id
		// is"+id);
		// In chrome browser this function didn't work ,so give a solution to
		// load the page correctly
		// highLight(e);
		javascript("document.getElementById('" + id + "').click();");
		// log.info("Clicked element's html ID is:"+id);
	}

	/**
	 * @Title: clickElementUsingMouse @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementMouseClick(WebElement e) {
		// throw new PendingException();
		// log.info("Click elements in page-clicked this element:"
		// + e.getTagName() + ",the text is:" + e.getText());
		// In chrome browser this function didn't work ,so give a solution to
		// load the page correctly
		// ((JavascriptExecutor)
		// driver).executeScript("window.scrollTo(0,"+e.getLocation().y+")");
		elementHighLight(e);
		new Actions(driver).moveToElement(e).clickAndHold().release().build().perform();

	}

	/**
	 * @Title: inputValue @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @param
	 *         params1 @return void return type @throws
	 */

	public void elementInput(WebElement e, String params1) {
		// throw new PendingException();
		// e.clear();
		elementHighLight(e);
		e.sendKeys(params1);

	}

	/**
	 * @Title: clearAndInputValue @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @param value @return
	 *         void return type @throws
	 */

	public void elementInputBeforeClear(WebElement e, String value) {
		// throw new PendingException();
		elementHighLight(e);
		e.clear();
		e.sendKeys(value);

	}

	/**
	 * @Title: inputValueJS @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @param
	 *         params1 @return void return type @throws
	 */

	public void elementInputByJavascriptAttribute(WebElement e, String value) {
		// throw new PendingException();
		// e.clear();
		javascript("arguments[0].setAttribute('value', '" + value + "')", e);
	}

	/**
	 * @Title: inputValueJS @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param elementlocator @param @param
	 *         value @return void return type @throws
	 */

	public void elementInputByJavascriptJquery(String elementlocator, String value) {
		// throw new PendingException();
		// e.clear();
		javascript("$('" + elementlocator + "').val('" + value + "')");
	}

	/**
	 * @Title: inputKey @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @param
	 *         params1 @return void return type @throws
	 */

	public void elementInputKey(WebElement e, Keys params1) {
		// throw new PendingException();
		// e.clear();
		elementHighLight(e);
		e.sendKeys(params1);

	}

	/**
	 * @Title: checkElement @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementSelectOrCheck(WebElement e) {
		// throw new PendingException();
		elementHighLight(e);
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
	 * @Title: selectValue @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @param
	 *         params1 @return void return type @throws
	 */

	public void elementSelectByValue(WebElement e, String params1) {
		// throw new PendingException();
		// log.info("selectValue: Select option index from the list ,the option
		// value is:"
		// + params1);
		elementHighLight(e);
		Select select = new Select(e);
		select.selectByValue(params1);
	}

	/**
	 * Element tag should be select not ul
	 * 
	 * @param e
	 * @param params1
	 */
	public void elementSelectByVisibleText(WebElement e, String params1) {
		// throw new PendingException();
		// log.info("selectValue: Select option index from the list ,the option
		// value is:"
		// + params1);
		// highLight(e);
		Select select = new Select(e);
		select.selectByVisibleText(params1);
	}

	/**
	 * @Title: dropdownListContainOptions @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @param
	 *         dropdownoptions @return void return type @throws
	 */

	public void elementSelectContainOptions(WebElement e, List<String> dropdownoptions) {
		// throw new PendingException();
		List<String> elements = new ArrayList<String>();
		// log.info("Select option index from the list ,list element option
		// value is:"
		// + e);
		elementHighLight(e);
		Select select = new Select(e);
		List<WebElement> options = select.getOptions();
		for (WebElement item : options) {
			elements.add(item.getText());
		}

		Assert.assertEquals(dropdownoptions, elements);

	}

	/**
	 * @Title: focusElment @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementFocus(WebElement e) {
		if ("input".equals(e.getTagName())) {
			e.sendKeys("");
		} else {
			new Actions(driver).moveToElement(e).perform();

		}
	}

	/**
	 * @Title: elementTextIs @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @param
	 *         params2 @return void return type @throws
	 */

	public void elementTextIs(WebElement e, String params2) {
		// throw new PendingException();
		// log.info("Get the element text.The webelement is:" + e.getTagName()
		// + ",the text in the webelement is:" + e.getText().trim());
		elementHighLight(e);
		String visibletext = e.getText().trim();
		Assert.assertEquals(params2.trim(), visibletext);
	}

	/**
	 * @Title: elementTextIs @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @return @return
	 *         String return type @throws
	 */

	public String elementTextIs(WebElement e) {
		// throw new PendingException();
		// log.info("Get the element text.The webelement is:" + e.getTagName()
		// + ",the text in the webelement is:" + e.getText().trim());
		elementHighLight(e);
		String visibletext = e.getText().trim();
		return visibletext;
	}

	/**
	 * @Title: elementAttributeValueIs @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @param
	 *         attributename @param @param params3 @return void return
	 *         type @throws
	 */

	public String elementAttributeValueIs(WebElement e, String attributename) {
		// throw new PendingException();
		String attributevalue = e.getAttribute(attributename);
		return attributevalue;
	}

	/**
	 * @Title: elementAttributeValueIs @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @param
	 *         attributename @param @param params3 @return void return
	 *         type @throws
	 */

	public void elementAttributeValueIs(WebElement e, String attributename, String params3) {
		// throw new PendingException();
		String attributevalue = e.getAttribute(attributename);
		Assert.assertEquals(params3, attributevalue);
	}

	/**
	 * @Title: elementCSSValue @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @param
	 *         attr @param @return @return String return type @throws
	 */

	public String elementCSSValueIs(WebElement e, String attr) {
		return e.getCssValue(attr);
	}

	/**
	 * @Title: assertElementIsPresented @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementDisplayed(WebElement e) {
		// throw new PendingException();
		boolean displayed = e.isDisplayed();
		Assert.assertTrue(displayed);
	}

	/**
	 * @Title: assertElementIsNotPresented @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementNotDisplayed(WebElement e) {
		boolean displayed = e.isDisplayed();
		Assert.assertFalse(displayed);
	}

	/**
	 * @Title: assertElementIsPresented @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 *         type @throws
	 */

	public boolean elementPresent(WebElement e) {
		// throw new PendingException();
		boolean foundElement = false;
		try {
			foundElement = e.isDisplayed();
		} catch (Exception ex) {
			// TODO: handle exception
		}
		return foundElement;
	}

	/**
	 * @Title: elementStatusIs @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @param
	 *         params2 @return void return type @throws
	 */

	public void elementIsEnabled(WebElement e) {
		// throw new PendingException();
		elementHighLight(e);
		boolean enabled = e.isEnabled();
		Assert.assertTrue(enabled);

	}

	/**
	 * @Title: elementStatusIs @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @param
	 *         params2 @return void return type @throws
	 */

	public boolean elementEnabled(WebElement e) {
		boolean enabled = e.isEnabled();
		return enabled;
	}

	/**
	 * @Title: elementIsSelected @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementIsSelected(WebElement e) {
		// throw new PendingException();
		elementHighLight(e);
		boolean selected = e.isSelected();
		Assert.assertTrue(selected);
	}

	/**
	 * @Title: elementIsSelected @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 *         type @throws
	 */

	public boolean elementSelected(WebElement e) {
		// throw new PendingException();
		// elementHighLight(e);
		boolean selected = e.isSelected();
		return selected;
	}

	/**
	 * @Title: elementIsNotSelected @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementIsNotSelected(WebElement e) {
		// throw new PendingException();
		elementHighLight(e);
		boolean selected = e.isSelected();
		Assert.assertFalse(selected);
	}

	/**
	 * @Title: elementHtmlCodeIs @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @param @return @return
	 *         String return type @throws
	 */

	public String elementHtmlCodeIs(WebElement e) {
		// throw new PendingException();
		String contents = (String) javascript("return arguments[0].innerHTML;", e);
		return contents;
	}

	/**
	 * @Title: scrollPagetoElement @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementScrollIntoView(WebElement e) {
		// throw new PendingException();
		elementHighLight(e);
		javascript("window.scrollTo(0," + e.getLocation().y + ")");
		javascript("arguments[0].scrollIntoView(true);", e);
		// log.info("Now we scroll the view to the position we can see");

	}

	/**
	 * @Title: waitForPageFullVisible @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param element @return void return
	 *         type @throws
	 */

	public void elementWaitVisible(WebElement element) {
		// log.info("waitForPageFullsync: Current browser state
		// is:"+currentbowserstate);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(SeleniumCore.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
				.pollingEvery(SeleniumCore.DEFAULT_WEBELEMENT_LOADING_TIME, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);

		wait.until(ExpectedConditions.visibilityOf(element));

	}

	/**
	 * @Title: waitForSeconds @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param e @return void return
	 *         type @throws
	 */

	public void elementWaitPresent(WebElement e) {
		// throw new PendingException();
		// int seconds=Integer.parseInt(s);
		AbstractWebDriverWait().until(ExpectedConditions.visibilityOf(e));
	}

	/**
	 * @Title: findElementByXpath @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param xpath @param @return @return
	 *         WebElement return type @throws
	 */

	public WebElement elementWaitPresent(String xpath) {
		WebElement e = null;
		try {
			AbstractWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

		} catch (TimeoutException ex) {
			// TODO: handle exception
			// e.printStackTrace();
		}
		return e;
	}

	/**
	 * @Title: waitElementbePresent @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param params1 @param @param
	 *         e @return void return type @throws
	 */

	public void elementWaitDisplayed(WebElement e) {
		// throw new PendingException();
		int waitcount = 0;
		boolean isDisplayed = e.isDisplayed();
		while (!isDisplayed) {
			waitcount = waitcount + 1;
			pageWait(3);
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
	 *         alterhu2020@gmail.com @param @param params1 @param @param
	 *         e @return void return type @throws
	 */

	public void elementWaitPresentAjax(final WebElement e, String timeoutInSeconds) {
		// throw new PendingException();

		boolean findobject = false;
		long time = Long.parseLong(timeoutInSeconds);
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
	 * @Title: waitForStableElement @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         locator @param @return @return WebElement return type @throws
	 */

	public WebElement elementWaitStable(final By locator, Object pageClass) {
		// resolve the pagefactory issue
		pageFactoryReload(pageClass);

		AbstractWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(locator));
		try {
			return driver.findElement(locator);
		} catch (StaleElementReferenceException e) {
			System.out.println("Attempting to recover from StaleElementReferenceException ..." + locator);
			return elementWaitStable(locator, pageClass);
		}
	}

	/**
	 * @Title: waitForStableElements @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         locator @param @return @return List<WebElement> return
	 *         type @throws
	 */

	public List<WebElement> elementsWaitStable(final By locator, Object page) {
		// resolve the pagefactory issue
		pageFactoryReload(page);
		return AbstractWebDriverWait().until(new ExpectedCondition<List<WebElement>>() {
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
	 * @Title: findElementsWithTagname @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         params1 @param @return @return List<WebElement> return
	 *         type @throws
	 */

	public List<WebElement> elementFindByTagName(String params1) {
		// throw new PendingException();
		List<WebElement> findElements = driver.findElements(By.tagName(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithCSS @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         params1 @param @return @return WebElement return type @throws
	 */

	public WebElement elementFindByCSS(String params1) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.cssSelector(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithXPath @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         params1 @param @return @return WebElement return type @throws
	 */

	public WebElement elementFindByXPath(String params1) {
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

	public List<WebElement> elementsFindByXPath(String params1) {
		// throw new PendingException();
		List<WebElement> findElements = driver.findElements(By.xpath(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithID @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         params1 @param @return @return WebElement return type @throws
	 */

	public WebElement elementFindByID(String params1) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.id(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithClassName @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         params1 @param @return @return WebElement return type @throws
	 */

	public WebElement elementFindByClassName(String params1) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.className(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithLinkText @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         params1 @param @return @return WebElement return type @throws
	 */

	public WebElement elementFindByLinkText(String params1) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.linkText(params1));
		return findElements;
	}

	/**
	 * @param params1
	 * @return List<WebElement>
	 */
	public List<WebElement> elementFindByLinkTexts(String params1) {
		List<WebElement> findElements = driver.findElements(By.linkText(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithName @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         params1 @param @return @return WebElement return type @throws
	 */

	public WebElement elementFindByName(String params1) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.name(params1));
		return findElements;
	}

	/**
	 * @Title: findElementWithpartialLinkText @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         params1 @param @return @return WebElement return type @throws
	 */

	public WebElement elementFindByPartialLinkText(String params1) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.partialLinkText(params1));

		return findElements;
	}

	/**
	 * @Title: findElementContainText @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         params1 @param @return @return WebElement return type @throws
	 */

	public WebElement elementFindByText(String text) {
		// throw new PendingException();
		WebElement findElements = driver.findElement(By.xpath("//*[contains(text(), '" + text + "')]"));

		return findElements;
	}

	/**
	 * @Title: findElementWithButtonText @Description: TODO @author
	 *         Judy.Zhu@greendotcorp.com @param @param
	 *         params1 @param @return @return WebElement return type @throws
	 */

	public WebElement elementFindByButtonText(String text) {
		// throw new PendingException();
		WebElement findElement = driver.findElement(By.xpath("//button[contains(text(),'" + text + "')]"));

		return findElement;
	}

	/**
	 * @Title: findElementWithTextboxPlaceholder @Description: TODO @author
	 *         Judy.Zhu@greendotcorp.com @param @param
	 *         params1 @param @return @return WebElement return type @throws
	 */

	public WebElement elementFindByInputboxPlaceholder(String text) {
		WebElement textbox = elementFindByXPath("//input[@placeholder=\"" + text + "\"]");

		return textbox;
	}

	/**
	 * @Title: visitPage @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param params1 @return void return
	 *         type @throws
	 */

	public void pageVisit(String params1) {
		// driver = SeleniumCore.driver;
		driver.get(params1);
	}

	/**
	 * @Title: waitForPageFullsync @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return void return type @throws
	 */

	public void pageWaitFullSync() {
		// log.info("waitForPageFullsync: Current browser state
		// is:"+currentbowserstate);
		// wait for jQuery to load
		ExpectedCondition<Boolean> jqueryload = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				// TODO Auto-generated method stub
				Long newpagestate = (Long) javascript("return jQuery.active;");
				log.debug("the new page state is:" + newpagestate);
				return (newpagestate == 0);
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsload = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				// TODO Auto-generated method stub
				String newpagestate = (String) javascript("return document.readyState;");
				log.debug("the new page state is:" + newpagestate);
				return (newpagestate.equals("complete"));
			}
		};
		try {
			WebDriverWait waitforElement = AbstractWebDriverWait();
			boolean loaded = waitforElement.until(jqueryload) && waitforElement.until(jsload);
			log.debug("finally the load is loading with completed status is:" + loaded);
		} catch (org.openqa.selenium.WebDriverException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @Title: waitForSeconds @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param s @return void return
	 *         type @throws
	 */

	public static void pageWait(int timeoutInSeconds) {
		// throw new PendingException();
		// int seconds=Integer.parseInt(s);
		try {
			Thread.sleep(timeoutInSeconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("Sleep current step met an error:" + e.getMessage());
		}
	}

	/**
	 * @Title: pageTitleIs @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param params1 @return void return
	 *         type @throws
	 */

	public void pageTitleIs(String params1) {
		// throw new PendingException();
		String title = driver.getTitle();
		Assert.assertEquals(params1, title);
	}

	/**
	 * @Title: currentUrlIs @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param url @return void return
	 *         type @throws
	 */

	public void pageUrlIs(String url) {
		String ActualUrl = driver.getCurrentUrl();
		Assert.assertEquals(url, ActualUrl);
	}

	/**
	 * @Title: pageContains @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param params1 @return void return
	 *         type @throws
	 */

	public void pageSourceContains(String params1) {
		// throw new PendingException();
		boolean sourcecontent = driver.getPageSource().contains(params1);
		Assert.assertTrue(sourcecontent);
	}

	/**
	 * @Title: pageRefresh @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return void return type @throws
	 */

	public void pageRefresh() {
		driver.navigate().refresh();
	}

	/**
	 * @Title: pageRefresh @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return void return type @throws
	 */

	public void pageF5Refresh() {
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.F5).perform();
	}

	/**
	 * @Title: pageRefresh @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return void return type @throws
	 */

	public void pageBack() {
		driver.navigate().back();
	}

	/**
	 * @Title: pageRefresh @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return void return type @throws
	 */

	public void pageForward() {
		driver.navigate().forward();
	}

	/**
	 * @Title: pageElementsRelocatored @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param page @return void return
	 *         type @throws
	 */

	public void pageFactoryReload(Object page) {
		PageFactory.initElements(driver, page);
	}

	/**
	 * add the css style file content
	 * 
	 * @param css
	 *            css file content
	 * @return the return value
	 */
	public static Object PageCssStyleAdd(String css) {
		// logger.info("Run the javascript from page ,the java script is:"
		// + script);
		// highLight(e);
		String cssFunction = "var pageRecordHeader = document.getElementsByTagName('head')[0];\n"
				+ "var pageRecordStyle=document.createElement('style');\n"
				+ "pageRecordStyle.setAttribute('type', 'text/css');\n";
		String injectCSSAlreadyContent = "function supportCSSType(){" + cssFunction
				+ "if(pageRecordStyle.styleSheet){console.log('pageRecord:true');return true;}else{console.log('pageRecord:true');return false;}};\n"
				+ "return supportCSSType();";
		boolean hasCssStyleAlready = (boolean) BaseSteps.javascript(injectCSSAlreadyContent);
		if (hasCssStyleAlready) {
			cssFunction += "pageRecordStyle.styleSheet.cssText = '" + css + "';";
			// log.info(CSSFunctionInjectedObject.toString());
		} else {
			cssFunction += "pageRecordStyle.appendChild(document.createTextNode('" + css + "'));";
		}
		cssFunction += "\n pageRecordHeader.appendChild(pageRecordStyle);";

		return BaseSteps.javascript(cssFunction);

	}

	/**
	 * @Title: isChromeEmulation @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return @return boolean return
	 *         type @throws
	 */

	public boolean browserIsChromeEmulator() {
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
	 *         alterhu2020@gmail.com @param @return void return type @throws
	 */

	public void browserConsoleLog() {
		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
		for (LogEntry entry : logEntries) {
			log.info(Calendar.getInstance().getTime() + " " + entry.getLevel() + " " + entry.getMessage());
			// do something useful with the data
		}
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
	public static String browserTypeAndVersionIs(RemoteWebDriver driver) {
		Capabilities caps = driver.getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();
		// String platform=caps.getPlatform().toString();
		log.info("Get the current running browser is:" + browserName + ",the browser version is:" + browserVersion);
		return browserName + " " + browserVersion;
	}

	/**
	 * @Title: clearBrowserData @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return void return type @throws
	 */

	public void browserClearCookies() {
		driver.manage().deleteAllCookies();
		// driver.quit();
	}

	/**
	 * @Title: hideAlert @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return void return type @throws
	 */

	public void alertHide() {
		javascript("window.alert=function(msg){window.message=msg};");
	}

	/**
	 * @Title: isAlertPresent @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return @return boolean return
	 *         type @throws
	 */

	public boolean alertIsPresent() {
		boolean presentFlag = false;
		try {
			Alert alert = AbstractWebDriverWait().until(ExpectedConditions.alertIsPresent());
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
	 * @Title: getAlertMessage @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return @return String return
	 *         type @throws
	 */

	public String alertMessageIs() {
		Object objmsg = javascript("return window.message;");
		return objmsg.toString();
	}

	/**
	 * @Title: switchToIframe @Description: TODO @author
	 *         Judy.Zhu@greendotcorp.com @param @return void return type @throws
	 */

	public void iframeSwitchTo(int iframeSequence) {
		driver.switchTo().frame(driver.findElements(By.tagName("iframe")).get(iframeSequence));
	}

	/**
	 * @Title: clickOKButtonInAlertDialog @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return void return type @throws
	 */

	public void dialogClickAlertOKButton() {
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
	 *         alterhu2020@gmail.com @param @return void return type @throws
	 */

	public void dialogClickAlertCancelButton() {
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
	 * @Title: switchToWindowWithTitle @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param windowTitle @return void
	 *         return type @throws
	 */

	public void windowSwitchToTitle(String windowTitle) {
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
	 * @return the WebDriverWait element.
	 */
	protected WebDriverWait AbstractWebDriverWait() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, SeleniumCore.DEFAULT_WEBELEMENT_LOADING_TIME);
		return webDriverWait;
	}

}
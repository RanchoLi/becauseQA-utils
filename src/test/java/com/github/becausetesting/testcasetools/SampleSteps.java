package com.github.becausetesting.testcasetools;

import cucumber.api.java.en.And;

import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

/**
 * @ClassName: SampleSteps
 * @Description: TODO
 * @author: TODO
 * @date: 2016-04-19 23:45:30 PM
 * 
 */

public class SampleSteps {

	public SampleSteps() {
		// if current impletmented page is ajax page ,you need a make it as a
		// timeout page
		// AjaxElementLocatorFactory factory=new
		// AjaxElementLocatorFactory(driver, 1000);
		// PageFactory.initElements(factory,this);
		// if current impletmented is for mobile device app or browser like
		// android or iphone device .etc
		// AppiumFieldDecorator mobiledecorator=new AppiumFieldDecorator(driver,
		// new TimeOutDuration(500, TimeUnit.SECONDS))
		// PageFactory.initElements(mobiledecorator, this);
	}

	/**
	 * @MethodName: do_it_now
	 * @Description: TODO
	 * @author: TODO
	 * @date: 2016-04-19 23:45:19 PM
	 * 
	 */
	@And("^do it now$")
	public void do_it_now() {
		// Write code here that turns the phrase above into concrete actions
		Assert.assertEquals(1, 1);
	}

}

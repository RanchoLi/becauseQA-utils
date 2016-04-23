package com.github.becausetesting.cucumber;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.github.becausetesting.reflections.RefelectionUtils;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Scenario;
/*
 * 
 * this interface is used for user to do some customization behavors in cucumber running
 * 
 * you can use these variables:
 *  public static AtomicInteger failedStepsCount;
	public static AtomicInteger skippedStepsCount;
	public static AtomicInteger undefinedStepsCount;
	public static AtomicInteger passStepsCount;

	public static AtomicInteger failedScenariosCount;
	public static AtomicInteger skippedScenariosCount;
	public static AtomicInteger undefinedScenariosCount;
	public static AtomicInteger passScenariosCount;

	private static String cucumberErrorMessage = "";
	
 */
/**
 * ClassName: BecauseCucumberHook  
 * Function: TODO ADD FUNCTION.  
 * Reason: TODO ADD REASON 
 * date: Apr 23, 2016 6:11:20 PM  
 * @author alterhu2020@gmail.com
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface BecauseCucumberHook {

	public static Logger logger=Logger.getLogger(BecauseCucumberHook.class);
	
	/**
	 * beforeRun: like create test plan 
	 * @author alterhu2020@gmail.com
	 * @since JDK 1.8
	 */
	public void beforeRun(); 
	/**
	 * afterRun: like close test plan 
	 * @author alterhu2020@gmail.com
	 * @since JDK 1.8
	 */
	public void afterRun(); 
	
	
	/**
	 * beforeEachScenario:before start the cucumber scenario
	 * @author alterhu2020@gmail.com
	 * @param scenario
	 * @since JDK 1.8
	 */
	public void beforeEachScenario(Scenario scenario);  //start the selenium 
	/**
	 * afterEachScenario: after finished the cucumber scenario
	 * @author alterhu2020@gmail.com
	 * @param scenario
	 * @since JDK 1.8
	 */
	public void afterEachScenario(Scenario scenario); // upload the result into test tool
	
	
	/**
	 * beforeEachFeature: before each cucumber feature is running
	 * @author alterhu2020@gmail.com
	 * @param feature
	 * @since JDK 1.8
	 */
	public void beforeEachFeature(Feature feature); // upload the tese cases into test run
	
	/**
	 * setCucumberPropertyFilePath: set the path
	 * @author alterhu2020@gmail.com
	 * @return set the cucumber files
	 * @since JDK 1.8
	 */
	public String setCucumberPropertyFilePath(); // set the feature file path	

	/**
	 * setSeleniumDriver: if user selenium driver or not
	 * @author alterhu2020@gmail.com
	 * @return the selenium driver {@link WebDriver}
	 * @since JDK 1.8
	 */
	public WebDriver setSeleniumDriver(); // if needs to use the selenium integration for testing
	
}

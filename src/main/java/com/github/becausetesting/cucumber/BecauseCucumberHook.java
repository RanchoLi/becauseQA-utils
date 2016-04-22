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
public interface BecauseCucumberHook {

	public static Logger logger=Logger.getLogger(BecauseCucumberHook.class);
	
	public void beforeRun(); //like create test plan 
	public void afterRun(); //like close test plan 
	
	
	public void beforeEachScenario(Scenario scenario);  //start the selenium 
	public void afterEachScenario(Scenario scenario); // upload the result into test tool
	
	
	public void beforeEachFeature(Feature feature); // upload the tese cases into test run
	
	public String setCucumberPropertyFilePath(); // set the feature file path	

	public WebDriver setSeleniumDriver(); // if needs to use the selenium integration for testing
	
}

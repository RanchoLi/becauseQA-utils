package com.github.becauseQA.cucumber;

import java.util.List;

import org.apache.log4j.Logger;

import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Result;
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

	public static Logger log=Logger.getLogger(BecauseCucumberHook.class);
	
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
	 * @param scenario the parameter for cucumber scenario.
	 * @since JDK 1.8
	 */
	public void beforeEachScenario(Scenario scenario);  //start the selenium 
	/**
	 * afterEachScenario: after finished the cucumber scenario
	 * @author alterhu2020@gmail.com
	 * @param scenario the parameter for cucumber scenario.
	 * @since JDK 1.8
	 */
	public void afterEachScenario(Scenario scenario,Result result); // upload the result into test tool
	
	
	/**
	 * beforeEachFeature: before each cucumber feature is running
	 * @author alterhu2020@gmail.com
	 * @param feature the feature for cucumber feature.
	 * @since JDK 1.8
	 */
	public void beforeEachFeature(Feature feature); // upload the tese cases into test run
	
	/**
	 * setCucumberReportFormatters use to set different format
	 * e.g :
	 *  html:target/cucumber/cucumber-html-report
	 *  json:target/cucumber/cucumber-json-report/cucumber.json
	 *  junit:target/cucumber/cucumber-junit-report/cucumber.xml
	 *  testng:target/cucumber/cucumber-testng-report/cucumber.xml
	 *  rerun:target/cucumber/cucumber-failed-report/failed,rerun.txt
	 *  pretty
	 * 
	 * @author alterhu2020@gmail.com
	 * @return
	 * @since JDK 1.8
	 */
	public List<String> setCucumberReportFormatters();
	
	/**
	 * setCucumberStepDefinitionPaths to use to set cucumber step definition path:
	 * we had set these paths as default path to search the step definition,
	 * <p>1. classpath:</p>
	 * <p>2. com.github.becausetesting.cucumber.selenium</p>
	 * so actually you no need to set the step definition path here ,just leave it as empty.
	 * @author alterhu2020@gmail.com
	 * @return
	 * @since JDK 1.8
	 * @deprecated
	 */
	@Deprecated
	public List<String> setCucumberStepDefinitionPaths();
	/**
	 * setCucumberPropertyFilePath: set the path
	 * @author alterhu2020@gmail.com
	 * @return set the cucumber files
	 * @since JDK 1.8
	 */
	public List<String> setCucumberFeatureFilePaths(); // set the feature file path	
	
	/**
	 * Set the cucumber tags you need to run in feature file, 
	 * tags is just another parameter in the cucumber options annotation.
	 *  We can also pass multiple tags as values separated by commas if we need so.
	 * @return the cucumber tags you specified.
	 */
	public String setCucumberTags();

	
	
}

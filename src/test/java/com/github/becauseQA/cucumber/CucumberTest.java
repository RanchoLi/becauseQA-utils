package com.github.becauseQA.cucumber;

import java.util.List;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;


@RunWith(BecauseCucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber/cucumber-html-report"})
public class CucumberTest implements BecauseCucumberHook{

	@Override
	public void beforeRun() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterRun() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeEachScenario(Scenario scenario) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void beforeEachFeature(Feature feature) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> setCucumberFeatureFilePaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setCucumberTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> setCucumberReportFormatters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> setCucumberStepDefinitionPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void afterEachScenario(Scenario scenario, Result result) {
		// TODO Auto-generated method stub
		
	}

	
}

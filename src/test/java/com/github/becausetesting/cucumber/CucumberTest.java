package com.github.becausetesting.cucumber;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Scenario;


@RunWith(BecauseCucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumberhtml"},monochrome=true)
public class CucumberTest implements BecauseCucumberHook{

	@Override
	public void beforeRun() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeEachScenario(Scenario scenario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterEachScenario(Scenario scenario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeEachFeature(Feature feature) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setCucumberPropertyFilePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebDriver setSeleniumDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterRun() {
		// TODO Auto-generated method stub
		
	}

		
	
}
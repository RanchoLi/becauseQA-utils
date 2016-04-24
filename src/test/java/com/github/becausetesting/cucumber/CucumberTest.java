package com.github.becausetesting.cucumber;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

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
public class CucumberTest{
	
}

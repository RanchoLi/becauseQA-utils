# Introduction

 This solution had implemented cucumber-jvm feature,you can use cucumber-jvm very flexiable than official library.
 
# Getting started

1.  In order to use cucumber and selenium feature. First, configure it in maven setting:
	
		<dependency>
			<groupId>com.github.becauseQA</groupId>
			<artifactId>becauseQA-utils</artifactId>
			<version>1.0.3</version>
		</dependency>
	
2.  Create a Java class called `CucumberRunner`  which will implemented `BecauseCucumberHook` interface,with the Runwith as `BecauseCucumber.class`:


     import java.util.List;
	import org.junit.runner.RunWith;	
	import com.github.becauseQA.cucumber.BecauseCucumber;
	import com.github.becauseQA.cucumber.BecauseCucumberHook;	
	import gherkin.formatter.model.Feature;
	import gherkin.formatter.model.Result;
	import gherkin.formatter.model.Scenario;
	
	@RunWith(BecauseCucumber.class)
    public class CucumberRunner implements BecauseCucumberHook {

	@Override
	public void beforeRun() {
		// TODO Auto-generated method stub
		System.out
				.println("This is the whole  test execution begin method .put any code you want to check or use here");
	}

	@Override
	public void afterRun() {
		// TODO Auto-generated method stub
		System.out
				.println("This is the whole test execution finished method.put any code you want to check or use here");

	}

	@Override
	public void beforeEachScenario(Scenario scenario) {
		// TODO Auto-generated method stub
		String content = "scenaro name:" + scenario.getName() + ",scenario description: " + scenario.getDescription();
		System.out.println("This is the method called before each cucumber scenario is called... " + content);

	}

	@Override
	public void afterEachScenario(Scenario scenario, Result result) {
		// TODO Auto-generated method stub
		System.out.println("This is the method after each cucumber scenario finished execution.");

	}

	@Override
	public void beforeEachFeature(Feature feature) {
		// TODO Auto-generated method stub
		System.out.println(
				"This is the method before each cucumber feature file is run,put any code you want to check or use here.");

	}

	@Override
	public List<String> setCucumberReportFormatters() {
		// TODO Auto-generated method stub
		System.out.println("This method will configured the cucumber report by default leave it empty.");
		return null;
	}

	@Override
	public List<String> setCucumberStepDefinitionPaths() {
		// TODO Auto-generated method stub
		System.out.println(
				"This method configured the step definition file for  cucumber ,by default we use the project classpath ,just leave it empty .no need to configure. ");
		return null;
	}

	@Override
	public List<String> setCucumberFeatureFilePaths() {
		// TODO Auto-generated method stub
		System.out.println("This method need to configure which cucumber feature file you need to run");
		return null;
	}

	@Override
	public String setCucumberTags() {
		// TODO Auto-generated method stub
		System.out.println(
				"This is the cucumber tags you want to run cucumber feature file ,you can covert the array or list as string");
		return null;
	}
    }
	
   for each method usage please have a look in this page
   
   
# Building

Checkout this repository and execute the maven installer from the base directory with the pom.xml file in it.


	mvn clean install
	
Once it is installed you can add it to your projects as a maven dependency.

# Maven Dependency

	<dependency>
			<groupId>com.github.becauseQA</groupId>
			<artifactId>becauseQA-utils</artifactId>
			<version>1.0.3</version>
    </dependency>

# Pull Requests

Please read CONTRIBUTING.md before submitting your pull requests.

# Useful Resources

Please check out the sample project to quick understand the project setup using cucumber-selenium feature.

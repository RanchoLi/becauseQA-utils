package com.github.becausetesting.cucumber;

import cucumber.api.PendingException;
import cucumber.runtime.CucumberException;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;
import gherkin.formatter.model.Tag;

import org.apache.log4j.Logger;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.becausetesting.reflections.RefelectionUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static cucumber.runtime.Runtime.isPending;

public class BecauseCucumberReporter implements Reporter, Formatter {
	private final List<Step> steps = new ArrayList<Step>();

	private final Reporter reporter;
	private final Formatter formatter;
	private final boolean strict;

	EachTestNotifier stepNotifier;
	private ExecutionUnitRunner executionUnitRunner;
	private RunNotifier runNotifier;
	EachTestNotifier executionUnitNotifier;
	private boolean ignoredStep;
	private boolean inScenarioLifeCycle;

	
	private byte[] screenshotAs = new byte[1024];
	/*
	 * static { try { driver = new RemoteWebDriver(new
	 * URL("http://127.0.0.1:4444/wd/hub"), DesiredCapabilities.firefox()); }
	 * catch (MalformedURLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */
	private static Logger logger = Logger.getLogger(BecauseCucumberReporter.class);
	private static Result result;
	
	public static AtomicInteger failedStepsCount;
	public static AtomicInteger skippedStepsCount;
	public static AtomicInteger undefinedStepsCount;
	public static AtomicInteger passStepsCount;

	public static AtomicInteger failedScenariosCount;
	public static AtomicInteger skippedScenariosCount;
	public static AtomicInteger undefinedScenariosCount;
	public static AtomicInteger passScenariosCount;

	private static String cucumberErrorMessage = "";

	public BecauseCucumberReporter(Reporter reporter, Formatter formatter, boolean strict) {
		this.reporter = reporter;
		this.formatter = formatter;
		this.strict = strict;

		// define the step and scenarios counters
		failedStepsCount = new AtomicInteger(0);
		skippedStepsCount = new AtomicInteger(0);
		undefinedStepsCount = new AtomicInteger(0);
		passStepsCount = new AtomicInteger(0);

		failedScenariosCount = new AtomicInteger(0);
		skippedScenariosCount = new AtomicInteger(0);
		undefinedScenariosCount = new AtomicInteger(0);
		passScenariosCount = new AtomicInteger(0);

		

	}

	/*****************************************************************************/

	public void startExecutionUnit(ExecutionUnitRunner executionUnitRunner, RunNotifier runNotifier) {
		this.executionUnitRunner = executionUnitRunner;
		this.runNotifier = runNotifier;
		this.stepNotifier = null;
		this.ignoredStep = false;

		executionUnitNotifier = new EachTestNotifier(runNotifier, executionUnitRunner.getDescription());
		executionUnitNotifier.fireTestStarted();

	}

	public void finishExecutionUnit() {
		if (ignoredStep) {
			executionUnitNotifier.fireTestIgnored();
		}
		executionUnitNotifier.fireTestFinished();
	}

	public void match(Match match) {
		Step runnerStep = fetchAndCheckRunnerStep();
		Description description = executionUnitRunner.describeChild(runnerStep);
		stepNotifier = new EachTestNotifier(runNotifier, description);
		reporter.match(match);
	}

	private Step fetchAndCheckRunnerStep() {
		Step scenarioStep = steps.remove(0);
		Step runnerStep = executionUnitRunner.getRunnerSteps().remove(0);
		if (!scenarioStep.getName().equals(runnerStep.getName())) {
			throw new CucumberException(
					"Expected step: \"" + scenarioStep.getName() + "\" got step: \"" + runnerStep.getName() + "\"");
		}
		return runnerStep;
	}

	@Override
	public void embedding(String mimeType, byte[] data) {
		reporter.embedding(mimeType, data);
	}

	@Override
	public void write(String text) {
		reporter.write(text);
	}

	public void result(Result result) {
		BecauseCucumberReporter.result = result;

		Throwable error = result.getError();
		if (Result.SKIPPED == result) {
			stepNotifier.fireTestIgnored();

			skippedStepsCount.incrementAndGet();
			// this.embedding("image/png", screenshotAs);
		} else if (isPendingOrUndefined(result)) {
			addFailureOrIgnoreStep(result);

			undefinedStepsCount.incrementAndGet();
			// this.embedding("image/png", screenshotAs);
		} else {
			if (stepNotifier != null) {
				// Should only fireTestStarted if not ignored
				stepNotifier.fireTestStarted();
				if (error != null) {
					stepNotifier.addFailure(error);

					failedStepsCount.incrementAndGet();
					// this.embedding("image/png", screenshotAs);

				} else {
					
					runNotifier.fireTestFinished(Description.createTestDescription(result.getClass(), result.getStatus()));
					passStepsCount.incrementAndGet();
					//runNotifier.fireTestRunFinished(new org.junit.runner.Result());
				}
				stepNotifier.fireTestFinished();
			}
			if (error != null) {
				executionUnitNotifier.addFailure(error);
			}
		}
		if (steps.isEmpty()) {
			// We have run all of our steps. Set the stepNotifier to null so
			// that
			// if an error occurs in an After block, it's reported against the
			// scenario
			// instead (via executionUnitNotifier).
			stepNotifier = null;
		}
		reporter.result(result);
	}

	private boolean isPendingOrUndefined(Result result) {
		Throwable error = result.getError();
		return Result.UNDEFINED == result || isPending(error);
	}

	private void addFailureOrIgnoreStep(Result result) {
		if (strict) {
			stepNotifier.fireTestStarted();
			addFailure(result);
			stepNotifier.fireTestFinished();
		} else {
			ignoredStep = true;
			stepNotifier.fireTestIgnored();
		}
	}

	private void addFailure(Result result) {

		Throwable error = result.getError();
		if (error == null) {
			error = new PendingException();
		}
		stepNotifier.addFailure(error);
		executionUnitNotifier.addFailure(error);
	}

	@Override
	public void before(Match match, Result result) {
		// logger.info(this.getClass().getName()+",Before scenrios
		// "+result.getStatus());
		handleHook(result);
		reporter.before(match, result);
	}

	@Override
	public void after(Match match, Result result) {
		// logger.info(this.getClass().getName()+",after scenarios
		// "+result.getStatus());
		reporter.after(match, result);
		handleHook(result);
	}

	private void handleHook(Result result) {
		if (result.getStatus().equals(Result.FAILED)) {
			executionUnitNotifier.addFailure(result.getError());
		}
	}

	@Override
	public void uri(String uri) {
		formatter.uri(uri);
	}

	@Override
	public void feature(gherkin.formatter.model.Feature feature) {
		// beforeEachFeature(feature);
		Object reportInstance = BecauseCucumber.reportInstance;
		Class<?> reportClass = BecauseCucumber.reportClass;
		if (reportInstance != null) {
			RefelectionUtils.invokeMethod(reportInstance, reportClass, BecauseCucumber.METHOD_BEFOREFEATURE,
					new Class[] { Feature.class }, feature);
		}
		formatter.feature(feature);
	}

	@Override
	public void background(Background background) {
		formatter.background(background);
	}

	@Override
	public void scenario(Scenario scenario) {
		formatter.scenario(scenario);
	}

	@Override
	public void scenarioOutline(ScenarioOutline scenarioOutline) {
		formatter.scenarioOutline(scenarioOutline);
	}

	@Override
	public void examples(Examples examples) {
		formatter.examples(examples);
	}

	@Override
	public void step(Step step) {
		formatter.step(step);
		if (inScenarioLifeCycle) {
			steps.add(step);
		}
	}

	@Override
	public void eof() {
		formatter.eof();
	}

	@Override
	public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {
		formatter.syntaxError(state, event, legalEvents, uri, line);
	}

	@Override
	public void done() {
		formatter.done();
		steps.clear();
	}

	@Override
	public void close() {
		formatter.close();
	}

	@Override
	public void startOfScenarioLifeCycle(Scenario scenario) {
		inScenarioLifeCycle = true;
		formatter.startOfScenarioLifeCycle(scenario);

		Object reportInstance = BecauseCucumber.reportInstance;
		Class<?> reportClass = BecauseCucumber.reportClass;
		if (reportInstance != null) {
			RefelectionUtils.invokeMethod(reportInstance, reportClass, BecauseCucumber.METHOD_BEFORESCENARIO,
					new Class[] { Scenario.class }, scenario);
		}
	}

	@Override
	public void endOfScenarioLifeCycle(Scenario scenario) {
		formatter.endOfScenarioLifeCycle(scenario);
		inScenarioLifeCycle = false;
		// upload the cucumber steps result into test case tool
		
		cucumberErrorMessage = result.getErrorMessage();
		Throwable error = result.getError();

		if (Result.SKIPPED == result) {
			skippedScenariosCount.incrementAndGet();
			//this.embedding("image/png", screenshotAs);
		} else if (isPendingOrUndefined(result)) {
			undefinedScenariosCount.incrementAndGet();
			//this.embedding("image/png", screenshotAs);
		} else {
			if (error != null) {
				failedScenariosCount.incrementAndGet();
				
				WebDriver driver = BecauseCucumber.driver;	
				if(driver!=null){
					RemoteWebDriver remoteWebDriver = (RemoteWebDriver) driver;
					String currentUrl = remoteWebDriver.getCurrentUrl();
					Capabilities capabilities = remoteWebDriver.getCapabilities();
					String platform="Platform: "+capabilities.getBrowserName()+","+capabilities.getPlatform().toString();
					screenshotAs = remoteWebDriver.getScreenshotAs(OutputType.BYTES);
//					cucumber.api.Scenario scenario2=(cucumber.api.Scenario) scenario;	
					this.write("Scenario failed,Page Url: "+currentUrl+","+platform+",at "+LocalDateTime.now());
					this.embedding("image/png",screenshotAs);
				
				}
				

			} else {
				passScenariosCount.incrementAndGet();
			}

		}

		String starthorizonline = "--------------------------------------------\n";
		String passedMessage = "Total Passed Steps: " + passStepsCount + "\n";
		String failedMessage = "Total Failed Steps: " + failedStepsCount + "\n";
		String skippedMessage = "Total Skipped Steps: " + skippedStepsCount + "\n";
		String undefinedMessage = "Total Undefined Steps: " + undefinedStepsCount + "\n";
		String passedScenarioMessage = "Total Passed Scenarios: " + passScenariosCount + "\n";
		String failedScenarioMessage = "Total Failed Scenarios: " + failedScenariosCount + "\n";
		String skippedScenarioMessage = "Total Skipped Scenarios: " + skippedScenariosCount + "\n";
		String undefinedScenarioMessage = "Total Undefined Scenarios: " + undefinedScenariosCount + "\n";
		String endhorizonline = "--------------------------------------------\n";

		logger.info("[" + this.getClass().getName() + "]\nFinish Scenario: " + scenario.getName() + ", Status: "
				+ result.getStatus() + "");
		logger.info(starthorizonline + passedMessage + failedMessage + skippedMessage + undefinedMessage
				+ passedScenarioMessage + failedScenarioMessage + skippedScenarioMessage + undefinedScenarioMessage
				+ endhorizonline);

		Object reportInstance = BecauseCucumber.reportInstance;
		Class<?> reportClass = BecauseCucumber.reportClass;
		if (reportInstance != null) {
			RefelectionUtils.invokeMethod(reportInstance, reportClass, BecauseCucumber.METHOD_AFTERSCENARIO,
					new Class[] { Scenario.class }, scenario);
		}

	}




}

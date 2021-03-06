package com.github.becauseQA.cucumber;

import static cucumber.runtime.Runtime.isPending;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.becauseQA.cucumber.selenium.BaseSteps;
import com.github.becauseQA.cucumber.selenium.PageRecorder;
import com.github.becauseQA.cucumber.selenium.SeleniumCore;
import com.github.becauseQA.reflections.RefelectionUtils;

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

public class BecauseCucumberReporter implements Reporter, Formatter {
	private final List<Step> steps = new ArrayList<Step>();

	private final Reporter reporter;
	private final Formatter formatter;
	private final boolean strict;

	EachTestNotifier stepNotifier;
	private ExecutionUnitRunner executionUnitRunner;
	private RunNotifier runNotifier;
	EachTestNotifier executionUnitNotifier;
	private boolean failedStep;
	private boolean ignoredStep;
	private boolean inScenarioLifeCycle;
	private Description description;

	private String cucumberStepTitle = "Automation Step Execution Recorder";
	private String cucumberScenarioTitle = "Automation Scenario Execution Recorder";

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

	private byte[] screenshotAs = new byte[1024];
	// public static Object reportInstance;

	private void myBecauseCucumberReporter() {

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

	private void becauseCucumberFeature(Feature feature) {
		Object reportInstance = BecauseCucumber.reportInstance;
		if (reportInstance != null) {
			RefelectionUtils.getMethod(reportInstance, BecauseCucumber.METHOD_BEFOREFEATURE, feature);
		}
	}

	/*
	 * private void becauseCucumberBeforeStep(Step step) { // Object
	 * reportInstance = BecauseCucumber.reportInstance; String CucumberStep =
	 * step.getKeyword() + " " + step.getName(); //
	 * PageRecorder.showMessage(CucumberStep, null); Object reportInstance =
	 * BecauseCucumber.reportInstance; if (reportInstance != null) {
	 * RefelectionUtils.getMethod(reportInstance,
	 * BecauseCucumber.METHOD_BEFORESTEP, step); } }
	 * 
	 * private void becauseCucumberAfterStep(Step step, Result result) { //
	 * Object reportInstance = BecauseCucumber.reportInstance; Object
	 * reportInstance = BecauseCucumber.reportInstance; if (reportInstance !=
	 * null) { RefelectionUtils.getMethod(reportInstance,
	 * BecauseCucumber.METHOD_AFTERSTEP, step, result); } }
	 */
	private void becauseCucumberStartOfScenarioLifeCycle(Scenario scenario) {
		String message = scenario.getKeyword() + " Name: " + scenario.getName() + ",Description: " + scenario.getLine();
		if (BaseSteps.driver != null) {
			PageRecorder.showMessage(message, "info", cucumberScenarioTitle);
		}
		Object reportInstance = BecauseCucumber.reportInstance;
		if (reportInstance != null) {
			RefelectionUtils.getMethod(reportInstance, BecauseCucumber.METHOD_BEFORESCENARIO, scenario);
		}
	}

	private void becauseCucumberEndOfScenarioLifeCycle(Scenario scenario) {
		result.getErrorMessage();
		Throwable error = result.getError();

		if (Result.SKIPPED == result) {
			skippedScenariosCount.incrementAndGet();
			// this.embedding("image/png", screenshotAs);
		} else if (isPendingOrUndefined(result)) {
			undefinedScenariosCount.incrementAndGet();
			// this.embedding("image/png", screenshotAs);
		} else {
			if (error != null) {
				failedScenariosCount.incrementAndGet();

				WebDriver driver = SeleniumCore.driver;
				if (driver != null) {
					RemoteWebDriver remoteWebDriver = (RemoteWebDriver) driver;
					String currentUrl = remoteWebDriver.getCurrentUrl();
					Capabilities capabilities = remoteWebDriver.getCapabilities();
					String platform = "Platform: " + capabilities.getBrowserName() + ","
							+ capabilities.getPlatform().name();
					screenshotAs = remoteWebDriver.getScreenshotAs(OutputType.BYTES);
					// cucumber.api.Scenario scenario2=(cucumber.api.Scenario)
					// scenario;
					this.write(
							"Scenario failed,Page Url: " + currentUrl + "," + platform + ",at " + LocalDateTime.now());
					this.embedding("image/png", screenshotAs);

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
		//String endhorizonline = "--------------------------------------------\n";

		logger.info("Finish Scenario: " + scenario.getName() + ", Status: "
				+ result.getStatus() + "");
		logger.info(starthorizonline + passedMessage + failedMessage + skippedMessage + undefinedMessage
				+ passedScenarioMessage + failedScenarioMessage + skippedScenarioMessage + undefinedScenarioMessage
				);
		logger.info(starthorizonline);

		Object reportInstance = BecauseCucumber.reportInstance;
		if (reportInstance != null) {
			RefelectionUtils.getMethod(reportInstance, BecauseCucumber.METHOD_AFTERSCENARIO, scenario, result);
		}
	}

	public BecauseCucumberReporter(Reporter reporter, Formatter formatter, boolean strict) {
		this.reporter = reporter;
		this.formatter = formatter;
		this.strict = strict;
		myBecauseCucumberReporter();
	}

	public void startExecutionUnit(ExecutionUnitRunner executionUnitRunner, RunNotifier runNotifier) {
		this.executionUnitRunner = executionUnitRunner;
		this.runNotifier = runNotifier;
		this.stepNotifier = null;
		this.failedStep = false;
		this.ignoredStep = false;

		executionUnitNotifier = new EachTestNotifier(runNotifier, executionUnitRunner.getDescription());
		executionUnitNotifier.fireTestStarted();
		// PageRecorder.showMessage(executionUnitRunner.getDescription().getDisplayName(),
		// "info");
	}

	public void finishExecutionUnit() {
		if (ignoredStep && !failedStep) {
			executionUnitNotifier.fireTestIgnored();
		}
		executionUnitNotifier.fireTestFinished();
	}

	@Override
	public void match(Match match) {
		Step runnerStep = fetchAndCheckRunnerStep();
		description = executionUnitRunner.describeChild(runnerStep);
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

	@Override
	public void result(Result result) {
		// fix this issue only for failed or passed steps to log the global
		// result
		/*
		 * int size = steps.size(); String cucumberStep =
		 * "Last Step Execution Result"; if (size > 0) { Step step =
		 * steps.get(size - 1); cucumberStep = step.getKeyword() + " " +
		 * step.getName() + " (lineNumber:" + step.getLine() + ")"; }
		 */

		String resultStatus = result.getStatus();
		if (Result.FAILED == resultStatus || Result.PASSED == resultStatus) {
			BecauseCucumberReporter.result = result;
		}
		Throwable error = result.getError();
		String popupMesageType = "info";
		if (Result.SKIPPED == result) {
			skippedStepsCount.incrementAndGet();
			stepNotifier.fireTestIgnored();
			popupMesageType="warning";			
		} else if (isPendingOrUndefined(result)) {
			undefinedStepsCount.incrementAndGet();
			addFailureOrIgnoreStep(result);
			popupMesageType="warning";
		} else {
			if (stepNotifier != null) {
				// Should only fireTestStarted if not ignored
				stepNotifier.fireTestStarted();
				if (error != null) {
					failedStepsCount.incrementAndGet();
					stepNotifier.addFailure(error);
				} else {
					passStepsCount.incrementAndGet();
					popupMesageType="success";
				}
				stepNotifier.fireTestFinished();
			}
			if (error != null) {
				failedStep = true;
				popupMesageType="error";
				executionUnitNotifier.addFailure(error);
			}
		}
		if (BaseSteps.driver != null) {
			PageRecorder.showMessage(description.getDisplayName() + "\n\n" + (error != null ? error.getMessage() : ""),
					popupMesageType, "[" + result.getStatus().toUpperCase() + "] " + cucumberStepTitle);
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
		failedStep = true;
		stepNotifier.addFailure(error);
		executionUnitNotifier.addFailure(error);
	}

	@Override
	public void before(Match match, Result result) {
		handleHook(result);
		reporter.before(match, result);
	}

	@Override
	public void after(Match match, Result result) {
		handleHook(result);
		reporter.after(match, result);
	}

	private void handleHook(Result result) {
		if (result.getStatus().equals(Result.FAILED) || (strict && isPending(result.getError()))) {
			executionUnitNotifier.addFailure(result.getError());
		} else if (isPending(result.getError())) {
			ignoredStep = true;
		}
	}

	@Override
	public void uri(String uri) {
		formatter.uri(uri);
	}

	@Override
	public void feature(gherkin.formatter.model.Feature feature) {
		becauseCucumberFeature(feature);
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
		if (inScenarioLifeCycle) {
			steps.add(step);

		}
		formatter.step(step);
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
	}

	@Override
	public void close() {
		formatter.close();
	}

	@Override
	public void startOfScenarioLifeCycle(Scenario scenario) {
		inScenarioLifeCycle = true;
		formatter.startOfScenarioLifeCycle(scenario);
		becauseCucumberStartOfScenarioLifeCycle(scenario);
	}

	@Override
	public void endOfScenarioLifeCycle(Scenario scenario) {
		formatter.endOfScenarioLifeCycle(scenario);
		inScenarioLifeCycle = false;
		becauseCucumberEndOfScenarioLifeCycle(scenario);
	}
}

package com.github.becausetesting.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.CucumberException;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.formatter.PluginFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.model.CucumberFeature;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;

import org.apache.log4j.Logger;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.becausetesting.reflections.RefelectionUtils;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Classes annotated with {@code @RunWith(Cucumber.class)} will run a Cucumber
 * Feature. The class should implement the BecauseCucumberHook interface, which
 * will let you to invoke the cucumber behaviours in runtime
 * </p>
 * <p>
 * Cucumber will look for a {@code .feature} file on the classpath, using the
 * same resource path as the annotated class ({@code .class} substituted by
 * {@code .feature}). also you can use the properties file to
 * </p>
 * Additional hints can be given to Cucumber by annotating the class with
 * {@link CucumberOptions}.
 *
 * @see CucumberOptions
 */
public class BecauseCucumber extends ParentRunner<FeatureRunner> {

	private static final Logger logger = Logger.getLogger(BecauseCucumber.class);
	private final List<FeatureRunner> children = new ArrayList<FeatureRunner>();

	private final Runtime runtime;

	private BecauseCucumberReporter becauseCucumberReporter;
	public Reporter reporter;
	public Formatter formatter;
	public boolean isStrict = false;

	public static Object reportInstance = null;

	public static String METHOD_BEFORERUN = "beforeRun";
	public static String METHOD_AFTERRUN = "afterRun";

	public static String METHOD_BEFORESCENARIO = "beforeEachScenario";
	public static String METHOD_AFTERSCENARIO = "afterEachScenario";
	public static String METHOD_BEFOREFEATURE = "beforeEachFeature";

	public static String METHOD_CUCUMBERFEATUREPATHS = "setCucumberFeatureFilePaths";
	public static String METHOD_SETREPORTFORMATS = "setCucumberReportFormatters";
	public static String METHOD_SETSTEPDEFINITIONS = "setCucumberStepDefinitionPaths";
	public static String METHOD_SETCUCUMBERTAGS = "setCucumberTags";

	public static String METHOD_SETSELENIUMDRIVER = "setSeleniumDriver";

	public static WebDriver driver;

	/**
	 * Constructor called by JUnit.
	 *
	 * @param clazz
	 *            the class with the @RunWith annotation.
	 * @throws java.io.IOException
	 *             if there is a problem
	 * @throws org.junit.runners.model.InitializationError
	 *             if there is another problem
	 */
	public BecauseCucumber(Class clazz) throws InitializationError, IOException {
		super(clazz);

		ClassLoader classLoader = clazz.getClassLoader();
		ResourceLoader resourceLoader = new MultiLoader(classLoader);

		Assertions.assertNoCucumberAnnotatedMethods(clazz);

		RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(clazz);
		RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();

		reporter = runtimeOptions.reporter(classLoader);
		formatter = runtimeOptions.formatter(classLoader);
		isStrict = runtimeOptions.isStrict();

		// report formats
		String[] formatArrays = new String[] { "html:target/cucumber/cucumber-html-report",
				"json:target/cucumber/cucumber-json-report/cucumber.json",
				"junit:target/cucumber/cucumber-junit-report/cucumber.xml",
				"testng:target/cucumber/cucumber-testng-report/cucumber.xml",
				"rerun:target/cucumber/cucumber-failed-report/failed,rerun.txt" };
		List<String> reportformatList = Arrays.asList(formatArrays);
		// get the step definition paths
		String[] stepArrays = new String[] { "classpath:", "com.github.becausetesting.cucumber.selenium" };
		List<String> stepdefinitionList = Arrays.asList(stepArrays);

		// the cucumber feature files
		List<CucumberFeature> cucumberFeatures;
		List<String> featurePaths = Lists.newArrayList();
		List<Object> filters = runtimeOptions.getFilters();
		String classPackagePath = clazz.getName();
		classPackagePath = classPackagePath.substring(0, Math.max(0, classPackagePath.lastIndexOf("."))).replace('.',
				'/');
		String classPathFeature = MultiLoader.CLASSPATH_SCHEME + classPackagePath;
		featurePaths.add(classPathFeature);
		// String string = System.getenv().get("cucumber.options");
		if (BecauseCucumberHook.class.isAssignableFrom(clazz)) {
			logger.info("implement the BecauseCucumberHook interface");
			reportInstance = RefelectionUtils.getContractorInstance(clazz, new Object[] {});

			RefelectionUtils.getMethod(reportInstance, METHOD_BEFORERUN, new Object[] {});
			// format the report
			Object formats = RefelectionUtils.getMethod(reportInstance, METHOD_SETREPORTFORMATS, new Object[] {});
			if (formats != null) {
				// reportformatList.clear();
				reportformatList = (List<String>) formats;
			}
			// step definition paths
			Object steps = RefelectionUtils.getMethod(reportInstance, METHOD_SETSTEPDEFINITIONS, new Object[] {});
			if (steps != null) {
				// stepdefinitionList.clear();
				stepdefinitionList = (List<String>) steps;
			}

			// feature path defined
			Object propertyFile = RefelectionUtils.getMethod(reportInstance, METHOD_CUCUMBERFEATUREPATHS,
					new Object[] {});
			if (propertyFile != null) {
				featurePaths.clear();
				List<String> features = (List<String>) propertyFile;
				featurePaths.addAll(features);
			}

			// feature scenario tags
			Object tagsObj = RefelectionUtils.getMethod(reportInstance, METHOD_SETCUCUMBERTAGS,
					new Object[] {});
			if (tagsObj != null) {
				List<String> tags = (List<String>) tagsObj;
				filters.addAll(tags);
			}

			logger.info("Begin to start the selenium driver...");
			Object tempdriver = RefelectionUtils.getMethod(reportInstance, BecauseCucumber.METHOD_SETSELENIUMDRIVER,
					new Object[] {});
			if (tempdriver != null) {
				driver = (WebDriver) tempdriver;
			}

		} else {
			logger.warn("Please implements BecauseCucumberHook interface in current Junit Class: " + clazz.getName());
		}

		// Add the format ,step definition files and feature files
		PluginFactory pluginFactory = new PluginFactory();
		for (String format : reportformatList) {
			Object formatObj = pluginFactory.create(format);
			runtimeOptions.addPlugin(formatObj);
		}
		runtimeOptions.getGlue().addAll(stepdefinitionList);
		cucumberFeatures = CucumberFeature.load(resourceLoader, featurePaths, filters);

		runtime = createRuntime(resourceLoader, classLoader, runtimeOptions);
		becauseCucumberReporter = new BecauseCucumberReporter(reporter, formatter, isStrict);
		addChildren(cucumberFeatures);
	}

	/**
	 * Create the Runtime. Can be overridden to customize the runtime or
	 * backend.
	 *
	 * @param resourceLoader
	 *            used to load resources
	 * @param classLoader
	 *            used to load classes
	 * @param runtimeOptions
	 *            configuration
	 * @return a new runtime
	 * @throws InitializationError
	 *             if a JUnit error occurred
	 * @throws IOException
	 *             if a class or resource could not be loaded
	 */
	protected Runtime createRuntime(ResourceLoader resourceLoader, ClassLoader classLoader,
			RuntimeOptions runtimeOptions) throws InitializationError, IOException {
		ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
		return new Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);
	}

	@Override
	public List<FeatureRunner> getChildren() {
		return children;
	}

	@Override
	protected Description describeChild(FeatureRunner child) {
		return child.getDescription();
	}

	@Override
	protected void runChild(FeatureRunner child, RunNotifier notifier) {
		child.run(notifier);
	}

	@Override
	public void run(RunNotifier notifier) {
		// notifier.addListener(new SanityChecker());
		super.run(notifier);
		becauseCucumberReporter.done();
		becauseCucumberReporter.close();
		runtime.printSummary();
		// after the execution
		if (reportInstance != null) {
			// load from the properties file for configuration
			Object invokeMethod = RefelectionUtils.getMethod(reportInstance, METHOD_CUCUMBERFEATUREPATHS,
					new Object[] {});
			if (invokeMethod != null) {
				// get the property file path
				String cucumberPath = invokeMethod.toString();

			}
			RefelectionUtils.getMethod(reportInstance, METHOD_AFTERRUN, new Object[] {});

		}

	}

	private void addChildren(List<CucumberFeature> cucumberFeatures) throws InitializationError {
		for (CucumberFeature cucumberFeature : cucumberFeatures) {
			children.add(new FeatureRunner(cucumberFeature, runtime, becauseCucumberReporter));
		}
	}

}

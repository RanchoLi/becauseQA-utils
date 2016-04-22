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

	// public static String reportClassName=null;
	public static Class<?> reportClass = null;
	public static Object reportInstance = null;

	public static String METHOD_BEFORERUN = "beforeRun";
	public static String METHOD_AFTERRUN = "afterRun";

	public static String METHOD_BEFORESCENARIO = "beforeEachScenario";
	public static String METHOD_AFTERSCENARIO = "afterEachScenario";
	public static String METHOD_BEFOREFEATURE = "beforeEachFeature";

	public static String METHOD_PROPERTYFILE = "setCucumberPropertyFilePath";

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
		
		
		//String string = System.getenv().get("cucumber.options");

		// format the report
		Object prettyformat = new PluginFactory().create("pretty");
		Object htmlformat = new PluginFactory().create("html:target/cucumber/cucumber-html-report");
		Object jsonformat = new PluginFactory().create("json:target/cucumber/cucumber-json-report/cucumber.json");
		Object junitformat = new PluginFactory().create("junit:target/cucumber/cucumber-junit-report/cucumber.xml");
		Object testngformat = new PluginFactory().create("testng:target/cucumber/cucumber-testng-report/cucumber.xml");

		Object rerunformat = new PluginFactory()
				.create("rerun:target/cucumber/cucumber-failed-report/failed,rerun.txt");

		runtimeOptions.addPlugin(prettyformat);
		runtimeOptions.addPlugin(htmlformat);
		runtimeOptions.addPlugin(jsonformat);
		runtimeOptions.addPlugin(junitformat);
		runtimeOptions.addPlugin(testngformat);
		runtimeOptions.addPlugin(rerunformat);

		
		// add our customize steps
		runtimeOptions.getGlue().add("classpath:"); //any place for the step definiation
		runtimeOptions.getGlue().add("com.github.becausetesting.cucumber.selenium");

		// feature path defined
		List<String> featurePaths = Lists.newArrayList();
		List<Object> filters = runtimeOptions.getFilters();
		List<CucumberFeature> cucumberFeatures;
		String classPackagePath = clazz.getName();
		classPackagePath=classPackagePath.substring(0, Math.max(0, classPackagePath.lastIndexOf("."))).replace('.', '/');
		String classPathFeature=MultiLoader.CLASSPATH_SCHEME+classPackagePath;
		featurePaths.add(classPathFeature);
		cucumberFeatures = CucumberFeature.load(resourceLoader, featurePaths, filters);
		//cucumberFeatures = runtimeOptions.cucumberFeatures(resourceLoader);
		// the below code for customization behaviours
		if (BecauseCucumberHook.class.isAssignableFrom(clazz)) {
			logger.info("implement the BecauseCucumberHook interface");
			reportClass = clazz;
			reportInstance = RefelectionUtils.createContractorInstance(clazz, null);

			RefelectionUtils.invokeMethod(reportInstance, reportClass, METHOD_BEFORERUN, new Class[] {},
					new Object[] {});

			Object propertyFile = RefelectionUtils.invokeMethod(reportInstance, reportClass, METHOD_PROPERTYFILE,
					new Class[] {}, new Object[] {});

			if (propertyFile != null) {
				featurePaths.add(propertyFile.toString());			
				cucumberFeatures = CucumberFeature.load(resourceLoader, featurePaths, filters);
			}

		} else {
			logger.warn("Please implements BecauseCucumberHook interface in current Junit Class: " + clazz.getName());
		}

		// if use the selenium driver
		logger.info("Begin to start the selenium driver...");
		Object tempdriver = RefelectionUtils.invokeMethod(reportInstance, reportClass,
				BecauseCucumber.METHOD_SETSELENIUMDRIVER, null, new Object[] {});
		if (tempdriver != null) {
			driver = (WebDriver) tempdriver;
		}

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
		if (BecauseCucumberHook.class.isAssignableFrom(reportClass)) {
			// load from the properties file for configuration
			Object invokeMethod = RefelectionUtils.invokeMethod(reportInstance, reportClass, METHOD_PROPERTYFILE,
					new Class[] {}, new Object[] {});
			if (invokeMethod != null) {
				// get the property file path
				String cucumberPath = invokeMethod.toString();

			}
			RefelectionUtils.invokeMethod(reportInstance, reportClass, METHOD_AFTERRUN, new Class[] {},
					new Object[] {});

		}

	}

	private void addChildren(List<CucumberFeature> cucumberFeatures) throws InitializationError {
		for (CucumberFeature cucumberFeature : cucumberFeatures) {
			children.add(new FeatureRunner(cucumberFeature, runtime, becauseCucumberReporter));
		}
	}

}

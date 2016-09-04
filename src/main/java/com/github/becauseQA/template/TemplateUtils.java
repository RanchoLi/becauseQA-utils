package com.github.becauseQA.template;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModelException;

public class TemplateUtils {

	private static Configuration configuration;

	/**
	 * initializeFreemarkerConfiguration:
	 * 
	 * @author alterhu2020@gmail.com
	 * @return the freemarker Configuration Object
	 * @since JDK 1.8
	 */
	private static Configuration initializeFreemarkerConfiguration() {
		if (configuration == null) {
			configuration = new Configuration(Configuration.VERSION_2_3_23);
			configuration.setDefaultEncoding("UTF-8");
			configuration.setAutoFlush(true);
			configuration.setTemplateUpdateDelayMilliseconds(5000);// load from
																	// the cache
			configuration.setLocalizedLookup(true);

			configuration.setIncompatibleImprovements(Configuration.VERSION_2_3_23);

			configuration.setNumberFormat("computer");
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
			configuration.setLogTemplateExceptions(false);

			// global share variables
			HashMap<Object, Object> shareVariables = new HashMap<>();
			shareVariables.put("Author", "Alter Hu");
			try {
				configuration.setSharedVaribles(shareVariables);
				// Template caching
				configuration.setSetting(Configuration.CACHE_STORAGE_KEY, "strong:20, soft:250");
			} catch (TemplateModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return configuration;
	}

	/**
	 * @param templatename
	 *            the template name.
	 * @param dataModel
	 *            the freemarker object. template is in src/main/resources
	 */
	public static void renderContent(String templatename, Object dataModel) {
		String outputPath = templatename + ".html";
		renderContent(templatename, outputPath, dataModel);

	}

	/**
	 * @param templatename
	 *            the template name.
	 * @param dataModel
	 *            the freemarker object. template is in src/main/resources
	 */
	public static void renderContent(String templatename, String filePath, Object dataModel) {
		ClassTemplateLoader classTemplateLoader = new ClassTemplateLoader(TemplateUtils.class.getClassLoader(),
				"/templates/");
		renderContent(classTemplateLoader, templatename, filePath, dataModel);

	}

	/**
	 * @param content the string content 
	 * @param dataModel
	 * @return
	 */
	public static String renderStringContent(String content, Object dataModel) {
		String templateName = "defaultStringTemplate";
		StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
		stringTemplateLoader.putTemplate(templateName, content);
		return renderContent(stringTemplateLoader, templateName, null, dataModel);

	}

	/**
	 * @param templateLoader
	 *            FileTemplateLoader StringTemplateLoader for testing
	 *            URLTemplateLoader ClassTemplateLoader WebappTemplateLoader
	 * @param templatename
	 * @param filePath
	 * @param dataModel
	 */
	public static String renderContent(TemplateLoader templateLoader, String templatename, String filePath,
			Object dataModel) {
		// String outputPath = templatename + ".html";
		StringWriter stringWriter = null;
		try {
			initializeFreemarkerConfiguration();
			configuration.setTemplateLoader(templateLoader);
			// configuration.setTemplateLoader(new
			// FileTemplateLoader(templateFolder));
			if (!templatename.endsWith(".ftl") && filePath != null) {
				templatename = templatename + ".ftl";
			}

			Template template = configuration.getTemplate(templatename);

			// Writer out = new OutputStreamWriter(System.out);
			// template.process(dataModel, out);
			// out.close();
			if (filePath != null) {
				FileWriter writer = new FileWriter(filePath);
				template.process(dataModel, writer);
				// writer.close();
			}
			stringWriter = new StringWriter();
			template.process(dataModel, stringWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringWriter.toString();

	}

	/**
	 * @param templatename
	 *            template name.
	 * @param result
	 *            the data model object.
	 * @throws TemplateException
	 *             template exception.
	 * @throws IOException
	 *             io exception for template.
	 */
	public static void rendMap(String templatename, Map<String, Object> result) throws TemplateException, IOException {
		String outputPath = templatename + ".html";
		rendMap(templatename, outputPath, result);
	}

	/**
	 * @param templatename
	 *            template name.
	 * @param result
	 *            the data model object.
	 * @throws TemplateException
	 *             template exception.
	 * @throws IOException
	 *             io exception for template.
	 */
	public static void rendMap(String templatename, String filePath, Map<String, Object> result)
			throws TemplateException, IOException {
		ClassTemplateLoader classTemplateLoader = new ClassTemplateLoader(TemplateUtils.class.getClassLoader(),
				"templates");
		rendMap(classTemplateLoader, templatename, filePath, result);

	}

	/**
	 * @param templatename
	 *            template name.
	 * @param result
	 *            the data model object.
	 * @throws TemplateException
	 *             template exception.
	 * @throws IOException
	 *             io exception for template.
	 */
	public static void rendMap(TemplateLoader templateLoader, String templatename, String filePath,
			Map<String, Object> result) throws TemplateException, IOException {

		try {
			initializeFreemarkerConfiguration();
			configuration.setTemplateLoader(templateLoader);
			if (!templatename.endsWith(".ftl")) {
				templatename = templatename + ".ftl";
			}
			Template template = configuration.getTemplate(templatename);

			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put("self", result);

			// Writer out = new OutputStreamWriter(System.out);
			// template.process(rootMap, out);
			// out.close();
			if (filePath != null) {
				FileWriter writer = new FileWriter(filePath);
				template.process(rootMap, writer);
				// writer.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

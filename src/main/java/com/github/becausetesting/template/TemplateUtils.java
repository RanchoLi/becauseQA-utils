package com.github.becausetesting.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class TemplateUtils {

	private static Configuration configuration;

	/**
	 * initializeFreemarkerConfiguration:
	 * 
	 * @author alterhu2020@gmail.com
	 * @return the freemarker Configuration Object
	 * @since JDK 1.8
	 */
	public static Configuration initializeFreemarkerConfiguration() {
		if (configuration != null) {
			configuration = new Configuration(Configuration.VERSION_2_3_23);
			configuration.setDefaultEncoding("UTF-8");
			configuration.setAutoFlush(true);
			configuration.setTemplateUpdateDelayMilliseconds(5000);
			configuration.setLocalizedLookup(true);

			configuration.setNumberFormat("computer");
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
			configuration.setLogTemplateExceptions(false);
		}
		return configuration;
	}

	/**
	 * @deprecated
	 * @param templatename
	 *            template short name.
	 * @param dataModel
	 *            the freemarker data model. template is in src/main/resources
	 */
	protected void renderResourceContent(String templatename, Object dataModel) {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		File templateFolder = new File(path + File.separator + "templates");
		File outputFolder = new File(path);
		String outputPath = outputFolder.getAbsolutePath() + File.separator + templatename + ".html";
		try {
			initializeFreemarkerConfiguration();
			configuration.setTemplateLoader(new FileTemplateLoader(templateFolder, true));
			Template template = configuration.getTemplate(templatename + ".jtl");
			FileWriter writer = new FileWriter(outputPath);
			template.process(dataModel, writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param templatename
	 *            the template name.
	 * @param dataModel
	 *            the freemarker object. template is in src/main/resources
	 */
	public void renderContent(String templatename, Object dataModel) {
		String outputPath = templatename + ".html";
		try {
			initializeFreemarkerConfiguration();
			configuration.setTemplateLoader(new ClassTemplateLoader(getClass().getClassLoader(), "templates"));
			// configuration.setTemplateLoader(new
			// FileTemplateLoader(templateFolder));
			Template template = configuration.getTemplate(templatename + ".ftl");

			Writer out = new OutputStreamWriter(System.out);
			template.process(dataModel, out);
			out.close();

			FileWriter writer = new FileWriter(outputPath);
			template.process(dataModel, writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	public void rendText(String templatename, Map<String, Object> result) throws TemplateException, IOException {
		String outputPath = templatename + ".html";
		try {
			initializeFreemarkerConfiguration();
			configuration.setTemplateLoader(new ClassTemplateLoader(getClass().getClassLoader(), "templates"));
			Template template = configuration.getTemplate(templatename + ".ftl");

			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put("self", result);

			Writer out = new OutputStreamWriter(System.out);
			template.process(rootMap, out);
			out.close();

			FileWriter writer = new FileWriter(outputPath);
			template.process(rootMap, writer);
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

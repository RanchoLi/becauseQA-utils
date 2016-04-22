package com.github.becausetesting.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.github.becausetesting.application.Environment;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateUtils {

	/**
	 * @deprecated
	 * @param templatename
	 * @param dataModel
	 * @see template is in src/main/resources
	 */
	protected void renderResourceContent(String templatename, Object dataModel) {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		File templateFolder = new File(path + File.separator + "templates");
		File outputFolder = new File(path);
		String outputPath = outputFolder.getAbsolutePath() + File.separator + templatename + ".html";
		try {
			Configuration configuration = Environment.ENVIRONMENT.getConfiguration();
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
	 * @param dataModel
	 * @see template is in src/main/resources
	 */
	public void renderContent(String templatename, Object dataModel) {
		String outputPath = templatename + ".html";
		try {
			Configuration configuration = Environment.ENVIRONMENT.getConfiguration();
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
	 * @param result
	 * @param out
	 * @throws TemplateException
	 * @throws IOException
	 */
	public void rendText(String templatename, Map<String, Object> result) throws TemplateException, IOException {
		String outputPath = templatename + ".html";
		try {
			Configuration configuration = Environment.ENVIRONMENT.getConfiguration();
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

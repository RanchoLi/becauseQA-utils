package com.github.becausetesting.application;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public enum Environment {

	ENVIRONMENT;

	private static Configuration configuration;

	/**
	 * initializeFreemarkerConfiguration:
	 * 
	 * @author alterhu2020@gmail.com
	 * @return the freemarker Configuration Object
	 * @since JDK 1.8
	 */
	public static Configuration initializeFreemarkerConfiguration() {
		configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setAutoFlush(true);
		configuration.setTemplateUpdateDelayMilliseconds(5000);
		configuration.setLocalizedLookup(true);

		configuration.setNumberFormat("computer");
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		configuration.setLogTemplateExceptions(false);
		return configuration;
	}


	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		Environment.configuration = configuration;
	}


}

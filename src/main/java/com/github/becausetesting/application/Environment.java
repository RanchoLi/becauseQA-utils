package com.github.becausetesting.application;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public enum Environment {

	ENVIRONMENT;

	private Configuration configuration;

	/**
	 * @return
	 */
	public Configuration initializeFreemarkerConfiguration() {
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
	
	public void beforeCucumber(){
		
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}


}

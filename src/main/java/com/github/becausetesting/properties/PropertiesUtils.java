package com.github.becausetesting.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesUtils {

	private static ResourceBundle resourceBundle;
	private static Properties properties;

	public static void setResource(File propertyfile) {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(propertyfile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setBundle(String propertyfile) {
		resourceBundle = ResourceBundle.getBundle(propertyfile, Locale.getDefault());
	}

	public static String getBundleString(String key) {
		if (resourceBundle != null) {
			return resourceBundle.getString(key);
		}
		return "Properties file path need to set firstly";
	}

	public static String getString(String key) {
		if (properties != null) {
			return properties.getProperty(key);
		}
		return "Properties file path need to set firstly";
	}

	public static String getEnvironment(String env) {
		String findenv = System.getenv(env);
		if (findenv == null) {
			findenv = getString(env);
		}

		return findenv;
	}
}

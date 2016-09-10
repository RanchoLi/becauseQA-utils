package com.github.becauseQA.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesUtils {

	private static ResourceBundle resourceBundle;
	private static Properties properties = new Properties();

	private static File xmlPropertiesFile = null;

	public static String getXMLPropertyValue(String propertyName) {
		String propertyValue = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(getXmlPropertiesFile());
			properties.loadFromXML(fileInputStream);
			propertyValue = properties.getProperty(propertyName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return propertyValue;
	}

	public static void saveXMLProperties(Properties properties) {
		try {
			FileOutputStream fileOut = new FileOutputStream(getXmlPropertiesFile());
			properties.storeToXML(fileOut, "Generate xml file properties " + getXmlPropertiesFile(), "UTF-8");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static File getXmlPropertiesFile() {
		return xmlPropertiesFile;
	}

	public static void setXmlPropertiesFile(File xmlPropertiesFile) {
		if (!xmlPropertiesFile.exists()) {
			try {
				FileOutputStream fileOut = new FileOutputStream(xmlPropertiesFile);
				properties.storeToXML(fileOut, "generate xml file properties " + xmlPropertiesFile, "UTF-8");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		PropertiesUtils.xmlPropertiesFile = xmlPropertiesFile;
	}

	public static void setPropertiesFile(File propertyfile) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(propertyfile);
			properties.load(fileInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	// **********************using
	// resourcebundle********************************************
	public static void setResourceBundle(String filename) {
		resourceBundle = ResourceBundle.getBundle(filename, Locale.getDefault());
	}

	public static void setResourceBundle(File propertyfile) {
		String filename = propertyfile.getName();
		int pos = filename.lastIndexOf(".");
		if (pos > 0) {
			filename = filename.substring(0, pos);
		}
		resourceBundle = ResourceBundle.getBundle(filename, Locale.getDefault());
		// InputStream propertyresource =
		// PropertyUtils.class.getClassLoader().getResourceAsStream(propertyfile+".properties");
	}

	public static String getBundleString(String key) {
		return resourceBundle != null ? resourceBundle.getString(key) : "Properties File Path not set Correctly";
	}

	// **********************using
	// property********************************************
	public static String getPropertyString(String key) {
		return properties != null ? properties.getProperty(key) : "Properties file path need to set firstly";
	}

	/**
	 * in jenkins if the properties had not set then it will return null which
	 * will caused it use the default value ,we should prevent the properties
	 * value in jenkins build not be empty
	 * 
	 * @param env
	 *            the environment vlaue
	 * @return the property value
	 */
	public static String getEnvironment(String env) {
		String findenv = System.getenv(env);
		if (findenv == null) {
			findenv = getPropertyString(env);
		}

		return findenv;
	}
}

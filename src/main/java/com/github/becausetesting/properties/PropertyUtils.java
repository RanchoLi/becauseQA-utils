package com.github.becausetesting.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertyUtils {

	private static ResourceBundle resourceBundle;
	private static Properties properties = new Properties();
	
	//**********************using resourcebundle********************************************
	
	public static void setResourceBundle(String propertyfile) {
		ResourceBundle tempresourceBundle = ResourceBundle.getBundle(propertyfile, Locale.getDefault());
		InputStream propertyresource = PropertyUtils.class.getClassLoader().getResourceAsStream(propertyfile+".properties");
		try {
			properties.load(propertyresource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resourceBundle = tempresourceBundle;
	}
	
	public static String getBundleString(String key){
		return resourceBundle.getString(key);
	}

	//**********************using resourcebundle********************************************
	
	
	//**********************using property********************************************
	public static String getPropertyString(String key){
		return properties.getProperty(key);
	}
	
}

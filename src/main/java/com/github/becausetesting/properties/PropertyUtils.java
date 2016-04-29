package com.github.becausetesting.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertyUtils {

	private static ResourceBundle resourceBundle;
	private static Properties properties = new Properties();
	
	//**********************using resourcebundle********************************************
	
	public static void setResourceBundle(File propertyfile) {
		String filename = propertyfile.getName();
		int pos=filename.lastIndexOf(".");
		if(pos>0){
			filename=filename.substring(0, pos);
		}
		ResourceBundle tempresourceBundle = ResourceBundle.getBundle(filename, Locale.getDefault());
		//InputStream propertyresource = PropertyUtils.class.getClassLoader().getResourceAsStream(propertyfile+".properties");
		try {
			properties.load(new FileInputStream(propertyfile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resourceBundle = tempresourceBundle;
	}
	
	public static String getBundleString(String key){
		return resourceBundle.getString(key);
	}

	public static String setBundleString(String key,String value){
		return resourceBundle.getString(key);// ????
	}

	
	//**********************using resourcebundle********************************************
	
	
	//**********************using property********************************************
	public static String getPropertyString(String key){
		return properties.getProperty(key);
	}
	
}

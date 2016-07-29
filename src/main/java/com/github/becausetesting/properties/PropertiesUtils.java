package com.github.becausetesting.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesUtils {

	private static ResourceBundle resourceBundle;
	private static Properties properties = new Properties();
	
	public static void setResource(File propertyfile) {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(propertyfile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//**********************using resourcebundle********************************************
	
	public static void setResourceBundle(File propertyfile) {
		String filename = propertyfile.getName();
		int pos=filename.lastIndexOf(".");
		if(pos>0){
			filename=filename.substring(0, pos);
		}
		ResourceBundle tempresourceBundle = ResourceBundle.getBundle(filename, Locale.getDefault());
		//InputStream propertyresource = PropertyUtils.class.getClassLoader().getResourceAsStream(propertyfile+".properties");

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
		if (properties != null) {
			return properties.getProperty(key);
		}
		return "Properties file path need to set firstly";
	}
	
	/** in jenkins if the properties had not set then it will return null
	 * which will caused it use the default value ,we should prevent the 
	 * properties value in jenkins build not be empty
	 * 
	 * @param env the environment vlaue
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

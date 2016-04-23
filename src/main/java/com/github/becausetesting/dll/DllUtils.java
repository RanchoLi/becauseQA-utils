package com.github.becausetesting.dll;

import java.io.File;
import java.util.List;

import com.github.becausetesting.string.StringUtils;


/**
 * ClassName: DllUtils  
 * Function: TODO ADD FUNCTION.  
 * Reason: TODO ADD REASON 
 * date: Apr 23, 2016 6:14:32 PM  
 * @author alterhu2020@gmail.com
 * @version 1.0.0
 * @since JDK 1.8
 */
public class DllUtils {

	public void loadDll(String directory, String name) {

		try {
			System.loadLibrary(name);
		} catch (UnsatisfiedLinkError e) {
			loadFromJar(directory, name);
		}

		// viewJavaLibraries();
	}

	protected void loadFromJar(String path, String name) {
		//name = name + ".dll";
		String libFileName = System.mapLibraryName(name);
		System.load(path + File.separator + libFileName);
	}

	protected void viewJavaLibraries() {
		String property = System.getProperty("java.library.path");
		StringUtils stringUtils = new StringUtils();
		List<String> seperateStr = stringUtils.split(property, ";");
		System.out.println("------java.library.path------");
		for (String str : seperateStr) {
			System.out.println(str);
		}
		System.out.println("------java.library.path------");
	}
}

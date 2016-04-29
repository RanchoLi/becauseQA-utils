package com.github.becausetesting.dll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.github.becausetesting.file.FileUtils;
import com.github.becausetesting.lang.StringUtils;
import com.github.becausetesting.regexp.RegexpUtils;

import net.sourceforge.jtds.util.SSPIJNIClient;

/**
 * ClassName: DllUtils Function: TODO ADD FUNCTION. Reason: TODO ADD REASON
 * date: Apr 23, 2016 6:14:32 PM
 * 
 * @author alterhu2020@gmail.com
 * @version 1.0.0
 * @since JDK 1.8
 */
public class DllUtils {

	/**
	 * @param resourcePath
	 *            the dll resource file path in src/main/resources folder ,the
	 *            path should be /dllfolder
	 * @param libname
	 *            the dll file name without suffix
	 */
	public static String loadDll(String resourcePath, String libname) {
		String tempdllpath = System.getProperty("java.io.tmpdir");
		String libFileName = System.mapLibraryName(libname);
		String destationfile = tempdllpath + libFileName;
		
		String resourceFilePath = null;
		if (resourcePath.endsWith(File.separator)) {
			resourceFilePath = resourcePath + libFileName;
		} else {
			resourceFilePath = resourcePath + File.separator + libFileName;
		}
		InputStream resourceFile = DllUtils.class.getResourceAsStream(resourceFilePath);
		// copy the file to local host
		
		FileOutputStream output=null;
		try {
			output = new FileOutputStream(destationfile);
			FileUtils.copy(resourceFile, output);

			System.load(destationfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return destationfile;
	}

	protected void loadFromJar(String path, String name) {
		// name = name + ".dll";
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

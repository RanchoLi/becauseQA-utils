/**
 * Project Name:commons
 * File Name:FileUtilsTest.java
 * Package Name:com.github.becausetesting.file
 * Date:Apr 17, 20167:05:44 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.file;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * ClassName:FileUtilsTest Function: TODO ADD FUNCTION. Reason: TODO ADD REASON.
 * Date: Apr 17, 2016 7:05:44 PM
 * 
 * @author Administrator
 * @version
 * @since JDK 1.8
 * @see
 */
public class FileUtilsTest {

	private FileUtils fileUtils;

	@Before
	public void setUp() throws Exception {
		fileUtils = new FileUtils();

	}

	@Test
	public void test() {
		String pathname = "samplefile.txt";
		fileUtils.createFile(new File(pathname));
		fileUtils.deleteFileorDirectory(new File(pathname));

		fileUtils.copyorMove("D:\\Downloads\\OpenLiveWriterSetup.exe", "E:\\Softwares");

		List<File> listFilePath = fileUtils.listFilePath("E:\\Softwares", ".exe");

		System.out.println(listFilePath.toString());

	}

}

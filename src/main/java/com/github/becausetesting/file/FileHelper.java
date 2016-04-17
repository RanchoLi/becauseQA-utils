/**
 * Project Name:commons
 * File Name:FileHelper.java
 * Package Name:com.github.becausetesting.file
 * Date:Apr 17, 20168:32:01 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * ClassName:FileHelper Function: TODO ADD FUNCTION. Reason: TODO ADD REASON.
 * Date: Apr 17, 2016 8:32:01 PM
 * 
 * @author Administrator
 * @version
 * @since JDK 1.8
 * @see
 */
public class FileHelper {

	// read file
	public String readFile(String filePath) throws IOException {
		BufferedReader reader = null;
		String allline = "";
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String templine = null;
			while ((templine = reader.readLine()) != null) {
				allline += templine + "\n";
			}

		} catch (FileNotFoundException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			reader.close();
		}
		return allline;
	}

	// write file
	public void writeFile(String sourceFile, String destinationFile) {
		BufferedInputStream reader = null;
		BufferedOutputStream writer = null;
		try {
			reader = new BufferedInputStream(new FileInputStream(sourceFile));
			writer = new BufferedOutputStream(new FileOutputStream(destinationFile));
			byte[] bytes = new byte[1024];
			int length = 0;
			int bytesum = 0;
			while ((length = reader.read(bytes)) != -1) {
				bytesum += length;
				writer.write(bytes, 0, length);

			}
		} catch (FileNotFoundException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				writer.close();
				reader.close();
			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
	}
}

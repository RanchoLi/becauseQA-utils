/**
 * Project Name:commons
 * File Name:FileUtils.java
 * Package Name:com.github.becausetesting.file
 * Date:Apr 16, 201611:19:12 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName:FileUtils Function: TODO ADD FUNCTION. Reason: TODO ADD REASON.
 * Date: Apr 16, 2016 11:19:12 PM
 * 
 * @author Administrator
 * @version
 * @since JDK 1.8
 * @see
 */
public class FileUtils {

	private List<File> fileList = new ArrayList<File>();

	public void createFile(File filePath) {
		if (filePath.exists()) {
			filePath.delete();
		}

		try {
			boolean isDir = filePath.isDirectory();
			if (!isDir) {
				filePath.createNewFile();
			} else {
				filePath.mkdirs();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public void deleteFileorDirectory(File filePath) {
		if (filePath.exists()) {
			filePath.delete();
		}
	}

	public boolean copyorMove(String sourceFile, String destinationPath) {
		File file = new File(sourceFile);
		if (file.exists()) {
			String filename = file.getName();
			File dir = new File(destinationPath);
			if (dir.isDirectory()) {
				return file.renameTo(new File(dir, filename));
			} else {
				return file.renameTo(dir);
			}
		} else {
			return false;
		}
	}

	public boolean copyorMoveStream(String sourceFile, String destinationPath) {
		File file = new File(sourceFile);
		if (file.exists()) {
			InputStream inStream;
			try {
				inStream = new FileInputStream(sourceFile);
				FileOutputStream fs = new FileOutputStream(destinationPath);
				byte[] buffer = new byte[1444];
				int length = 0;
				int bytesum = 0;
				while ((length = inStream.read(buffer)) != -1) {
					bytesum += length;
					fs.write(buffer, 0, length);
				}
				inStream.close();
				fs.close();
				return true;
			} catch (FileNotFoundException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
		return false;
	}

	public List<File> listFilePath(String filePath, String... filter) {
		File file = new File(filePath);
		if (file.exists() && file.isDirectory()) {
			File[] listFiles = file.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {

					// TODO Auto-generated method stub

					return true;
				}
			});

			for (File filterFile : listFiles) {
				String filename = filterFile.getName();
				String filterfilePath = filterFile.getAbsolutePath();
				if (filterFile.isFile()) {
					fileList.add(filterFile);
				} else {
					listFilePath(filterfilePath, filename);
				}
			}

		} 

		return fileList;
	}
}

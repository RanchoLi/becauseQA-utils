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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:FileUtils Function: TODO ADD FUNCTION. Reason: TODO ADD REASON.
 * Date: Apr 16, 2016 11:19:12 PM
 * 
 * @author Administrator
 * @version 1.0.0
 * @since JDK 1.8
 */
public class FileUtils {

	private List<File> fileList = new ArrayList<File>();

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

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

	/**
	 * @param filePath delete file or folder path.
	 */
	public void deleteFileorFolder(File filePath) {
		if (filePath.exists() && filePath.isFile()) {
			filePath.delete();
		} else {
			File[] listFiles = filePath.listFiles();
			if (listFiles.length >= 1) {
				for (int k = 0; k < listFiles.length; k++) {
					listFiles[k].delete();
				}
			}
			filePath.delete();
		}
	}

	/**
	 * @param sourceFileFolder the dll in resource folder.
	 * @param libname the dll name without suffix.
	 * @return the dll temp folder
	 */
	public static String copyDll(String sourceFileFolder, String libname) {
		String tempdllpath = System.getProperty("java.io.tmpdir");
		String mapLibraryName = System.mapLibraryName(libname);
		String destationfile = tempdllpath+ mapLibraryName;

		String useSourcePath = null;
		if (sourceFileFolder.endsWith(File.separator)) {
			useSourcePath = sourceFileFolder + mapLibraryName;
		} else {
			useSourcePath = sourceFileFolder + File.separator + mapLibraryName;
		}

		try {
			InputStream resourceAsStream = FileUtils.class.getResourceAsStream(useSourcePath);
			FileOutputStream fileOutputStream = new FileOutputStream(destationfile);
			copy(resourceAsStream, fileOutputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return destationfile;
	}

	public static boolean copy(String sourceFile, String destinationPath) {
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

	public static long copy(InputStream input, OutputStream output) {
		long count = 0;
		int length = 0;
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		try {
			while ((length = input.read(buffer)) != -1) {
				output.write(buffer, 0, length);
				count += length;
			}
			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	public static long copyFile(String sourceFile, String destinationPath) {
		File file = new File(sourceFile);
		int bytesum = 0;
		if (file.exists()) {
			InputStream inStream;
			try {
				inStream = new FileInputStream(sourceFile);
				FileOutputStream fs = new FileOutputStream(destinationPath);
				byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
				int length = 0;

				while ((length = inStream.read(buffer)) != -1) {
					bytesum += length;
					fs.write(buffer, 0, length);
				}
				fs.flush();
				fs.close();
				inStream.close();

			} catch (FileNotFoundException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
		return bytesum;

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

/**
 * Project Name:commons
 * File Name:ExcelUtils.java
 * Package Name:com.github.becauseQA.file
 * Date:Apr 16, 201611:19:36 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becauseQA.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * ClassName:ExcelUtils Function: TODO ADD FUNCTION. Reason: TODO ADD REASON.
 * Date: Apr 16, 2016 11:19:36 PM
 * 
 * @author Administrator
 * @version 1.0.0
 * @since JDK 1.8
 */
public class ExcelUtils {

	public static Workbook workbook;
	public static Sheet sheet;
	public static ExcelUtils INSTANCE = null;
	private static List<String> header = new ArrayList<String>();
	private static Map<String, String> rowmap = new HashMap<String, String>();

	private boolean findRowData = false;
	private static int currentRowNumber = 0;
	private static int totalColumns = 0;
	private static int totalRows = 0;

	/**
	 * 
	 * @author ahu@greendotcorp.com
	 * @param excelfile
	 *            the excel file name.
	 * @return ExcelUtils return type
	 */

	public static ExcelUtils getInstance(File excelfile, String sheetName) {
		// Creating only when required.
		INSTANCE = new ExcelUtils();
		try {
			workbook = Workbook.getWorkbook(excelfile);
			sheet = workbook.getSheet(sheetName);
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return INSTANCE;
	}

	/**
	 * getRowdataViaOneColumns:
	 * 
	 * @author alterhu2020@gmail.com
	 * @param sheetname
	 *            excel sheet name.
	 * @param firstColumnValue
	 *            first column name.
	 * @return excel values
	 * @since JDK 1.8
	 */
	public Map<String, String> getRowdataByFirstOneColumn(String firstColumnValue) {
		totalRows = sheet.getRows();
		totalColumns = sheet.getColumns();

		for (int columnindex = 0; columnindex < totalColumns; columnindex++) {
			String headerelement = sheet.getCell(columnindex, 0).getContents().trim();
			header.add(columnindex, headerelement);
		}
		for (int rowindex = 1; rowindex < totalRows; rowindex++) {
			// get the column 1 to get the host name,the column index begin with
			// 0
			String firstcontent = sheet.getCell(0, rowindex).getContents().toLowerCase().trim();

			if (firstcontent.toLowerCase().contains(firstColumnValue.toLowerCase().trim())) {
				//System.out.println("Found the correct cell data,the case type we found in excel is:" + firstcontent);
				findRowData = true;
				currentRowNumber = rowindex;
				break;
			}

		}
		// put the map value for the host name row
		if (findRowData) {
			for (int columnindex = 0; columnindex < totalColumns; columnindex++) {
				String findcontent = sheet.getCell(columnindex, currentRowNumber).getContents().trim();
				String mapheader = header.get(columnindex);
				rowmap.put(mapheader, findcontent);
			}
		}

		return rowmap;
	}

	/**
	 * getRowdataViaOneColumns:
	 * 
	 * @author alterhu2020@gmail.com
	 * @param sheetname
	 *            excel sheet name.
	 * @param firstColumnValue
	 *            first column name.
	 * @return excel values
	 * @since JDK 1.8
	 */
	public Map<String, String> getRowdataByFirstTwoColumns(String firstColumnValue, String secondColumnValue) {
		getRowdataByFirstOneColumn(firstColumnValue);

		for (int rowindex = currentRowNumber; rowindex < totalRows; rowindex++) {
			// get the column 1 to get the host name,the column index begin with
			// 0
			String firstcontent = sheet.getCell(0, rowindex).getContents().toLowerCase().trim();
			String secondcontent = sheet.getCell(1, rowindex).getContents().toLowerCase().trim();

			if (firstcontent.toLowerCase().contains(firstColumnValue.toLowerCase().trim())
					&& secondcontent.toLowerCase().contains(secondColumnValue.toLowerCase().trim())) {
				findRowData = true;
				currentRowNumber = rowindex;
				break;
			}
		}
		// put the map value for the host name row
		if (findRowData) {
			for (int columnindex = 0; columnindex < totalColumns; columnindex++) {
				String findcontent = sheet.getCell(columnindex, currentRowNumber).getContents().trim();
				String mapheader = header.get(columnindex);
				rowmap.put(mapheader, findcontent);
			}
		}
		return rowmap;
	}

	/**
	 * getRowdataViaOneColumns:
	 * 
	 * @author alterhu2020@gmail.com
	 * @param sheetname
	 *            excel sheet name.
	 * @param firstColumnValue
	 *            first column name.
	 * @return excel values
	 * @since JDK 1.8
	 */
	public Map<String, String> getRowdataByFirstThreeColumns(String firstColumnValue, String secondColumnValue,
			String thirdColumnValue) {
		getRowdataByFirstTwoColumns(firstColumnValue, secondColumnValue);

		for (int rowindex = currentRowNumber; rowindex < totalRows; rowindex++) {
			// get the column 1 to get the host name,the column index begin with
			// 0
			String firstcontent = sheet.getCell(0, rowindex).getContents().toLowerCase().trim();
			String secondcontent = sheet.getCell(1, rowindex).getContents().toLowerCase().trim();
			String thirdcontent = sheet.getCell(2, rowindex).getContents().toLowerCase().trim();

			if (firstcontent.toLowerCase().contains(firstColumnValue.toLowerCase().trim())
					&& secondcontent.toLowerCase().contains(secondColumnValue.toLowerCase().trim())
					&& thirdcontent.toLowerCase().contains(thirdColumnValue.toLowerCase().trim())) {
				findRowData = true;
				currentRowNumber = rowindex;
				break;
			}
		}
		// put the map value for the host name row
		if (findRowData) {
			for (int columnindex = 0; columnindex < totalColumns; columnindex++) {
				String findcontent = sheet.getCell(columnindex, currentRowNumber).getContents().trim();
				String mapheader = header.get(columnindex);
				rowmap.put(mapheader, findcontent);
			}
		}
		return rowmap;
	}
}

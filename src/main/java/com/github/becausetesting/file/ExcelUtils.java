/**
 * Project Name:commons
 * File Name:ExcelUtils.java
 * Package Name:com.github.becausetesting.file
 * Date:Apr 16, 201611:19:36 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.file;

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
 * ClassName:ExcelUtils  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     Apr 16, 2016 11:19:36 PM 
 * @author   Administrator
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class ExcelUtils {

	public static Workbook workbook;
	public static ExcelUtils INSTANCE = null;
	
	/**
	 * @Title: getInstance
	 * @Description: TODO
	 * @author ahu@greendotcorp.com
	 * @param @return
	 * @return ExcelUtils return type
	 * @throws
	 */

	public static ExcelUtils getInstance(File excelfile) {
		// Creating only when required.
		if (INSTANCE == null) {
			INSTANCE = new ExcelUtils();
			try {
				workbook = Workbook.getWorkbook(excelfile);
			} catch (BiffException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return INSTANCE;
	}
	
	/**
	 * @Title: getDataTableRowBasedOnTowParameterValues
	 * @Description: TODO
	 * @author alterhu2020@gmail.com
	 * @param @param sheetname
	 * @param @param firstparametervalue,secondparametervalue
	 * @param @return
	 * @return Map<String,String> return type
	 * @throws
	 */

	public Map<String, String> getRowdataViaOneColumns(
			String sheetname,
			String firstcolumn) {

		List<String> header = new ArrayList<String>();
		Map<String, String> rowmap = new HashMap<String, String>();

		boolean findrow = false;
		int rownumber = 0;
		Sheet sheet = workbook.getSheet(sheetname);
		int rows = sheet.getRows();
		int columns = sheet.getColumns();

		for (int columnindex = 0; columnindex < columns; columnindex++) {
			String headerelement = sheet.getCell(columnindex, 0).getContents()
					.trim();
			header.add(columnindex, headerelement);
		}

		for (int rowindex = 1; rowindex < rows; rowindex++) {
			// get the column 1 to get the host name,the column index begin with
			// 0
			String firstcontent = sheet.getCell(0, rowindex).getContents()
					.toLowerCase().trim();
			
			if (firstcontent.toLowerCase().contains(firstcolumn.toLowerCase().trim())) {
				System.out.println("Found the correct cell data,the case type we found in excel is:"
						+ firstcontent);
				findrow = true;
				rownumber = rowindex;
				break;
			} else {
				findrow = false;
			}

		}
		// put the map value for the host name row
		if (findrow) {
			for (int columnindex = 0; columnindex < columns; columnindex++) {
				String findcontent = sheet.getCell(columnindex, rownumber)
						.getContents().trim();
				String mapheader = header.get(columnindex);
				rowmap.put(mapheader, findcontent);
			}
		}

		return rowmap;
	}
}


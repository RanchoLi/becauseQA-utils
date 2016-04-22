/**
 * Project Name:commons
 * File Name:JDBCUtils.java
 * Package Name:com.github.becausetesting.database
 * Date:Apr 16, 201611:07:18 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.github.becausetesting.properties.PropertyUtils;

/**
 * ClassName:JDBCUtils  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     Apr 16, 2016 11:07:18 PM 
 * @author   Administrator
 * @version  
 * @since    JDK 1.8
 * @see 	
 * 
 *  jtds connection string: 
 *  SQL Server: 
 *    driver class: net.sourceforge.jtds.jdbc.Driver
 *    url: jdbc:jtds:sqlserver://GDCQA4-SQL01;DatabaseName=QA4;user=MyUserName;password=myPassword;
 *         jdbc:jtds:sqlserver://GDCQA4-SQL01;DatabaseName=QA4;useNTLMv2=true;domain=nextestate.com;
 *         
 *         jdbc:sqlserver://localhost:1433;DatabaseName=QA4;integratedSecurity=true;
 *         
 *    
 */
public class SQLServerUtils {


	public static String drivername = null;
	public static String driverurl = null;
	public static String user = null;
	public static String password = null;
	public static Connection connection = null;
	public static ResultSet rs = null;

	
	/**
	 * @Title: getConnection @Description: TODO @author
	 * ahu@greendotcorp.com @param @param drivername @param @param
	 * driverurl @param @param user @param @param
	 * password @param @return @return Connection return type @throws
	 * 
	 * @see jdbc:jtds:sqlserver://GDCQA4-SQL01/QA4;useNTLMv2=true;domain=nextestate.com;
	 */
	

	public void getAuthorizationConnection(String url) {

		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			connection = DriverManager.getConnection(url);
			System.out.println("Build the JDBC driver connection successfully....");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * @Title: getConnection @Description: TODO @author
	 * ahu@greendotcorp.com @param @param drivername @param @param
	 * driverurl @param @param user @param @param
	 * password @param @return @return Connection return type @throws
	 * @see useNTLMv2=true;domain=nextestate.com
	 */

	public void getConnection(String url,String user,String password) {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			connection = DriverManager.getConnection(url,user,password);
			System.out.println("Build the JDBC driver connection successfully....");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @Title: getConnection @Description: TODO @author
	 * ahu@greendotcorp.com @param @return @return Connection return
	 * type @throws
	 */

	public void getConnection(File propertyfile) {
		
		PropertyUtils.setResourceBundle(propertyfile);
		
		//drivername = PropertyUtils.getBundleString("driver.name");
		driverurl = PropertyUtils.getBundleString("driver.url");
		user = PropertyUtils.getBundleString("driver.user");
		password = PropertyUtils.getBundleString("driver.password");
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			connection = DriverManager.getConnection(driverurl, user, password);
		} catch (ClassNotFoundException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	
	}

	/**
	 * @Title: selectRecord @Description: TODO @author
	 * ahu@greendotcorp.com @param @param con @param @param
	 * sql @param @return @return ResultSet return type @throws
	 */

	public ResultSet selectRecord(String sql) {
		try {
			rs = connection.prepareStatement(sql).executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}

	/**
	 * Method updateRecord.
	 * 
	 * @param con
	 *            Connection
	 * @param deletesql
	 *            String
	 * @return int
	 */
	public int updateRecord(String sql) {
		int updaterows = 0;
		try {
			updaterows = connection.prepareStatement(sql).executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updaterows;

	}
	

	/**
	 * @Title: runBatchSQL @Description: TODO @author
	 * ahu@greendotcorp.com @param @param con @param @param
	 * sql @param @return @return int return type @throws
	 */

	public int updateBatchSQL(String... sql) {
		int updaterow = 0;
		for (String subsql : sql) {
			updaterow = updaterow + updateRecord(subsql);
		}
		return updaterow;

	}
	
	
	/**
	 * @Title: callStoreProcedure @Description: TODO @author
	 * ahu@greendotcorp.com @param @param con @param @param
	 * procedure @param @return @return CallableStatement return type @throws
	 */

	public static CallableStatement callStoreProcedure(String procedure) {
		try {
			CallableStatement prepareCall = connection.prepareCall(procedure);
			return prepareCall;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}




	/**
	 * Method closeAllConnections.
	 * 
	 * @param con
	 *            Connection
	 * @param rs
	 *            ResultSet
	 */
	public static void closeAllConnections(Connection con, ResultSet rs) {
		try {
			if (con != null) {
				con.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}


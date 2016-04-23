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
 * @version  1.0.0
 * @since    JDK 1.8
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
public class MySQLUtils {


	public static String drivername = null;
	public static String driverurl = null;
	public static String user = null;
	public static String password = null;
	public static Connection connection = null;
	public static ResultSet rs = null;

	

	

	/**
	 * getAuthorizationConnection:  
	 * jdbc:jtds:sqlserver://GDCQA4-SQL01/QA4;useNTLMv2=true;domain=nextestate.com;
	 * @author alterhu2020@gmail.com
	 * @param url url parameter.
	 * @since JDK 1.8
	 */
	public void getAuthorizationConnection(String url) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
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
	 * getConnection: get jdbc connection
	 * @author alterhu2020@gmail.com
	 * @param url the jdbc url.
	 * @param user the jdbc username.
	 * @param password the jdbc password.
	 * @since JDK 1.8
	 */
	public void getConnection(String url,String user,String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
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
	 * getConnection: get jdbc connection
	 * @author alterhu2020@gmail.com
	 * @param propertyfile the jdbc store properties file.
	 * @since JDK 1.8
	 */
	public void getConnection(File propertyfile) {
		
		PropertyUtils.setResourceBundle(propertyfile);
		
		driverurl = PropertyUtils.getBundleString("driver.url");
		user = PropertyUtils.getBundleString("driver.user");
		password = PropertyUtils.getBundleString("driver.password");
		try {
			Class.forName("com.mysql.jdbc.Driver");
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
	 * selectRecord: 
	 * @author alterhu2020@gmail.com
	 * @param sql sql statement.
	 * @return ResultSet
	 * @since JDK 1.8
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
	 * updateRecord: update the sql
	 * @author alterhu2020@gmail.com
	 * @param sql the sql statement.
	 * @return the count affected.
	 * @since JDK 1.8
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
	 * updateBatchSQL: update batch SQL
	 * @author alterhu2020@gmail.com
	 * @param sql the sql statement.
	 * @return int the update count.
	 * @since JDK 1.8
	 */
	public int updateBatchSQL(String... sql) {
		int updaterow = 0;
		for (String subsql : sql) {
			updaterow = updaterow + updateRecord(subsql);
		}
		return updaterow;

	}
	


	/**
	 * callStoreProcedure: call a storeprocedure
	 * @author alterhu2020@gmail.com
	 * @param procedure the procedure name.
	 * @return CallableStatement the storeprocedure object.
	 * @since JDK 1.8
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
	 * closeAllConnections: close the jdbc connection
	 * @author alterhu2020@gmail.com
	 * @param con jdbc connection object.
	 * @param rs jdbc resultset object.
	 * @since JDK 1.8
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


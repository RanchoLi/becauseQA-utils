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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.github.becausetesting.properties.PropertiesUtils;

/**
 * ClassName:JDBCUtils Function: TODO ADD FUNCTION. Reason: TODO ADD REASON.
 * Date: Apr 16, 2016 11:07:18 PM
 * 
 * @author Administrator
 * @version 1.0.0
 * @since JDK 1.8
 * 
 *        jtds connection string: SQL Server: driver class:
 *        net.sourceforge.jtds.jdbc.Driver url:
 *        jdbc:jtds:sqlserver://GDCQA4-SQL01;DatabaseName=QA4;user=MyUserName;
 *        password=myPassword;
 *        jdbc:jtds:sqlserver://GDCQA4-SQL01;DatabaseName=QA4;useNTLMv2=true;
 *        domain=nextestate.com;
 * 
 *        jdbc:sqlserver://localhost:1433;DatabaseName=QA4;integratedSecurity=
 *        true;
 * 
 * 
 */
public class JDBCUtils {

	public static Connection connection = null;
	public static PreparedStatement ps = null;
	public static CallableStatement cs = null;

	protected static Logger logger = Logger.getLogger(JDBCUtils.class);

	public enum DatabaseDriver {
		MYSQL("com.mysql.jdbc.Driver"), SQLSERVER("net.sourceforge.jtds.jdbc.Driver"), ORACLE(
				"oracle.jdbc.driver.OracleDriver");

		private String drivername;

		DatabaseDriver(String drivername) {
			// TODO Auto-generated constructor stub
			this.drivername = drivername;
		}

		public String getDrivername() {
			return drivername;
		}

		public void setDrivername(String drivername) {
			this.drivername = drivername;
		}

	}

	private static String setDriverUrl(DatabaseDriver driver, String serverName, String defaultDatabaseName) {
		String url = "";
		try {
			Class.forName(driver.getDrivername());
			switch (driver) {
			case SQLSERVER:
				url = "jdbc:jtds:sqlserver://" + serverName + "/" + defaultDatabaseName;
				break;
			case ORACLE:
				url = "jdbc:oracle://" + serverName + "/" + defaultDatabaseName;
				break;
			default:
				url = "jdbc:mysql://" + serverName + "/" + defaultDatabaseName;
				break;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return url;

	}

	/**
	 * getAuthorizationConnection:
	 * 
	 * @author alterhu2020@gmail.com
	 * @param url
	 *            url for jdbc.
	 * @since JDK 1.8
	 *        jdbc:jtds:sqlserver://GDCQA4-SQL01/QA4;useNTLMv2=true;domain=
	 *        nextestate.com;
	 */
	public static void getNTLMConnection(DatabaseDriver driver, String domain, String serverName,
			String defaultDatabaseName) {
		String NTLMStr = ";useNTLMv2=true;domain=" + domain;
		if (connection == null) {
			try {
				String driverurl = setDriverUrl(driver, serverName, defaultDatabaseName);
				connection = DriverManager.getConnection(driverurl + NTLMStr);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * getConnection: get jdbc connection
	 * 
	 * @author alterhu2020@gmail.com
	 * @param url
	 *            jdbc url.
	 * @param user
	 *            jdbc username.
	 * @param password
	 *            jdbc password.
	 * @since JDK 1.8 useNTLMv2=true;domain=nextestate.com
	 */
	public static void getConnection(DatabaseDriver driver, String serverName, String defaultDatabaseName, String user,
			String password) {

		try {
			String driverurl = setDriverUrl(driver, serverName, defaultDatabaseName);
			connection = DriverManager.getConnection(driverurl, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * getConnection: get jdbc connection
	 * 
	 * @author alterhu2020@gmail.com
	 * @param propertyfile
	 *            the jdbc properties file.
	 * @since JDK 1.8
	 */
	public static void getConnection(DatabaseDriver driver, File propertyfile) {
		if (connection == null) {
			PropertiesUtils.setResourceBundle(propertyfile);

			// drivername = PropertyUtils.getBundleString("driver.name");
			String serverName = PropertiesUtils.getBundleString("driver.serverName");
			String defaultDatabaseName = PropertiesUtils.getBundleString("driver.defaultDatabaseName");
			String user = PropertiesUtils.getBundleString("driver.user");
			String password = PropertiesUtils.getBundleString("driver.password");
			getConnection(driver, serverName, defaultDatabaseName, user, password);

		}

	}

	/**
	 * prepare the sql to run for select query,you need to use executeQuery();
	 * for insert,update,delete you need to user executeUpdate(); for batch job
	 * ,first addBatch(); then executeBatch();//
	 * http://viralpatel.net/blogs/batch-insert-in-java-jdbc/ batch run
	 * 
	 * @param sql
	 * @return PreparedStatement ps
	 * @throws SQLException
	 */
	public static PreparedStatement prepareSql(String sql) {
		try {
			ps = connection.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ps;
	}

	/**
	 * sp need to run for select query,you need to use executeQuery(); for
	 * insert,update,delete you need to user executeUpdate(); for batch job
	 * ,first addBatch(); then executeBatch();//
	 * http://viralpatel.net/blogs/batch-insert-in-java-jdbc/ batch run
	 * 
	 * @param procedure
	 *            the sql statement like: {call
	 *            GDFN.dbo.GetRetailersByProgramPartner (?, ?)}
	 * @return CallableStatement prepareCall
	 */
	public static CallableStatement prepareStoreProcedure(String sp) {
		try {
			cs = connection.prepareCall(sp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cs;
	}

}

/**
 * Project Name:commons
 * File Name:JDBCUtils.java
 * Package Name:com.github.becauseQA.database
 * Date:Apr 16, 201611:07:18 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becauseQA.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;

import com.github.becauseQA.apache.commons.StringUtils;

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
 *        jdbc:jtds:sqlserver://servername;DatabaseName=db;user=MyUserName;
 *        password=myPassword;
 *        jdbc:jtds:sqlserver://servername;DatabaseName=db;useNTLMv2=true;
 *        domain=domain.name;
 * 
 *        jdbc:sqlserver://localhost:1433;DatabaseName=dab;integratedSecurity=
 *        true;
 * 
 * 
 */
public class JDBCUtils {

	protected static Logger logger = Logger.getLogger(JDBCUtils.class);

	public static Connection connection = null;
	public static PreparedStatement ps = null;
	public static CallableStatement cs = null;

	public static BasicDataSource dataSource = null;
	private static QueryRunner queryRunner = null;

	public enum DatabaseDriver {
		MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql"), SQLSERVER("net.sourceforge.jtds.jdbc.Driver",
				"jdbc:jtds:sqlserver"), ORACLE("oracle.jdbc.driver.OracleDriver", "jdbc:oracle");

		private String driverClassName;
		private String driverUrl;

		DatabaseDriver(String driverClassName, String driverUrl) {
			// TODO Auto-generated constructor stub
			this.driverClassName = driverClassName;
			this.driverUrl = driverUrl;
		}

		public String getdriverClassName() {
			return driverClassName;
		}

		public void setdriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}

		public String getdriverUrl() {
			return driverUrl;
		}

		public void setdriverUrl(String driverUrl) {
			this.driverUrl = driverUrl;
		}

	}

	/**
	 * getAuthorizationConnection:
	 * 
	 * @author alterhu2020@gmail.com
	 * @param url
	 *            url for jdbc.
	 * @since JDK 1.8
	 *        jdbc:jtds:sqlserver://servername/databasename;useNTLMv2=true;domain=
	 *        testdomain.com;
	 */
	public static void getConnection(DatabaseDriver driver, String domain, String serverName,
			String defaultDatabaseName, String username, String password) {
		if (connection == null) {
			try {
				Class.forName(driver.getdriverClassName());
				String driverurl = driver.getdriverUrl() + "://" + serverName + "/" + defaultDatabaseName;
				String NTLMStr = (StringUtils.isNotEmpty(domain)) ? ";useNTLMv2=true;domain=" + domain : "";
				connection = DriverManager.getConnection(driverurl + NTLMStr, username, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static DataSource getDataSource(Properties properties) {
		if (dataSource == null) {
			try {
				dataSource = BasicDataSourceFactory.createDataSource(properties);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		queryRunner = new QueryRunner(dataSource, false);
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataSource;
	}

	public static DataSource getDataSource(DatabaseDriver driver, String domain, String serverName,
			String defaultDatabaseName, String username, String password) {
		if (dataSource == null) {
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName(driver.getdriverClassName());
			String driverurl = driver.getdriverUrl() + "://" + serverName + "/" + defaultDatabaseName;
			String NTLMStr = (StringUtils.isNotEmpty(domain)) ? ";useNTLMv2=true;domain=" + domain : "";
			dataSource.setUrl(driverurl + NTLMStr);
			dataSource.setUsername(username);
			dataSource.setPassword(password);

			// set the auto commit for transaction
			dataSource.setMaxTotal(100);
			dataSource.setDefaultAutoCommit(true);
		}
		queryRunner = new QueryRunner(dataSource, false);
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataSource;
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
	 * @deprecated Use {@link #query(String, ResultSetHandler, Object...)}
	 *             {@link #update(String, Object...)}
	 *             {@link #insert(String, ResultSetHandler, Object...)}
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
	 * Execute a batch of SQL INSERT, UPDATE, or DELETE queries. 
	 * The Connection is retrieved from the DataSource set in the constructor. 
	 * This Connection must be in auto-commit mode or the update will not be saved. 
	 * @param sql sql statement
	 * @param params the parameters
	 * @return the update rows
	 */
	public static int[] batch(String sql, Object[][] params) {
		int[] resultSize =null;
		synchronized (queryRunner) {
			try {
				resultSize = queryRunner.batch(sql, params);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultSize;
	}

	/**
	 * run a sql query statement
	 * 
	 * @param sql
	 *            the sql statement need to use
	 * @param resultSetHandler
	 *            the result set u want to return 1. if return a array data ：
	 *            ResultSetHandler<List<Object[]>> resultSetHandler = new
	 *            ArrayListHandler(); 2. if return a list<Bean>
	 *            ResultSetHandler<List<Boolean>> resultSetHandler = new
	 *            BeanListHandler<Boolean>(Boolean.class); 3. if return a
	 *            list<Map<bean>> ResultSetHandler<List<Map<String, Object>>>
	 *            resultSetHandler = new MapListHandler(); 4. if return a
	 *            column's list  list data
	 *            ArrayHandler ：将结果集中第一行的数据转化成对象数组。返回值类型：Object[]
	 * 
	 *            ArrayListHandler将结果集中所有的数据转化成List。返回值类型：List<Object[]>
	 * 
	 *            BeanHandler ：将Object中第一行的数据转化成类对象。返回值类型：T
	 * 
	 *            BeanListHandler
	 *            ：将Object中所有的数据转化成List，List中存放的是类对象。返回值类型：List<T>
	 * 
	 *            ColumnListHandler ：将Object中某一列的数据存成List，List中存放的是
	 *            Object对象。返回值类型：List<Object>
	 * 
	 *            KeyedHandler
	 *            ：将Object存成映射，key为某一列对应为Map。Map中存放的是数据。Map<关键字字段值，map<列名,字段值>>返回值类型：Map<Object,Map<String,Object>>
	 * 
	 *            MapHandler
	 *            ：将结果集中第一行的数据存成Map<列名,字段值>映射。返回值类型：Map<String,Object>
	 * 
	 *            MapListHandler
	 *            ：将结果集中所有的数据存成List。List中存放的是Map<列名,字段值>。返回值类型：List<Map<String,Object>>
	 * 
	 *            ScalarHandler ：返回结果集中的第一行的指定列的一个值。返回值类型：Object
	 * @param params
	 *            the sql statement parameters
	 * @return the resultset object
	 */
	public static <T> T query(String sql, ResultSetHandler<T> resultSetHandler, Object... params) {
		T result = null;
		try {
			result = queryRunner.query(sql, resultSetHandler, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static int update(String sql, Object... params) {
		int resultSize = 0;
		synchronized (queryRunner) {
			try {
				resultSize = queryRunner.update(sql, params);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultSize;
	}
	
	public static <T> T insert(String sql, ResultSetHandler<T> resultSetHandler, Object... params) {
		T result = null;
		synchronized (queryRunner) {
			try {
				result = queryRunner.insert(sql, resultSetHandler, params);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	public static <T> T insertBatch(String sql, ResultSetHandler<T> resultSetHandler, Object[][] params) {
		T result = null;
		synchronized (queryRunner) {
			try {
				result = queryRunner.insertBatch(sql, resultSetHandler, params);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * sp need to run for select query,you need to use executeQuery(); for
	 * insert,update,delete you need to user executeUpdate(); for batch job
	 * ,first addBatch(); then executeBatch();//
	 * http://viralpatel.net/blogs/batch-insert-in-java-jdbc/ batch run
	 * 
	 * @param procedure
	 *            the sql statement like: {call db.dbo.spname (?, ?)}
	 * @return CallableStatement prepareCall
	 * @deprecated Use
	 *             {@link #prepareStoreProcedure(String, ResultSetHandler, Object...)}
	 * 
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

	/**
	 * call a store precedure call
	 * 
	 * @param sql
	 *            like sp： {CALL p_wcct_get_office_staff(?)}
	 * @param resultSetHandler
	 *            the resultset type u need to covert
	 * @param params
	 *            the parameter passed to sp
	 * @return the bean or list
	 */
	public static <T> T prepareStoreProcedure(String sql, ResultSetHandler<T> resultSetHandler, Object... params) {
		T result = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			DbUtils.close(connection);
			sql = (sql.toLowerCase().contains("call")) ? sql : "{CALL " + sql + "}";
			stmt = connection.prepareCall(sql);
			queryRunner.fillStatement(stmt, params);
			rs = stmt.executeQuery();
			result = resultSetHandler.handle(rs);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				try {
					DbUtils.close(rs);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} finally {
				try {
					DbUtils.close(stmt);
					DbUtils.close(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		return result;
	}

}

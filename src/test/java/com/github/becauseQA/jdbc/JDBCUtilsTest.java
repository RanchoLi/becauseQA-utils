package com.github.becauseQA.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.github.becauseQA.jdbc.JDBCUtils.DatabaseDriver;

/*
 * see document : http://www.cnblogs.com/wushiqi54719880/archive/2011/06/23/2088022.html
 * 
 */
public class JDBCUtilsTest {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReturnArray() {
		/*
		 * will return the first row data
		 */
		ResultSetHandler<Object[]> resultSetHandler = new ArrayHandler();
		JDBCUtils.getDataSource(DatabaseDriver.MYSQL, null, "192.168.137.88:3306", "employees", "root", "root");
		Object[] prepareSql = JDBCUtils.query("select * from employees limit 2,100", resultSetHandler);

		for (int i = 0; i < prepareSql.length; i++) {
			System.out.println("ArrayHandler first row: "+prepareSql[i]);
		}
	}

	@Test
	public void testReturnArrayList() {

		System.out.println("test the covert to ArrayListHandler ");
		ResultSetHandler<List<Object[]>> resultSetHandler = new ArrayListHandler();
		JDBCUtils.getDataSource(DatabaseDriver.MYSQL, null, "192.168.137.88:3306", "employees", "root", "root");
		List<Object[]> prepareSql = JDBCUtils.query("select * from employees limit 2,100", resultSetHandler);

		for (Object[] employee : prepareSql) {
			System.out.println("ArrayListHandler EMPLOYEE: " + Arrays.toString(employee));
		}
	}

	@Test
	public void testReturnBeanList() {

		System.out.println("test the covert to ArrayListHandler ");
		ResultSetHandler<List<Boolean>> resultSetHandler = new BeanListHandler<Boolean>(Boolean.class);
		JDBCUtils.getDataSource(DatabaseDriver.MYSQL, null, "192.168.137.88:3306", "employees", "root", "root");
		List<Boolean> prepareSql = JDBCUtils.query("select * from employees limit 2,100", resultSetHandler);

		for (Boolean employee : prepareSql) {
			System.out.println("BeanListHandler EMPLOYEE: " + employee);
		}
	}

	@Test
	public void testMapHandler() {
		/*
		 * MapHandler ：将结果集中第一行的数据存成Map<列名,字段值>映射。返回值类型：Map<String,Object>
		 */
		System.out.println("test the covert to FIRST ROW DATA IN MAP ");
		ResultSetHandler<Map<String, Object>> resultSetHandler = new MapHandler();
		JDBCUtils.getDataSource(DatabaseDriver.MYSQL, null, "192.168.137.88:3306", "employees", "root", "root");
		Map<String, Object> firstrowData = JDBCUtils.query("select * from employees limit 2,100", resultSetHandler);

		for (String key : firstrowData.keySet()) {
			System.out.println("MapHandler EMPLOYEE: " + firstrowData.get(key));
		}
	}

	@Test
	public void testReturnMapList() {

		System.out.println("test the covert to mapList ");
		ResultSetHandler<List<Map<String, Object>>> resultSetHandler = new MapListHandler();
		JDBCUtils.getDataSource(DatabaseDriver.MYSQL, null, "192.168.137.88:3306", "employees", "root", "root");
		List<Map<String, Object>> prepareSql = JDBCUtils.query("select * from employees limit 2,100", resultSetHandler);

		for (Map<String, Object> employee : prepareSql) {
			System.out.println("MapListHandler EMPLOYEE: " + employee);
		}
	}

	@Test
	public void testKeyedHandler() {
		/*
		 * covert one column 's all data return
		 */
		System.out.println("test the covert to map ");
		ResultSetHandler<Map<Object,Map<String, Object>>> resultSetHandler = new KeyedHandler<>("first_name");
		JDBCUtils.getDataSource(DatabaseDriver.MYSQL, null, "192.168.137.88:3306", "employees", "root", "root");
		Map<Object, Map<String, Object>> firstrowData = JDBCUtils.query("select * from employees limit 2,100", resultSetHandler);

		for (Object key : firstrowData.keySet()) {
			System.out.println("KeyedHandler EMPLOYEE: " + firstrowData.get(key));
		}
	}

	@Test
	public void testColumnHandler() {
		/*
		 * covert one column 's all data return
		 */
		System.out.println("test the covert to FIRST ROW DATA IN MAP ");
		ResultSetHandler<List<Object>> resultSetHandler = new ColumnListHandler<>(3);
		JDBCUtils.getDataSource(DatabaseDriver.MYSQL, null, "192.168.137.88:3306", "employees", "root", "root");
		List<Object> thirdColumnData = JDBCUtils.query("select * from employees limit 2,100", resultSetHandler);

		for (Object key : thirdColumnData) {
			System.out.println("ColumnListHandler 3 is: " + key);
		}
	}

	@SuppressWarnings("deprecation")
	@Ignore
	@Test
	public void testGetConnectionStringStringStringString() {
		/*
		 * String ssopath =
		 * DllUtils.class.getClassLoader().getResource("jtds/x64/SSO").getPath()
		 * ; String path =new File(ssopath ).getAbsolutePath();
		 * dllUtils.loadDll("ntlmauth", path);
		 */
		JDBCUtils.getConnection(DatabaseDriver.SQLSERVER, "domainame.com", "servername", "dbname", null, null);
		PreparedStatement selectRecord = JDBCUtils.prepareSql("select top 10  * from db");
		ResultSet resultSet = null;
		try {
			resultSet = selectRecord.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while (resultSet.next()) {

				String row = resultSet.getString(3);
				System.out.println(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

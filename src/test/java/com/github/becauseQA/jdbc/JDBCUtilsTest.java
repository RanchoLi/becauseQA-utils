package com.github.becauseQA.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.becauseQA.dll.DllUtils;
import com.github.becauseQA.jdbc.JDBCUtils.DatabaseDriver;

public class JDBCUtilsTest {

	@Before
	public void setUp() throws Exception {
		new JDBCUtils();
		new DllUtils();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetConnectionStringStringStringString() {
		/*String ssopath = DllUtils.class.getClassLoader().getResource("jtds/x64/SSO").getPath();
		String path =new File(ssopath ).getAbsolutePath();
		dllUtils.loadDll("ntlmauth", path);
		*/
		JDBCUtils.getNTLMConnection(DatabaseDriver.SQLSERVER,"nextestate.com","GDCQA4-SQL01","QA4");		
		PreparedStatement selectRecord = JDBCUtils.prepareSql("select top 10  * from GDFN..program");
		ResultSet resultSet=null;
		try {
			resultSet = selectRecord.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while(resultSet.next()){
			
				String row = resultSet.getString(3);
				System.out.println(row);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	

}

package com.github.becausetesting.jdbc;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.becausetesting.dll.DllUtils;

public class JDBCUtilsTest {

	private SQLServerUtils jdbcUtils;
	private DllUtils dllUtils;

	@Before
	public void setUp() throws Exception {
		jdbcUtils = new SQLServerUtils();
		dllUtils = new DllUtils();
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
		jdbcUtils.getAuthorizationConnection("jdbc:jtds:sqlserver://GDCQA4-SQL01/QA4;useNTLMv2=true;domain=nextestate.com;");		
		ResultSet selectRecord = jdbcUtils.selectRecord("select top 10  * from GDFN..program");
		try {
			while(selectRecord.next()){
			
				String row = selectRecord.getString(3);
				System.out.println(row);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	

}

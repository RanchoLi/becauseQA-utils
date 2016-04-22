package com.github.becausetesting.testcasetools;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.becausetesting.testcasetools.TestRailAPI.CASETYPECODE;
import com.github.becausetesting.testcasetools.TestRailAPI.PRIORITYCODE;

public class TestRailAPITest {

	private TestRailAPI api;
	private String projectname = "Green Dot Network";
	private String testplanname = "Green Dot Network-Web-Sample-1";
	private String testsuitename = "Disbursements";
	private String testsectioname1 = "Uber MC Send Link API";
	private String testsectioname2 = "Authentication and Connectivity to MasterCard Send API";
	private String casename = "TC04 - Verify Fund accounts failed if input destinationuri is incorrect";

	@Before
	public void setUp() throws Exception {
		api = new TestRailAPI();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetProjectid() {
		boolean projectByName = api.getProjectByName(projectname);
		System.out.println(projectByName);
		api.getLatestMilestone();
		api.getUserID();
		// api.closeTestPlan(testplanname);
		// api.createTestPlan(testplanname+"test1");
		// api.closeTestPlan(testplanname+"test1");
		api.getTestSuite(testsuitename);
		api.getTestSection(testsectioname1, testsectioname2);
		System.out.println(api.getSectionid());
		api.getTestCase(casename);
		api.createTestCase("1", CASETYPECODE.AUTOMATED, PRIORITYCODE.SHOULD_TEST, null, null);

	}

}

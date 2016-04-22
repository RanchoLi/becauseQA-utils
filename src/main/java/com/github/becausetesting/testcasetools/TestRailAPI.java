package com.github.becausetesting.testcasetools;

import java.io.IOException;
import java.lang.Character.UnicodeScript;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.github.becausetesting.http.HttpUtils;
import com.github.becausetesting.http.HttpsCert;
import com.github.becausetesting.json.JSONUtils;
import com.github.becausetesting.time.TimeUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @ClassName: TestRailAPI
 * @Description: TODO
 * @author alterhu2020@gmail.com
 * @date Aug 3, 2014 9:26:51 AM
 * 
 */

public class TestRailAPI {

	// the priority code in test rail
	public enum PRIORITYCODE {
		URGENT_TEST(5), MUST_TEST(4), SHOULD_TEST(3), TEST_IF_TIME(2), DONOT_TEST(1);

		private int prioritycode;
		PRIORITYCODE(int prioritycode) {
			this.prioritycode = prioritycode;
		}

	}

	// the case type in test rail
	public enum CASETYPECODE {
		AUTOMATED(1), FUNCTIONALITY(2), PERFORMANCE(3), REGRESSION(4), USABILITY(5), OTHER(6), MANUAL_TEST(7);

		private int typecode;
		CASETYPECODE(int typecode) {
			this.typecode = typecode;
		}

	}

	// the result code in test rail
		public enum RESULTCODE {
			PASSED(1,"Automated Passed"), BLOCKED(2,"Automated Blocked"), UNTESTED(3,"Automated Untested"), RETEST(4,"Automated Retest"), FAILED(5,"Automated Failed");

			private int resultcode;
			private String result;
			RESULTCODE(int resultcode,String result) {
				this.resultcode = resultcode;
				this.result=result;
			}

		}

		
	private String base_Url = null;
	private String user = null;
	private String password = null;
	public String apikey = "";

	public long userid = 0;
	public long projectid = 0;
	public long milestoneid = 0;
	public long planid = 0;
	public long runid = 0;
	public String entryid = null;
	public long suiteid = 0;
	public long sectionid = 0;
	private long caseid = 0;

	private HttpUtils httpUtils;

	public TestRailAPI(String baseurl, String username, String password) {
		this.setBase_Url(baseurl);
		this.setUser(username);
		this.setPassword(password);
		if (!getBase_Url().endsWith("/")) {
			setBase_Url(getBase_Url() + "/");
		}
		setBase_Url(getBase_Url() + "index.php?/api/v2/");

	}

	public TestRailAPI() {
		this.setBase_Url("https://gdcqatestrail01/testrail/index.php?/api/v2/");
		this.setUser("qa_test_automation@greendotcorp.com");
		this.setPassword("qa_test_automation");
	}

	/**********************************
	 * Http Request Method
	 **************************************************/
	private Object getRequest(String url) {
		HttpsCert.ignoreCert();
		httpUtils = new HttpUtils();
		URL parseurl;
		Object responsetext = null;

		try {
			parseurl = new URL(this.getBase_Url() + url);
			httpUtils.getConnection(parseurl, "GET");
			Map<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "application/json");
			httpUtils.setAuthorizationHeader(this.getUser(), this.getPassword());
			httpUtils.setHeaders(headers);
			String response = httpUtils.getResponse();
			if (response != "") {
				responsetext = new JSONUtils().toJsonElement(response);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responsetext;
	}

	private Object postRequest(String url, Object data) {
		HttpsCert.ignoreCert();
		httpUtils = new HttpUtils();
		URL parseurl = null;
		Object responsetext = null;

		try {
			parseurl = new URL(this.getBase_Url() + url);
			httpUtils.getConnection(parseurl, "GET");
			Map<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "application/json");
			httpUtils.setAuthorizationHeader(this.getUser(), this.getPassword());
			httpUtils.setHeaders(headers);
			httpUtils.postJsonData(data);
			String response = httpUtils.getResponse();
			if (response != "") {
				responsetext = new JSONUtils().toJsonElement(response);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responsetext;
	}

	/**********************************
	 * Http Request Method
	 **************************************************/
	/**
	 * @Title: getProjectByName @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         projectname @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */

	public boolean getProjectByName(String projectname) {
		boolean findproject = false;
		String url = "get_projects";
		Object response = getRequest(url);
		if (response instanceof JsonArray) {
			JsonArray projects = (JsonArray) response;
			for (int k = 0; k < projects.size(); k++) {
				JsonObject foundproject = (JsonObject) projects.get(k);
				String project = foundproject.get("name").getAsString();
				if (project.equalsIgnoreCase(projectname.trim())) {
					this.projectid = foundproject.get("id").getAsLong();
					findproject = true;
					break;
				}
			}

		}
		return findproject;
	}

	/**
	 * @Title: getPriorities @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return List
	 *         <JsonObject> return type @throws
	 */

	public List<JsonObject> getPriorities() {
		List<JsonObject> prioritieslist = new ArrayList<JsonObject>();
		JsonArray priorities = (JsonArray) getRequest("get_priorities");
		for (int k = 0; k < priorities.size(); k++) {
			prioritieslist.add((JsonObject) priorities.get(k));
		}
		return prioritieslist;
	}

	/**
	 * @Title: getProjectConfiguration @Description: TODO @author
	 *         alterhu2020@gmail.com @param @throws
	 *         MalformedURLException @param @throws IOException @return void
	 *         return type @throws
	 */

	public void getProjectConfiguration() {
		/*
		 * String confurl = String.format("get_configs/%d", this.projectid);
		 * JsonArray configs = (JsonArray) getRequest(confurl); testRailsConfigs
		 * = new ArrayList<JsonObject>(); for (int k = 0; k < configs.size();
		 * k++) { JsonObject configroup = (JsonObject) configs.get(k);
		 * testRailsConfigs.add(configroup); }
		 * 
		 */
	}

	/**
	 * @Title: createProject @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param projectname @param @param
	 *         description @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long createProject(String projectname, String description) {
		String newurl = "add_project";
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", projectname);
		postData.put("announcement", description);
		postData.put("show_announcement", true);
		JsonObject newproject = (JsonObject) postRequest(newurl, postData);
		this.projectid = newproject.get("id").getAsLong();
		// System.out.println("Create the test rail new project:"+projectname);

		return this.projectid;

	}

	/**
	 * @Title: updateProject @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param projectname @param @param
	 *         description @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long updateProject(String projectname, String description) {

		String newurl = String.format("update_project/%d", this.projectid);
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", projectname);
		postData.put("announcement", description);
		postData.put("show_announcement", true);
		JsonObject newproject = (JsonObject) postRequest(newurl, postData);
		this.projectid = newproject.get("id").getAsLong();
		// System.out.println("Update the test rail project:"+projectname);

		return this.projectid;

	}

	/**
	 * @Title: getUserID @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */

	public boolean getUserID() {
		boolean finduser = false;
		String userurl = "get_users";
		JsonArray userinfo = (JsonArray) getRequest(userurl);
		for (int k = 0; k < userinfo.size(); k++) {
			JsonObject singleuser = (JsonObject) userinfo.get(k);
			if (singleuser.get("name").getAsString().toLowerCase().contains(this.getUser().toLowerCase()) || singleuser
					.get("email").getAsString().toLowerCase().contains(this.getUser().toLowerCase().trim())) {
				this.userid = singleuser.get("id").getAsLong();
				finduser = true;
				break;
			}
		}

		return finduser;
	}

	/**
	 * @Title: getMilestone @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         milestonename @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */

	public boolean getMilestone(String milestonename) {
		boolean findmilestone = false;
		String url = String.format("get_milestones/%d", this.projectid);
		JsonArray milestones = (JsonArray) getRequest(url);
		for (int k = 0; k < milestones.size(); k++) {
			JsonObject milestone = (JsonObject) milestones.get(k);
			if (milestone.get("name").getAsString().equalsIgnoreCase(milestonename.trim())
					&& (!milestone.get("is_completed").getAsBoolean())) {
				this.milestoneid = milestone.get("id").getAsLong();
				findmilestone = true;
				break;
			}
		}
		return findmilestone;
	}

	/**
	 * @Title: getMilestone @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         milestonename @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */

	public boolean getLatestMilestone() {
		boolean findmilestone = false;
		String url = String.format("get_milestones/%d", this.projectid);
		JsonArray milestones = (JsonArray) getRequest(url);
		for (int k = 0; k < milestones.size(); k++) {
			JsonObject milestone = (JsonObject) milestones.get(k);
			if (!milestone.get("is_completed").getAsBoolean()) {
				this.milestoneid = milestone.get("id").getAsLong();
				findmilestone = true;
				break;
			}
		}
		return findmilestone;
	}

	/**
	 * @Title: createMilestone @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param milestonename @param @param
	 *         parameters @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long createMilestone(String milestonename, String description, String duedate) {

		String newmilestone = String.format("add_milestone/%d", this.projectid);
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", milestonename);
		postData.put("description", description);
		long finalseconds;
		// this parameter is unix timestamp
		try {
			finalseconds = new SimpleDateFormat("yyyy-MM-dd").parse(duedate).getTime();
			postData.put("due_on", finalseconds);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonObject milestone = (JsonObject) postRequest(newmilestone, postData);
		this.milestoneid = milestone.get("id").getAsLong();
		// return true;
		return this.milestoneid;
	}

	/**
	 * @Title: updateMilestone @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param milestonename @param @param
	 *         description @param @param duedate @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long updateMilestone(String milestonename, String description, String duedate) {

		String newmilestone = String.format("update_milestone/%d", this.milestoneid);
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", milestonename);
		postData.put("description", description);
		long finalseconds;
		try {
			finalseconds = new SimpleDateFormat("yyyy-MM-dd").parse(duedate).getTime();
			postData.put("due_on", finalseconds);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonObject milestone = (JsonObject) postRequest(newmilestone, postData);
		this.milestoneid = milestone.get("id").getAsLong();
		// return true;
		return this.milestoneid;
	}

	/**
	 * @Title: getTestPlan @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         planname @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */

	public boolean getTestPlan(String planname) {
		boolean iscreated = false;
		String url = String.format("get_plans/%s", this.projectid);
		JsonArray plans = (JsonArray) getRequest(url);
		for (int k = 0; k < plans.size(); k++) {
			JsonObject plan = (JsonObject) plans.get(k);
			if (plan.get("name").getAsString().equalsIgnoreCase(planname.trim())) {
				this.planid = plan.get("id").getAsLong();
				iscreated = true;
				break;
			}
		}
		return iscreated;
	}

	public void getTestPlan(long planid) {

		String url = String.format("get_plan/%s", planid);
		JsonObject plans = (JsonObject) getRequest(url);
		System.out.println(plans);
		// return iscreated;
	}

	public boolean getPlanEntry(String testsuitname) {
		boolean iscreated = false;
		boolean testSuite = getTestSuite(testsuitname);
		if (testSuite) {
			String url = String.format("get_plan/%s", this.planid);
			JsonObject plan = (JsonObject) getRequest(url);
			JsonArray entries = (JsonArray) plan.get("entries");
			for (int k = 0; k < entries.size(); k++) {
				JsonObject runs = (JsonObject) entries.get(k);

				JsonArray runarray = (JsonArray) runs.get("runs");
				for (int j = 0; j < runarray.size(); j++) {
					JsonObject run = (JsonObject) runarray.get(j);
					long findsuiteid = run.get("suite_id").getAsLong();
					if (findsuiteid == this.suiteid) {
						iscreated = true;
						this.runid = run.get("id").getAsLong();
						break;
					}
				}
			}
		}
		return iscreated;
	}

	public boolean getPlanEntryName(String entryname) {
		boolean iscreated = false;
		// boolean testSuite = getTestSuite(testsuitname);
		String url = String.format("get_plan/%s", this.planid);
		JsonObject plan = (JsonObject) getRequest(url);
		JsonArray entries = (JsonArray) plan.get("entries");
		for (int k = 0; k < entries.size(); k++) {
			JsonObject runs = (JsonObject) entries.get(k);

			JsonArray runarray = (JsonArray) runs.get("runs");
			for (int j = 0; j < runarray.size(); j++) {
				JsonObject run = (JsonObject) runarray.get(j);
				String findentryname = run.get("name").getAsString();
				if (findentryname.equals(entryname)) {
					iscreated = true;
					this.runid = run.get("id").getAsLong();
					this.entryid = run.get("entry_id").getAsString();
					break;
				}
			}
		}

		return iscreated;
	}

	public boolean getPlanEntry(long suiteid) {
		boolean iscreated = false;

		String url = String.format("get_plan/%s", this.planid);
		JsonObject plan = (JsonObject) getRequest(url);
		JsonArray entries = (JsonArray) plan.get("entries");
		for (int k = 0; k < entries.size(); k++) {
			JsonObject runs = (JsonObject) entries.get(k);

			JsonArray runarray = (JsonArray) runs.get("runs");
			for (int j = 0; j < runarray.size(); j++) {
				JsonObject run = (JsonObject) runarray.get(j);
				long findsuiteid = run.get("suite_id").getAsLong();
				if (findsuiteid == suiteid) {
					iscreated = true;
					this.runid = run.get("id").getAsLong();
					this.entryid = run.get("entry_id").getAsString();
					break;
				}
			}
		}

		return iscreated;
	}

	public boolean addPlanEntry(String testsuitname) {
		boolean iscreated = false;
		boolean testSuite = getTestSuite(testsuitname);
		if (testSuite) {
			String url = String.format("add_plan_entry/%s", this.planid);

			HashMap<String, Object> postData = new HashMap<String, Object>();
			postData.put("suite_id", this.suiteid);
			postData.put("name", testsuitname);
			postData.put("include_all", false);
			JsonObject planruns = (JsonObject) postRequest(url, postData);

			this.runid = planruns.get("id").getAsLong();
			iscreated = true;
		}
		return iscreated;
	}

	public boolean addPlanEntry(long suiteid) {
		boolean iscreated = false;
		String testSuite = getTestSuite(suiteid);
		String url = String.format("add_plan_entry/%s", this.planid);

		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("suite_id", suiteid);
		postData.put("name", testSuite);
		postData.put("include_all", false);
		JsonObject planruns = (JsonObject) postRequest(url, postData);

		this.runid = planruns.get("id").getAsLong();
		iscreated = true;

		return iscreated;
	}

	public boolean addPlanEntry(long suiteid, List<Integer> configs) {
		boolean iscreated = false;
		String testSuite = getTestSuite(suiteid);
		String url = String.format("add_plan_entry/%s", this.planid);

		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("suite_id", suiteid);
		postData.put("name", testSuite);
		postData.put("assignedto_id", this.userid);
		postData.put("include_all", true);
		/*
		 * postData.put("config_ids", configs); List<Long> caselist=new
		 * ArrayList<Long>(); caselist.add(caseid); postData.put("case_ids",
		 * caselist);
		 * 
		 * 
		 * 
		 * JsonObject data=new JsonObject();
		 * 
		 * data.put("include_all", true); // data.put("case_ids", caselist);
		 * data.put("config_ids", configs);
		 * 
		 * List<JsonObject> runlist=new ArrayList<JsonObject>();
		 * runlist.add(data); postData.put("runs", runlist);
		 */

		JsonObject planruns = (JsonObject) postRequest(url, postData);

		JsonArray runarray = (JsonArray) planruns.get("runs");
		for (int j = 0; j < runarray.size(); j++) {
			JsonObject run = (JsonObject) runarray.get(j);
			long findsuiteid = run.get("suite_id").getAsLong();
			if (findsuiteid == suiteid) {
				iscreated = true;
				this.runid = run.get("id").getAsLong();
				this.entryid = run.get("entry_id").getAsString();
				break;
			}
		}

		iscreated = true;

		return iscreated;
	}

	public boolean addPlanEntryforName(long suiteid, String name, List<Integer> configs) {
		boolean iscreated = false;
		// String testSuite = getTestSuite(suiteid);
		String url = String.format("add_plan_entry/%s", this.planid);

		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("suite_id", suiteid);
		postData.put("name", name);
		postData.put("assignedto_id", this.userid);
		postData.put("include_all", true);
		/*
		 * postData.put("config_ids", configs); List<Long> caselist=new
		 * ArrayList<Long>(); caselist.add(caseid); postData.put("case_ids",
		 * caselist);
		 * 
		 * 
		 * 
		 * JsonObject data=new JsonObject();
		 * 
		 * data.put("include_all", true); // data.put("case_ids", caselist);
		 * data.put("config_ids", configs);
		 * 
		 * List<JsonObject> runlist=new ArrayList<JsonObject>();
		 * runlist.add(data); postData.put("runs", runlist);
		 */

		JsonObject planruns = (JsonObject) postRequest(url, postData);

		JsonArray runarray = (JsonArray) planruns.get("runs");
		for (int j = 0; j < runarray.size(); j++) {
			JsonObject run = (JsonObject) runarray.get(j);
			long findsuiteid = run.get("suite_id").getAsLong();
			if (findsuiteid == suiteid) {
				iscreated = true;
				this.runid = run.get("id").getAsLong();
				this.entryid = run.get("entry_id").getAsString();
				break;
			}
		}

		iscreated = true;

		return iscreated;
	}

	public boolean updatePlanEntryForCase(long suiteid, List<Long> caselist) {
		boolean isupdate = false;
		String url = String.format("update_plan_entry/%s/%s", this.planid, this.entryid);

		HashMap<String, Object> postData = new HashMap<String, Object>();
		// postData.put("assignedto_id", this.userid);
		postData.put("include_all", false);
		postData.put("assignedto_id", this.userid);

		postData.put("case_ids", caselist);

		JsonObject planruns = (JsonObject) postRequest(url, postData);

		JsonArray runarray = (JsonArray) planruns.get("runs");
		for (int j = 0; j < runarray.size(); j++) {
			JsonObject run = (JsonObject) runarray.get(j);
			long findsuiteid = run.get("suite_id").getAsLong();
			if (findsuiteid == suiteid) {
				isupdate = true;
				this.runid = run.get("id").getAsLong();
				break;
			}
		}

		return isupdate;
	}

	/**
	 * @Title: createTestPlan @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         planname @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long createTestPlan(String planname) {

		String planurl = String.format("add_plan/%s", this.projectid);
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", planname);
		postData.put("milestone_id", this.milestoneid);
		JsonObject newplan = (JsonObject) postRequest(planurl, postData);
		this.planid = newplan.get("id").getAsLong();
		// return true;
		return this.planid;

	}

	/**
	 * @Title: updateTestPlan @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         planname @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long updateTestPlan(String planname) {

		String planurl = String.format("update_plan/%d", this.planid);
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", planname);
		postData.put("milestone_id", this.milestoneid);
		postData.put("is_completed", true);
		JsonObject newplan = (JsonObject) postRequest(planurl, postData);
		this.planid = newplan.get("id").getAsLong();
		// return true;
		return this.planid;

	}

	/**
	 * @Title: closeTestPlan @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         planname @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */

	public void closeTestPlan(String planname) {
		String url = String.format("get_plans/%d", this.projectid);
		JsonArray plans = (JsonArray) getRequest(url);

		for (int k = 0; k < plans.size(); k++) {
			JsonObject plan = (JsonObject) plans.get(k);
			if (plan.get("name").getAsString().toLowerCase().contains(planname.toLowerCase().trim())) {
				// &&((Boolean)plan.get("is_completed"))){
				String closeurl = String.format("close_plan/%s", plan.get("id").getAsString());
				postRequest(closeurl, new JsonObject()).toString();
				// System.out.println(response);
				break;
			}
		}
		// return false;
	}

	/**
	 * @Title: getTestRun @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         runame @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */

	public boolean getTestRun(String runame) {
		boolean findrun = false;
		String url = String.format("get_plan/%s", this.planid);
		JsonObject plan = (JsonObject) getRequest(url);
		JsonArray entries = (JsonArray) plan.get("entries");
		for (int k = 0; k < entries.size(); k++) {
			JsonObject runs = (JsonObject) entries.get(k);
			String currententry = runs.get("id").getAsString();
			JsonArray onerun = (JsonArray) runs.get("runs");
			for (int i = 0; i < onerun.size(); i++) {
				JsonObject run = (JsonObject) onerun.get(i);
				if (run.get("name").getAsString().equalsIgnoreCase(runame.trim())) {

					this.entryid = currententry;
					this.runid = run.get("id").getAsLong();
					findrun = true;
					break;
				}
			}

		}
		return findrun;
	}

	/**
	 * @Title: createTestPlanRun @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long createTestPlanRunForSuite() {
		String runurl = String.format("add_plan_entry/%d", this.planid);
		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("suite_id", this.suiteid);

		arguments.put("assignedto_id", userid);
		arguments.put("include_all", true);

		JsonObject planruns = (JsonObject) postRequest(runurl, arguments);

		this.runid = planruns.get("id").getAsLong();
		// this.entryid=planruns.get("id");
		return this.runid;

	}

	public long createTestPlanRunForSection(String runame) {
		String runurl = String.format("add_plan_entry/%d", this.planid);
		Map<String, Object> arguments = new HashMap<String, Object>();

		arguments.put("name", runame);
		arguments.put("suite_id", this.suiteid);

		arguments.put("assignedto_id", userid);
		arguments.put("include_all", false);
		long[] cases = getTestCaseIds(this.sectionid);

		arguments.put("case_ids", cases);

		JsonObject planruns = (JsonObject) postRequest(runurl, arguments);

		this.runid = planruns.get("id").getAsLong();
		// this.entryid=planruns.get("id");
		return this.runid;

	}

	/**
	 * @Title: updateTestPlanRunForTestSection @Description: This function is a
	 *         bug by now ,as metioned in offical document ,so you should use
	 *         the add_plan_entry intead it
	 *         http://forum.gurock.com/topic/1584/adding-a-new-test-run-
	 *         configuration-to-an-existing-entry/ it's a to-do list for
	 *         testrail team by 2014/8/3 @deprecated @author
	 *         alterhu2020@gmail.com @param @param runame @param @param
	 *         sectionid @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */

	public boolean updateTestPlanRunForTestSection(String runame, long sectionid) {

		// getTestRun(runame);
		String runurl = String.format("update_plan_entry/%d/%s", this.planid, this.entryid);
		Map<String, Object> arguments = new HashMap<String, Object>();
		// arguments.put("suite_id", this.suiteid);
		arguments.put("name", runame);
		arguments.put("assignedto_id", this.userid);
		arguments.put("include_all", false);

		long[] cases = getTestCaseIds(sectionid);

		arguments.put("case_ids", cases);

		JsonObject planruns = (JsonObject) postRequest(runurl, arguments);
		if (planruns != null) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: updateTestPlanRunForCases @Description: This function is a bug by
	 *         now ,as metioned in offical document ,so you should use the
	 *         add_plan_entry intead it
	 *         http://forum.gurock.com/topic/1584/adding-a-new-test-run-
	 *         configuration-to-an-existing-entry/ it's a to-do list for
	 *         testrail team by 2014/8/3 @deprecated @author
	 *         alterhu2020@gmail.com @param @param
	 *         caseids @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */

	public boolean updateTestPlanRunForCases(long[] caseids) {

		// getTestRun(runame);
		String runurl = String.format("update_plan_entry/%d/%d", this.planid, this.entryid);
		Map<String, Object> arguments = new HashMap<String, Object>();
		// arguments.put("suite_id", this.suiteid);

		// arguments.put("assignedto_id", userid);
		arguments.put("include_all", false);

		arguments.put("case_ids", caseids);

		JsonObject planruns = (JsonObject) postRequest(runurl, arguments);
		if (planruns != null) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: getTestsInRun @Description: TODO @author
	 *         alterhu2020@gmail.com @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */

	public boolean getTestsInRun() {
		boolean hastests = false;
		String url = String.format("get_tests/%d", this.runid);
		JsonArray tests = (JsonArray) getRequest(url);
		if (tests.size() > 0) {
			hastests = true;
		}
		return hastests;
	}

	/**
	 * @Title: getTestSuite @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         suitename @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */

	public boolean getTestSuite(String suitename) {
		boolean iscreated = false;
		String url = String.format("get_suites/%s", this.projectid);
		JsonArray suites = (JsonArray) getRequest(url);
		for (int k = 0; k < suites.size(); k++) {
			JsonObject suite = (JsonObject) suites.get(k);
			if (suite.get("name").getAsString().equalsIgnoreCase(suitename.trim())) {
				this.suiteid = suite.get("id").getAsLong();
				iscreated = true;
				break;
			}
		}
		return iscreated;
	}

	/**
	 * @Title: getTestSuite @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         suitename @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */

	public String getTestSuite(long suiteid) {
		String suitename = null;
		String url = String.format("get_suites/%s", this.projectid);
		JsonArray suites = (JsonArray) getRequest(url);
		for (int k = 0; k < suites.size(); k++) {
			JsonObject suite = (JsonObject) suites.get(k);
			long findsuiteid = suite.get("id").getAsLong();
			if (findsuiteid == suiteid) {
				suitename = suite.get("name").getAsString();
				break;
			}
		}
		return suitename;
	}

	/**
	 * @param caseid
	 * @return
	 */
	public long getTestSuiteidByTestCaseId(long caseid) {
		long suiteid = 0;
		String url = String.format("get_case/%d", caseid);
		JsonObject caseobject = (JsonObject) getRequest(url);
		if (caseobject != null) {
			long newcaseid = caseobject.get("suite_id").getAsLong();
			if (newcaseid != 0l) {
				suiteid = newcaseid;
			}
		}
		return suiteid;

	}

	/**
	 * @Title: createTestSuite @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param suitename @param @param
	 *         description @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long createTestSuite(String suitename, String description) {

		String newsuiteurl = String.format("add_suite/%s", this.projectid);
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", suitename);
		postData.put("description", description);

		JsonObject newsuite = (JsonObject) postRequest(newsuiteurl, postData);
		this.suiteid = newsuite.get("id").getAsLong();

		return this.suiteid;
	}

	/**
	 * @Title: updateTestSuite @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param suitename @param @param
	 *         description @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long updateTestSuite(String suitename, String description) {

		String newsuiteurl = String.format("update_suite/%d", this.suiteid);
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", suitename);
		postData.put("description", description);

		JsonObject newsuite = (JsonObject) postRequest(newsuiteurl, postData);
		this.suiteid = newsuite.get("id").getAsLong();

		return this.suiteid;
	}

	/**
	 * @Title: deleteTestSuite @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param suitename @param @param
	 *         description @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long deleteTestSuite(String suitename) {

		getTestSuite(suitename);
		String newsuiteurl = String.format("delete_suite/%d", this.suiteid);

		JsonObject newsuite = (JsonObject) postRequest(newsuiteurl, null);
		this.suiteid = newsuite.get("id").getAsLong();

		return this.suiteid;
	}

	/**
	 * 
	 * /* without the parent section ,it's at the suite
	 */
	/**
	 * @Title: createTestSection @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         sectionname @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long createTestSection(String... sectionnames) {
		// parse the section strin
		int sectioncount = 0;
		for (String sectionname : sectionnames) {
			String newsectionurl = String.format("add_section/%s", this.projectid);
			HashMap<String, Object> postData = new HashMap<String, Object>();
			postData.put("name", sectionnames);
			postData.put("suite_id", this.suiteid);
			if (sectioncount > 1) {
				postData.put("parent_id", this.sectionid);
			}
			JsonObject newsection = (JsonObject) postRequest(newsectionurl, postData);
			this.sectionid = newsection.get("id").getAsLong();
			sectioncount += 1;
		}
		return this.sectionid;
	}
	/*
	 * @Title: getTestSection @Description: TODO @author
	 * alterhu2020@gmail.com @param @param
	 * sectionname @param @return @param @throws
	 * MalformedURLException @param @throws IOException @return boolean return
	 * type @throws
	 */

	public boolean getTestSection(String... sectionnames) {
		boolean iscreated = false;
		String url = String.format("get_sections/%s&suite_id=%s", this.projectid, this.suiteid);
		JsonArray sections = (JsonArray) getRequest(url);
		String parentid = null;
		String nextparentid = null;

		int sectioncounts = 0;
		for (String sectioname : sectionnames) {
			for (int k = 0; k < sections.size(); k++) {
				JsonObject section = (JsonObject) sections.get(k);
				String findsectioname = section.get("name").getAsString();
				this.sectionid = section.get("id").getAsLong();
				nextparentid = Long.toString(this.sectionid);
				if (sectioncounts > 1) {
					parentid = section.get("parent_id").getAsString();
					if (findsectioname.equalsIgnoreCase(sectioname.trim()) && parentid.equalsIgnoreCase(nextparentid)) {
						break;
					}
				} else {
					if (findsectioname.equalsIgnoreCase(sectioname.trim())) {
						break;
					}
				}
			}
			sectioncounts += 1;
		}
		return iscreated;

	}

	/**
	 * @Title: updateTestSection @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         sectionname @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long updateTestSection(String sectionname) {
		String newsectionurl = String.format("update_section/%d", this.sectionid);
		HashMap<String, Object> postData = new HashMap<String, Object>();
		postData.put("name", sectionname);

		JsonObject newsection = (JsonObject) postRequest(newsectionurl, postData);
		this.sectionid = newsection.get("id").getAsLong();
		// return true;

		return this.sectionid;
	}

	/**
	 * @Title: getCaseList @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param
	 *         sectionid @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long[]
	 *         return type @throws
	 */

	public long[] getTestCaseIds(long sectionid) {
		String url = String.format("get_cases/%s&suite_id=%s&section_id=%d", this.projectid, this.suiteid, sectionid);
		JsonArray cases = (JsonArray) getRequest(url);

		long[] caseids = new long[cases.size()];
		for (int caseindex = 0; caseindex < cases.size(); caseindex++) {
			JsonObject currentcase = (JsonObject) cases.get(caseindex);
			caseids[caseindex] = currentcase.get("id").getAsLong();
		}

		return caseids;

	}

	public JsonArray getTestCaseObject(long sectionid) {
		String url = String.format("get_cases/%s&suite_id=%s&section_id=%d", this.projectid, this.suiteid, sectionid);
		JsonArray cases = (JsonArray) getRequest(url);
		return cases;

	}

	public boolean getTestCase(long caseid) {
		boolean findcase = false;
		String url = String.format("get_case/%d", caseid);
		JsonObject caseobject = (JsonObject) getRequest(url);

		if (caseobject != null) {
			long newcaseid = caseobject.get("id").getAsLong();
			if (newcaseid != 0l) {
				findcase = true;
			}
		}
		return findcase;

	}

	/**
	 * @Title: getTestCase @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param casename @param @param
	 *         sectionid @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long getTestCase(String casename, long sectionid) {
		long caseid = 0;
		String url = String.format("get_cases/%s&suite_id=%s&section_id=%d", this.projectid, this.suiteid, sectionid);
		JsonArray cases = (JsonArray) getRequest(url);
		for (int caseindex = 0; caseindex < cases.size(); caseindex++) {
			JsonObject currentcase = (JsonObject) cases.get(caseindex);
			if (currentcase.get("title").getAsString().equalsIgnoreCase(casename.trim())) {
				caseid = currentcase.get("id").getAsLong();
				break;
			}
		}
		return caseid;

	}

	/**
	 * @Title: getTestCase @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param casename @param @param
	 *         sectionid @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	public long getTestCase(String casename) {
		long caseid = 0;
		String url = String.format("get_cases/%s&suite_id=%s&section_id=%d", this.projectid, this.suiteid,
				this.sectionid);
		JsonArray cases = (JsonArray) getRequest(url);
		for (int caseindex = 0; caseindex < cases.size(); caseindex++) {
			JsonObject currentcase = (JsonObject) cases.get(caseindex);
			if (currentcase.get("title").getAsString().equalsIgnoreCase(casename.trim())) {
				caseid = currentcase.get("id").getAsLong();
				break;
			}
		}
		this.setCaseid(caseid);
		return caseid;

	}

	/**
	 * @Title: createTestCase @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param caseName @param @param
	 *         sectionId @param @param refs @param @param
	 *         parameters @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return long
	 *         return type @throws
	 */

	@SuppressWarnings("rawtypes")
	public long createTestCase(String caseName, CASETYPECODE typeid, PRIORITYCODE priorityid, String refs,
			HashMap<String, Object> parameters) {
		long newcaseid = 0;
		if (this.sectionid != 0l) {
			String url = String.format("add_case/%d", this.sectionid);
			HashMap<String, Object> postData = new HashMap<String, Object>();
			postData.put("title", caseName);
			postData.put("type_id", typeid.typecode); // Automated 1
			postData.put("estimate", "2m");
			postData.put("priority_id", priorityid.prioritycode); // 4 means
																	// must test
			if (this.milestoneid != 0l)
				postData.put("milestone_id", this.milestoneid);
			if (refs != null)
				postData.put("refs", refs);
			if (parameters != null) {
				postData.putAll(parameters);
			}

			JsonObject newcase = (JsonObject) postRequest(url, postData);
			if (newcase != null) {
				newcaseid = newcase.get("id").getAsLong();
			}
		}
		this.caseid=newcaseid;
		return newcaseid;
	}

	/**
	 * @Title: updateTestCase @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param caseName @param @param
	 *         caseId @param @param refs @param @param
	 *         parameters @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */
	public boolean updateTestCase(String caseName, CASETYPECODE typeid, PRIORITYCODE priorityid, String refs,
			HashMap parameters) {
		boolean hadupdated = false;
		long getCaseid = getTestCase(caseName);
		String url = String.format("update_case/%d", getCaseid);

		HashMap<String, Object> postData = new HashMap<String, Object>();
		if (caseName != null && caseName != "") {
			postData.put("title", caseName);
		}
		if (typeid != null) {
			postData.put("type_id", typeid.typecode); // Automated
		}
		postData.put("estimate", "5m");
		if (priorityid != null) {
			postData.put("priority_id", priorityid.prioritycode);
		}
		postData.put("milestone_id", this.milestoneid); // Milestone attached to
														// case
		if (refs != null)
			postData.put("refs", refs);
		if (parameters != null)
			postData.putAll(parameters);

		JsonObject updatedcase = (JsonObject) postRequest(url, postData);
		if (updatedcase != null) {
			hadupdated = true;
		}

		return hadupdated;

	}
	/*
	 * 
	 * 1 Passed 2 Blocked 3 Untested 4 Retest 5 Failed
	 */
	/**
	 * @Title: runTestCase @Description: TODO @author
	 *         alterhu2020@gmail.com @param @param casename @param @param
	 *         result @param @param rundescription @param @return @param @throws
	 *         MalformedURLException @param @throws IOException @return boolean
	 *         return type @throws
	 */

	public boolean addTestResultForTestRun(LocalDateTime start,RESULTCODE status, String version,
			String rundescription) {
		if (this.runid != 0 && this.userid != 0) {
			String url = String.format("add_result_for_case/%d/%d", this.runid, this.caseid);
			HashMap<String, Object> postData = new HashMap<String, Object>();

			postData.put("status_id", status.resultcode);
			postData.put("assignedto_id", this.userid);
			rundescription+="\n  >*This test has been marked as***"+status.result+"***";
			postData.put("comment", rundescription);
			long times = new TimeUtils().dateDiff(TimeUtils.UNIT.SECONDS, start, LocalDateTime.now());
			postData.put("elapsed", times+"s");
			postData.put("version", version);

			JsonObject runresult = (JsonObject) postRequest(url, postData);
			if (runresult != null) {
				return true;
			}
		}

		return false;

	}

	public String getBase_Url() {
		return base_Url;
	}

	public void setBase_Url(String base_Url) {
		this.base_Url = base_Url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getProjectid() {
		return projectid;
	}

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}

	public long getMilestoneid() {
		return milestoneid;
	}

	public void setMilestoneid(long milestoneid) {
		this.milestoneid = milestoneid;
	}

	public long getPlanid() {
		return planid;
	}

	public void setPlanid(long planid) {
		this.planid = planid;
	}

	public long getRunid() {
		return runid;
	}

	public void setRunid(long runid) {
		this.runid = runid;
	}

	public long getSuiteid() {
		return suiteid;
	}

	public void setSuiteid(long suiteid) {
		this.suiteid = suiteid;
	}

	public long getSectionid() {
		return sectionid;
	}

	public void setSectionid(long sectionid) {
		this.sectionid = sectionid;
	}

	public long getCaseid() {
		return caseid;
	}

	public void setCaseid(long caseid) {
		this.caseid = caseid;
	}

}

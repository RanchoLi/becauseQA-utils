package com.github.becauseQA.testcasetools;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.github.becauseQA.http.HttpUtils;
import com.github.becauseQA.json.JSONUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * a api for jira connection ,see this page：
 * https://developer.atlassian.com/jiradev/jira-apis/jira-rest-apis/jira-rest-
 * api-tutorials/jira-rest-api-example-discovering-meta-data-for-creating-issues
 * 
 * api document: https://docs.atlassian.com/jira/REST/
 * 
 * @author ahu
 *
 */
public class JiraAPI {

	private String base_Url = null;
	private String user = null;
	private String password = null;

	/**
	 * Creates a new instance of TestRailAPI.
	 *
	 * @param baseurl
	 *            testrail url.
	 * @param username
	 *            testrail username.
	 * @param password
	 *            testrail password.
	 */
	public JiraAPI(String baseurl, String username, String password) {
		this.base_Url=baseurl;
		this.user=username;
		this.password=password;
		if (!baseurl.endsWith("/")) {
			baseurl=baseurl + "/";
		}
		base_Url=baseurl + "rest/api/2/";

	}

	/**
	 * Creates a new instance of TestRailAPI.
	 *
	 */
	public JiraAPI() {
		this.setBase_Url("https://pd/rest/api/2/");
		this.setUser("qa_test_automation");
		this.setPassword("Gr33nDot!");
		//this.setUser("ahu");
		//this.setPassword("gu.chan-102633");
	}

	/**
	 * getRequest:
	 * 
	 * @author alterhu2020@gmail.com
	 * @param url
	 *            jira api uri.
	 * @return the response content.
	 * @since JDK 1.8
	 */
	private Object getRequest(String url) {
		URL parseurl;
		Object responsetext = null;
		try {
			parseurl = new URL(this.getBase_Url() + url);
			Map<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "application/json");
			// headers.put("Accept", "application/json");
			HttpUtils.setAuthorizationHeader(headers,this.getUser(), this.getPassword());
			String response=HttpUtils.getRequestAsString(parseurl, headers);
			if (response != "") {
				responsetext = JSONUtils.toJsonElement(response);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responsetext;
	}

	private Object postRequest(String url, Object data) {
		URL parseurl = null;
		Object responsetext = null;

		try {
			parseurl = new URL(this.getBase_Url() + url);
			Map<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "application/json");
			HttpUtils.setAuthorizationHeader(headers,this.user, this.password);
			String response=HttpUtils.postRequestAsString(parseurl, headers, data);
			
			if (response != "") {
				responsetext = JSONUtils.toJsonElement(response);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responsetext;
	}
	
	
	// *************************************
		/**
		 * get the project by project name
		 * 
		 * @param projectname
		 * @return
		 */
		public String getMyself() {
			String emailAddress= null;
			String url = "myself";
			Object response = getRequest(url);
			if (response instanceof JsonObject) {
				JsonObject myself = (JsonObject) response;
				emailAddress=myself.get("emailAddress").getAsString();

			}
			return emailAddress;
		}
		

	// *************************************
	/**
	 * get the project by project name
	 * 
	 * @param projectname
	 * @return
	 */
	public String[] getProjectNames() {
		String[] projectNames= null;
		String url = "project";
		Object response = getRequest(url);
		if (response instanceof JsonArray) {
			JsonArray projects = (JsonArray) response;
			int size=projects.size();
			projectNames=new String[size];
			for (int k = 0; k < projects.size(); k++) {
				JsonObject foundproject = (JsonObject) projects.get(k);
				String project = foundproject.get("name").getAsString();
				projectNames[k]=project;
			}

		}
		return projectNames;
	}
	/**
	 * get the project by project name
	 * 
	 * @param projectname
	 * @return
	 */
	public String getProjectByName(String projectname) {
		String findproject = null;
		String url = "project";
		Object response = getRequest(url);
		if (response instanceof JsonArray) {
			JsonArray projects = (JsonArray) response;
			for (int k = 0; k < projects.size(); k++) {
				JsonObject foundproject = (JsonObject) projects.get(k);
				String project = foundproject.get("name").getAsString();
				if (project.equalsIgnoreCase(projectname.trim())) {
					findproject = foundproject.get("key").getAsString();
					break;
				}
			}

		}
		return findproject;
	}

	public JsonArray getIssueTypes(String projectkey) {
		JsonArray issueTypes = new JsonArray();
		String url = String.format("issue/createmeta?projectKeys=%s", projectkey);
		Object response = getRequest(url);
		if (response != null) {
			JsonObject projectsResponse = (JsonObject) response;
			JsonArray jsonElement = projectsResponse.get("projects").getAsJsonArray();
			JsonObject projectElement = jsonElement.get(0).getAsJsonObject();
			issueTypes = projectElement.get("issueTypes").getAsJsonArray();
		}
		return issueTypes;
	}

	public JsonObject getIssueFieldData(String projectkey, IssueType issueType) {
		JsonObject fieldData = new JsonObject();
		String url = String.format(
				"issue/createmeta?projectKeys=%s&issuetypeNames=%s&expand=projects.issuetypes.fields", projectkey,
				issueType.getName());
		Object response = getRequest(url);
		if (response != null) {
			JsonObject projectsResponse = (JsonObject) response;
			JsonArray jsonElement = projectsResponse.get("projects").getAsJsonArray();
			JsonObject projectElement = jsonElement.get(0).getAsJsonObject();
			JsonArray issueTypesArray = projectElement.get("issuetypes").getAsJsonArray();
			fieldData = issueTypesArray.get(0).getAsJsonObject().get("fields").getAsJsonObject();
		}
		return fieldData;
	}

	/**
	 * get the project by project name
	 * 
	 * @param projectname
	 * @return
	 */
	public String getIssue(String jiraTicket) {
		String issuekey = null;
		String url = String.format("issue/%s", jiraTicket);
		Object response = getRequest(url);
		if (response != null) {
			JsonObject issue = (JsonObject) response;
			boolean equalsJiraTicket = issue.get("key").getAsString().equals(jiraTicket);
			if (equalsJiraTicket) {
				issuekey = issue.get("key").getAsString();
			}

		}
		return issuekey;
	}

	public boolean createIssue(String parentIssueKey, Fields fields) {
		String url = "issue";	
		Fields parentFields = new Fields();	
		parentFields.setKey(parentIssueKey);
		Issue issue = new Issue();
		issue.setFields(fields);
		fields.setParent(parentFields);
		JsonObject newIssueData = (JsonObject) postRequest(url, issue);
		if (newIssueData != null) {
			return true;
		}
		return false;
	}

	public class Issue {
		private Fields fields;

		public Fields getFields() {
			return fields;
		}

		public void setFields(Fields fields) {
			this.fields = fields;
		}

	}

	public class CustomField{ 
		private String value;
		private String key;
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
	}
	/*
	 * if Null will not seriziable
	 */
	public class Fields {
		private String key;
		private Project project;
		private String summary;
		private String description;
		private IssueType issuetype;
		private Fields parent;
		private Priority priority;
		private Timetracking timetracking;
		private Assignee assignee;
		private CustomField customfield_10801;

		public Fields() {
			
			
		}
		public Fields(String priorityValue) {	
			priority=new Priority(null, priorityValue, priorityValue, null);	
		}

		public Project getProject() {
			project=new Project();
			return project;
		}

		public String getSummary() {
			return summary;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	
		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public IssueType getIssuetype() {
			issuetype=new IssueType();
			return issuetype;
		}


		public Priority getPriority() {
			return priority;
		}

		public Timetracking getTimetracking() {
			timetracking=new Timetracking();
			return timetracking;
		}

		public Assignee getAssignee() {
			assignee=new Assignee();
			return assignee;
		}

		public CustomField getCustomfield_10801() {
			customfield_10801=new CustomField();
			return customfield_10801;
		}
		
		public Fields getParent() {
			return parent;
		}
		private void setParent(Fields parent) {
			this.parent = parent;
		}
		
	

	}

	public class Assignee {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public class Timetracking {
		private String originalEstimate;
		private String remainingEstimate;

		public String getOriginalEstimate() {
			return originalEstimate;
		}

		public void setOriginalEstimate(String originalEstimate) {
			this.originalEstimate = originalEstimate;
		}

		public String getRemainingEstimate() {
			return remainingEstimate;
		}

		public void setRemainingEstimate(String remainingEstimate) {
			this.remainingEstimate = remainingEstimate;
		}
	}

	public class Priority {
		private String self;
		private String description;
		private String name;
		private String id;

		public Priority(String self, String description, String name, String id) {
			this.self = self;
			this.description = description;
			this.name = name;
			this.id = id;

		}

		/**
		 * @return the self
		 */
		public String getSelf() {
			return self;
		}

		/**
		 * @param self
		 *            the self to set
		 */
		public void setSelf(String self) {
			this.self = self;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @param description
		 *            the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id
		 *            the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

	}

	public class IssueType {
		private String id;
		private String name;
		private String self;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSelf() {
			return self;
		}

		public void setSelf(String self) {
			this.self = self;
		}

	}

	public class Project {
		private String self;
		private String id;
		private String key;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getSelf() {
			return self;
		}

		public void setSelf(String self) {
			this.self = self;
		}

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

}

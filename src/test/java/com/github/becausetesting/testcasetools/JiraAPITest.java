package com.github.becausetesting.testcasetools;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.github.becausetesting.testcasetools.JiraAPI.Fields;
import com.github.becausetesting.testcasetools.JiraAPI.IssueType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JiraAPITest {

	private static Logger log=Logger.getLogger(JiraAPITest.class);
	
	private JiraAPI jiraAPI;
	
	@Before
	public void setup(){
		jiraAPI = new JiraAPI();
	}
	@Test
	public void test() {
		
		String projectKey="GDN";
		String parentIssueKey="GDN-20725";
		String issueType="Sub-task";
		
		
		String summary="Regresion Testing-moneypak email";
		String description="Create from jira api";
		String OriginalEstimate="1h";
		String priority="Minor";
		String assigner="ahu";
		String team="Mustang";
	
	
		String myself = jiraAPI.getMyself();
		
		Fields fields=jiraAPI.new Fields(priority);
		fields.setSummary(summary);
		fields.setDescription(description);
		fields.getProject().setKey(projectKey);
		
		IssueType issuetype = fields.getIssuetype();
		issuetype.setName(issueType);
		
		JsonObject issueFieldData = jiraAPI.getIssueFieldData(projectKey, issuetype);
		
		String[] teams=null;
		JsonArray asJsonArray = issueFieldData.get("customfield_10801").getAsJsonObject().get("allowedValues").getAsJsonArray();
		int size = asJsonArray.size();
		teams=new String[size];
		for(int k=0;k<asJsonArray.size();k++){ 
			teams[k]=asJsonArray.get(k).getAsJsonObject().get("value").getAsString();
			
		}
		System.out.println(teams);
		fields.getTimetracking().setOriginalEstimate(OriginalEstimate);		
		fields.getAssignee().setName(assigner);
		
		fields.getCustomfield_10801().setValue(team);
		
		jiraAPI.createIssue(parentIssueKey, fields);
		
		
	}

}

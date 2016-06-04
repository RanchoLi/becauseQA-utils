package com.github.becausetesting.testcasetools;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.github.becausetesting.testcasetools.JiraAPI.Fields;
import com.github.becausetesting.testcasetools.JiraAPI.Issue;
import com.github.becausetesting.testcasetools.JiraAPI.IssueType;
import com.github.becausetesting.testcasetools.JiraAPI.Priority;
import com.github.becausetesting.testcasetools.JiraAPI.Project;
import com.github.becausetesting.testcasetools.JiraAPI.Timetracking;
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
		String parentIssueKey="GDN-20275";
		String issueType="Sub-task";
		
		
		String summary="Certify Unit Testing-QA";
		String description="Create from jira api";
		String OriginalEstimate="1h";
		String priority="Minor";
		String assigner="ahu";
	
		
		Fields fields=jiraAPI.new Fields(priority);
		fields.setSummary(summary);
		fields.setDescription(description);
		fields.getProject().setKey(projectKey);
		
		IssueType issuetype = fields.getIssuetype();
		issuetype.setName(issueType);
		fields.getTimetracking().setOriginalEstimate(OriginalEstimate);		
		fields.getAssignee().setName(assigner);
		
		jiraAPI.createIssue(parentIssueKey, fields);
		
		
	}

}

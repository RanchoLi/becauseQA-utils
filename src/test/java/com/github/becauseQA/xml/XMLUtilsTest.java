package com.github.becauseQA.xml;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Node;
import org.junit.Ignore;
import org.junit.Test;

public class XMLUtilsTest {

	
	@Ignore
	@Test
	public void testURL() {
		
		String xmlurl="https://selenium-release.storage.googleapis.com/?delimiter=/&prefix=";
		
		XMLUtils.read(xmlurl);
		
		Node latestNode = XMLUtils.getXPathNode("//ns:ListBucketResult/ns:CommonPrefixes[last()-1]");
		System.out.println("Parse xpath is; "+latestNode.getUniquePath());
		System.out.println(latestNode.getPath()+",get text:"+latestNode.getStringValue()+",get content: "+latestNode.getText());
		
		Node xPathNode = XMLUtils.getXPathNode("//*[name()='ListBucketResult']/*[name()='CommonPrefixes' and position()=last()-1]");
		
		System.out.println("find another node is: "+xPathNode.getName()+",path: "+xPathNode.getStringValue());
		
		
		
	}
	
	@Ignore
	@Test
	public void testNonamespace(){
		
		XMLUtils.read(new File("sample.xml"));
		Node xPathNode = XMLUtils.getXPathNode("//metadata/versioning/latest");
		
		System.out.println("sample xml content is: "+xPathNode.getStringValue().trim());
	}
	
	
	@Test
	public void testObject(){
		
		List<ResultRow> list=new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			ResultRow row=new ResultRow();
			row.setSelected(true);
			row.setName("test");
			row.setTeam("mine");
			list.add(row);
			
		}
		
		File outputXmlFile=new File("sampletable.xml");
		
		Result tables=new Result();
		tables.setTable(list);
		// covert object to string
		String marshal = XMLUtils.marshal(tables);
		System.out.println("parse result is\n"+marshal);
		//covert object to xml file
		XMLUtils.marshal(tables,outputXmlFile );
		
		//covert xml to object
		
		Result result=(Result) XMLUtils.unmarshal(outputXmlFile, Result.class);
		list=result.getTable();
		
		System.out.println(list);
		
	}
	
	@Test
	public void testObject2(){
		
		SimpleTable table=new SimpleTable();
		table.setFirstname("test323");
		table.setLastname("5433ds");
		
		
		String content=XMLUtils.marshal(table);
		System.out.println(content);
	}

}

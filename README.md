                                  BecauseQA-Utils 
  
what is it?

BecauseQA-Utils libraries  is a 100% pure Java libraries designed to use any popular open java libraries easily.

## Apache Common libraries (com.github.becauseQA.apache.commons)
  
## Global Setting(com.github.becauseQA.application)

## Command line tool (com.github.becauseQA.args)

## AutoIt libraries(com.github.becauseQA.autoit)

## Java Collection libraries(com.github.becauseQA.collections)

## Run windows command(com.github.becauseQA.command)

## Cucumber integration(com.github.becauseQA.cucumber)

## Cucumber integration with selenium(com.github.becauseQA.cucumber.selenium)

## Dll load(com.github.becauseQA.dll)

## email libraries(com.github.becauseQA.email)

## Encrypt/Decrypt libraries(com.github.becauseQA.encrypt)

## File operation libraries(com.github.becauseQA.file)

## Environment host libraries(com.github.becauseQA.host)

## Http operation/HttpClient libraries(com.github.becauseQA.http)

## JDBC operation(com.github.becauseQA.jdbc)

## Jira API(com.github.becauseQA.jire)

## JSON libraries(com.github.becauseQA.json)--Gson

## LDAP connection for user authentication(com.github.becauseQA.ldap)

## Properties file operation(com.github.becauseQA.properties)

## Random number operation(com.github.becauseQA.random)

## Java Reflection libraries(com.github.becauseQA.reflections)

## Java regular expression(com.github.becauseQA.regexp)

## Selenium libraries(com.github.becauseQA.selenium)

## Java String operation(com.github.becauseQA.string)

## Freemarker template libraries(com.github.becauseQA.template)

## Test case tool integration(com.github.becauseQA.testcasetools)

## Java thread practise(com.github.becauseQA.thread)

## Java8 time utilities(com.github.becauseQA.time)

## Web service operation for SOAP/RESTFul(com.github.becauseQA.webservices)

## Xml operation(com.github.becauseQA.xml)

*   Covert Java object to Xml String or XML File
  
    
	//Parse the java Bean Object
	String xmlContent=XMLUtils.marshal(Object);
	
   
	//covert object to xml file
	XMLUtils.marshal(tables,outputXmlFile );
	
*  Covert the xml file or xml string to Java Entity
   
	Result result=(Result) XMLUtils.unmarshal(XmlFile, Result.class);
or 
    
	Result result=(Result) XMLUtils.unmarshal(XmlString, Result.class);



------------------------------------------------------------
Thank you for using becauseQA commons libraries.



<#ftl>
<#-- this file will guid you to create the freemarker tag-->
<!DOCTYPE html>
<html>
<body>
<h1>hello username is: ${self.user.username},password: <#if self.user.password??>null<#else>maptesting</#if>, age: ${self.user.age},salaray: ${self.user.salary}</h1>
<#--http://stackoverflow.com/questions/1510971/expand-a-boolean-variable-to-the-string-true-or-false/1510984-->
<h2> default value for boolean:${self.user.female?c}</h2>
<h2>boolean value use: <#if self.user.female>yes<#else>no</#if></h2>
<h2>boolean value use: ${self.user.female?string('maptesting','notesting')}</h2>

<#-- list value iterator-->
 <table>
<#if self.users?has_content>
	<#list self.users as items>
	 <tr>
	 <td>${items.username!}</td>
	 <td>Null value for :${items.password!}</td>
	 <td>${items.age!}</td>
	 <#break>
	 </tr>
	</#list>
<#else>
   <tr>no content</tr>
</#if>
 </table>
 
 <#-- map iterator-->
<#assign mapObj=self.mapkey/>

get value is: ${mapObj['mytestpasswordkey']}

<#if mapObj?keys?has_content>
   <#list mapObj?keys as key>   
     <h3> map keys info:${key}=${mapObj[key]}</h3>
   </#list>
<#else>
   <b> this is empty</b>
</#if>

<#--------------------------------below------------------------------------>

<#assign testmap={"key1":"keyvalue1","key2":"keyvalue2"}/>
<#assign keys=testmap?keys/>
<#list keys as key>
 key:${key}: ${testmap[key]}
</#list>

</body>
<html>

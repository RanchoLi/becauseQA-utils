<#ftl>
<#-- this file will guid you to create the freemarker tag-->
<!DOCTYPE html>
<html>
<body>
<h1>hello username is: ${username},password: <#if password??>null<#else>password</#if>, age: ${age},salaray: ${salary}</h1>
<#--http://stackoverflow.com/questions/1510971/expand-a-boolean-variable-to-the-string-true-or-false/1510984-->
<h2> default value for boolean:${female?c}</h2>
<h2>boolean value use: <#if female>yes<#else>no</#if></h2>
<h2>boolean value use: ${female?string('yestesting','notesting')}</h2>
</body>
<html>

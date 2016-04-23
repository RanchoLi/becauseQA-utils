/**
 * Project Name:commons
 * File Name:LDAPUtils.java
 * Package Name:com.github.becausetesting.ldap
 * Date:Apr 23, 20162:11:03 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.ldap;
/**
 * ClassName:LDAPUtils  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     Apr 23, 2016 2:11:03 PM 
 * @author   Administrator
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;

import com.github.becausetesting.encrypt.Base64Utils;

public class LDAPUtils {

	private static Logger logger = Logger.getLogger(LDAPUtils.class);

	private static DirContext ctx;
	private static SearchControls search;
	private static String baseDomainName="";
	private static String baseFilter ="&(|(objectClasses=person)(objectClass=inetOrgPerson)(objectClass=user))";


	/**
	 * getContext: get the ldap connection
	 *
	 * @author Administrator
	 * @param servername should be :ldap://localhost:389/dc=domainname,dc=com  
	 * @param user or null
	 * @param password or null
	 * @return
	 * @since JDK 1.8
	 */
	public static DirContext getContext(String servername, String user, String password) {
		if (ctx != null) {
			return ctx;
		}
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		// ldap://localhost:389/dc=domainname,dc=com ,in this way will ignore
		// the domain setting
		env.put(Context.PROVIDER_URL,servername); //// Could be found
															//// using: nslookup
															//// command	
		if (user!=null&&password != null) {			
			env.put(Context.SECURITY_PRINCIPAL,user);
			env.put(Context.SECURITY_CREDENTIALS, password);
		}else{
			env.put(Context.SECURITY_AUTHENTICATION, "none");
		}
		try {
			ctx = new InitialDirContext(env);
			search = new SearchControls();
			search.setSearchScope(SearchControls.SUBTREE_SCOPE);//SearchControls
			// search.setReturningAttributes(returnAttributes);

		} catch (javax.naming.AuthenticationException e) {
			logger.error("Authentication met exception:" + e);
		} catch (NamingException e) {
			// (e.getMessage());
			logger.error("InitialDirContext met exception:" + e);
		}

		return ctx;
	}

	/**
	 * closeCtx: CLOSE the connection
	 * TODO
	 * TODO
	 * TODO 
	 * TODO 
	 *
	 * @author Administrator
	 * @since JDK 1.8
	 */
	private static void closeCtx() {
		try {
			if (ctx != null) {
				ctx.close();
			}
		} catch (NamingException namingException) {
			namingException.printStackTrace();
		}
	}

	/**
	 * verifySHA: compare the SHA1 password with the user input password
	 * TODO
	 * TODO
	 * TODO 
	 * TODO 
	 *
	 * @author Administrator
	 * @param ldappw
	 * @param inputpw
	 * @return
	 * @since JDK 1.8
	 */
	private static boolean verifySHA(String ldappw, String inputpw) {

		// MessageDigestMD5 /SHA,HERE LDAP USER SHA-1
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		// get the encrypt password
		if (ldappw.startsWith("{SSHA}")) {
			ldappw = ldappw.substring(6);
		} else if (ldappw.startsWith("{SHA}")) {
			ldappw = ldappw.substring(5);
		}

		// decrypt the SHA-1 password
		byte[] ldappwbyte = Base64.getDecoder().decode(ldappw);
		byte[] shacode;
		byte[] salt;

		// first20 is SHA-1,20 after is random
		if (ldappwbyte.length <= 20) {
			shacode = ldappwbyte;
			salt = new byte[0];
		} else {
			shacode = new byte[20];
			salt = new byte[ldappwbyte.length - 20];
			System.arraycopy(ldappwbyte, 0, shacode, 0, 20);
			System.arraycopy(ldappwbyte, 20, salt, 0, salt.length);
		}
		// put the input password into
		md.update(inputpw.getBytes());
		// put the random encrypt pasword
		md.update(salt);

		// compared
		byte[] inputpwbyte = md.digest();

		// get the compared password
		return MessageDigest.isEqual(shacode, inputpwbyte);
	}

	/**
	 * getCurrentUserEmail: from the ldap server to get the user's email
	 * TODO
	 * TODO
	 * TODO 
	 * TODO 
	 *
	 * @author Administrator
	 * @return
	 * @since JDK 1.8
	 */
	public static String getCurrentUserEmail() {

		String emailAddress = null;
		String user = System.getProperty("user.name");
		// String filter = "(mail=" + user + "))";
		String filter = "(&(samaccountname=" + user + "))"; // ?????String filter = "(&(sn=" + user + "))";
		Attributes attributes = searchPerson(filter);
		if (attributes == null) {
			logger.warn("No attributes find for current filter: " + filter);
		} else {
			String email = attributes.get("mail").toString();
			emailAddress = email.substring(email.indexOf(":") + 1).trim();
		}
		return emailAddress;
	}

	/**
	 * searchByUserNameAndAuthenticate: from the server to get the username and sam password and 
	 * compare if it's correct as user input
	 *
	 * @author Administrator
	 * @param user
	 * @param pwd
	 * @return
	 * @since JDK 1.8
	 */
	public static boolean searchByUserNameAndAuthenticate(String user, String pwd) {
		boolean success = false;
		String filter = "(&(cn="+user+"))";
	
		Attributes attributes = searchPerson(filter);
		if (attributes == null) {
			logger.warn("No   attributes find for current filter: " + filter);
		} else {
			Attribute attr = attributes.get("userPassword");
			try {
				Object o = attr.get();
				byte[] s = (byte[]) o;
				String encryptpasword = new String(s);
				success = verifySHA(encryptpasword, pwd);
			} catch (NamingException e) {

				// TODO Auto-generated catch block
				logger.error("authenticate failed: " + e);

			}

		}

		return success;
	}

	public static Attributes searchPerson(String filter) {
		Attributes attributes = null;
		//search the top user list,user the ldap browser tool to see the search filter
		filter="("+baseFilter+filter+")";
		int searchResultCount=0;
		try {
			NamingEnumeration en = ctx.search(baseDomainName, filter, search);
			while (en != null && en.hasMoreElements()) {
				searchResultCount+=1;
				Object obj = en.nextElement();
				if (obj instanceof SearchResult) {
					SearchResult si = (SearchResult) obj;
					logger.info("Current Search Result Name: " + si.getName());
					attributes = si.getAttributes();
				} else {
					logger.info(obj);
				}
			}

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			closeCtx();
		}
		logger.info("Total Search Result Count is: "+searchResultCount);
		return attributes;
	}

}

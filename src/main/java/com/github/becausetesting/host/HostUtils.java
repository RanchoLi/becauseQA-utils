/**
 * Project Name:commons
 * File Name:HostUtils.java
 * Package Name:com.github.becausetesting.host
 * Date:Apr 16, 201610:56:39 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.host;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ClassName:HostUtils  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     Apr 16, 2016 10:56:39 PM 
 * @author   Administrator
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class HostUtils {

	
	public static String getShortHostName() {

		String hostname = null;
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hostname;
	}

	/**
	 * get the host IP address
	 * 
	 * @return string host ip address
	 */
	public static String getHostIP() {
		String hostip = null;
		try {
			hostip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hostip;
	}

	/**
	 * get the host name with domain name ,like pdeauto17.fc.kp.com
	 * 
	 * @return String
	 */
	public static String getFQDN() {
		String fqdn = null;
		try {
			fqdn = InetAddress.getLocalHost().getCanonicalHostName().trim();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fqdn;
	}

	/**
	 * get the current JAVA version in the host
	 * 
	 * @return String
	 */
	public static String getJREType() {
		String jretype = System.getProperty("os.arch");
		return jretype;
	}

	/**
	 * get the os type ,like 32 bit or 64 bit, this is only can be used in
	 * windows
	 * 
	 * @return String
	 */
	public static String getOSType() {
		// SystemEnvironment env =SystemEnvironment.getSystemEnvironment();
		// final String envArch = env.getOsArchitecture();
		String arch = System.getProperty("os.arch");
		String ostype = System.getenv("PROCESSOR_ARCHITEW6432");
		// String ostype=System.getProperty("os.arch");
		// logger.info("The os original type is : " + ostype);
		if (ostype != null) {
			if (ostype.contains("64")) {
				return "64bit";
			} else {
				return "32bit";
			}
		} else {
			return arch;
		}
		// return "";

	}

	/**
	 * get the operating system version
	 * 
	 * @return String
	 */
	public static String getOperatingSystemName() {
		String osname = System.getProperty("os.name");
		// logger.info("the Operating system name is:" + osname);
		return osname;
	}

	/**
	 * get the operation system os core version
	 * 
	 * @return String
	 */
	public static String getOperatingSystemVersion() {
		String osversion = System.getProperty("os.version");
		// logger.info("the Operating system version:" + osversion);
		return osversion;
	}

}


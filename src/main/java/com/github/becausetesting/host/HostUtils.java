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
import java.util.Locale;

import com.github.becausetesting.reflections.RefelectionUtils;
import com.sun.jna.Platform;

/**
 * ClassName:HostUtils Function: TODO ADD FUNCTION. Reason: TODO ADD REASON.
 * Date: Apr 16, 2016 10:56:39 PM
 * 
 * @author Administrator
 * @version 1.0.0
 * @since JDK 1.8
 */
public class HostUtils {

	public enum OSType {
		Windows, MacOS, Linux, Other
	};

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
	 * @deprecated get the current JAVA version in the host
	 * 
	 * @return String
	 */
	@Deprecated
	public static String getJREType() {
		String jretype = System.getProperty("os.arch");
		return jretype;
	}

	// cached result of OS detection
	protected static OSType detectedOS;

	/**
	 * detect the operating system from the os.name System property and cache
	 * the result
	 * 
	 * @returns - the operating system detected
	 */
	public static OSType getPlatform() {
		if (detectedOS == null) {
			String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
			if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
				detectedOS = OSType.MacOS;
			} else if (OS.indexOf("win") >= 0) {
				detectedOS = OSType.Windows;
			} else if (OS.indexOf("nux") >= 0) {
				detectedOS = OSType.Linux;
			} else {
				detectedOS = OSType.Other;
			}
		}
		return detectedOS;

	}

	public static boolean isWindows() {
		return Platform.isWindows();
	}

	public static boolean is64Bit() {
		return Platform.is64Bit();
	}

	/**
	 * get the os type ,like 32 bit or 64 bit, this is only can be used in
	 * windows
	 * 
	 * @return String
	 */
	public static String getOSBit() {
		// SystemEnvironment env =SystemEnvironment.getSystemEnvironment();
		// final String envArch = env.getOsArchitecture();
		String arch = System.getProperty("os.arch");
		String processorType = System.getenv("PROCESSOR_ARCHITEW6432");
		// String ostype=System.getProperty("os.arch");
		// logger.info("The os original type is : " + ostype);
		if (arch != null) {
			if (arch.contains("64")) {
				return "64bit";
			} else {
				return "32bit";
			}
		} else {
			return processorType;
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

	@SuppressWarnings("rawtypes")
	public static String getCurrentUserName() {
		String className = null;
		String userName = null;
		String methodUsername = "getUsername";
		if (Platform.isWindows()) {
			className = "com.sun.security.auth.module.NTSystem";
			methodUsername = "getName";
		} else if (Platform.isLinux()) {
			className = "com.sun.security.auth.module.UnixSystem";
		} else if (Platform.isSolaris()) {
			className = "com.sun.security.auth.module.SolarisSystem";
		}

		Class getclass = RefelectionUtils.getclass(className);
		Object contractorInstance = RefelectionUtils.getContractorInstance(getclass, new Object[] {});
		Object method = RefelectionUtils.getMethod(contractorInstance, methodUsername, new Object[] {});
		if (method != null)
			userName = (String) method;
		return userName;
	}

}

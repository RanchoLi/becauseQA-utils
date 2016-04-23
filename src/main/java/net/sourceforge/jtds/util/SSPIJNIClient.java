// jTDS JDBC Driver for Microsoft SQL Server and Sybase
// Copyright (C) 2004 The jTDS Project
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
package net.sourceforge.jtds.util;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.SQLException;


import com.github.becausetesting.dll.DllUtils;
import com.github.becausetesting.file.FileUtils;
import com.github.becausetesting.regexp.RegexpUtils;

import net.sourceforge.jtds.jdbc.Driver;

/**
 * A JNI client to SSPI based CPP program (DLL) that returns the user
 * credentials for NTLM authentication.
 * 
 * The DLL name is ntlmauth.dll.
 *
 * @author Magendran Sathaiah (mahi@aztec.soft.net)
 */
public class SSPIJNIClient {

	/**
	 * Singleton instance.
	 */
	private static SSPIJNIClient thisInstance;

	/**
	 * SSPI native library loaded flag.
	 */
	private static boolean libraryLoaded;

	/**
	 * SSPI client initialized flag.
	 */
	private boolean initialized;

	/**
	 * Initializes the SSPI client.
	 */
	private native void initialize();

	/**
	 * Uninitializes the SSPI client.
	 */
	private native void unInitialize();

	/**
	 * Prepares the NTLM TYPE-1 message and returns it as a <code>byte[]</code>.
	 */
	private native byte[] prepareSSORequest();

	/**
	 * Prepares the NTLM TYPE-3 message using the current user's credentials.
	 * <p>
	 * It needs the challenge BLOB and it's size as input. The challenge BLOB is
	 * nothig but the TYPE-2 message that is received from the SQL Server.
	 *
	 * @param buf
	 *            challenge BLOB
	 * @param size
	 *            challenge BLOB size
	 * @return NTLM TYPE-3 message
	 */
	private native byte[] prepareSSOSubmit(byte[] buf, long size);

	static {
		try {
			//I/O Error: SSO Failed: Native SSPI library not loaded 
			// System.loadLibrary("ntlmauth");
			String osname = System.getProperty("os.name");
			String osarch = System.getProperty("os.arch");
			String tempdllpath = System.getProperty("java.io.tmpdir");

			String ntlmfile="ntlmauth";
			RegexpUtils regexpUtils = new RegexpUtils();
			boolean iswindows = regexpUtils.validate(osname, "^Windows.*");
			String shortpath = "";

			boolean is32bit = regexpUtils.validate(osarch, "^x86$");
			boolean is64bit = regexpUtils.validate(osarch, "^amd64$");
			if (is32bit) {
				shortpath = "/jtds/x86/SSO/"+ntlmfile+".dll";
			}
			if (is64bit) {
				shortpath = "/jtds/x64/SSO/"+ntlmfile+".dll";
			}
			
			InputStream ssofile =SSPIJNIClient.class.getResourceAsStream(shortpath);
			// copy the file to local host
			String destationfile=tempdllpath+File.separator+ntlmfile+".dll";
			FileOutputStream output = new FileOutputStream(destationfile);
			
			FileUtils.copy(ssofile, output);
			new DllUtils().loadDll(tempdllpath, ntlmfile);

			SSPIJNIClient.libraryLoaded = true;

		} catch (UnsatisfiedLinkError | IOException err) {
			Logger.println("Unable to load library: " + err);
		}
	}

	/**
	 * Private constructor for singleton.
	 */
	private SSPIJNIClient() {
		// empty constructor
	}

	
	/**
	 * getInstance:  get SSPIJNIClient object.
	 * @author alterhu2020@gmail.com
	 * @return SSPIJNIClient object.
	 * @throws Exception any exception.
	 * @since JDK 1.8
	 */
	public static synchronized SSPIJNIClient getInstance() throws Exception {

		if (thisInstance == null) {
			if (!libraryLoaded) {
				throw new Exception(
						"Native SSPI library not loaded. " + "Check the java.library.path system property.");
			}
			thisInstance = new SSPIJNIClient();
			thisInstance.invokeInitialize();
		}
		return thisInstance;
	}

	/**
	 * Calls <code>#initialize()</code> if the SSPI client is not already
	 * inited.
	 */
	public void invokeInitialize() {
		if (!initialized) {
			initialize();
			initialized = true;
		}
	}

	/**
	 * Calls <code>#unInitialize()</code> if the SSPI client is inited.
	 */
	public void invokeUnInitialize() {
		if (initialized) {
			unInitialize();
			initialized = false;
		}
	}

	/**
	 * Calls <code>#prepareSSORequest()</code> to prepare the NTLM TYPE-1
	 * message.
	 *
	 * @throws Exception
	 *             if an error occurs during the call or the SSPI client is
	 *             uninitialized
	 *@return the whole byte array.
	 */
	public byte[] invokePrepareSSORequest() throws Exception {
		if (!initialized) {
			throw new Exception("SSPI Not Initialized");
		}
		return prepareSSORequest();
	}

	/**
	 * Calls <code>#prepareSSOSubmit(byte[], long)</code> to prepare the NTLM
	 * TYPE-3 message.
	 *@param buf the buffer byte object.
	 * @throws Exception
	 *             if an error occurs during the call or the SSPI client is
	 *             uninitialized
	 * @return the whole byte arrays.
	 */
	public byte[] invokePrepareSSOSubmit(byte[] buf) throws Exception {
		if (!initialized) {
			throw new Exception("SSPI Not Initialized");
		}
		return prepareSSOSubmit(buf, buf.length);
	}
}

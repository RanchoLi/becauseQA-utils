/**
 * Project Name:commons
 * File Name:CommandUtils.java
 * Package Name:com.github.becausetesting.command
 * Date:Apr 16, 201610:59:06 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: CommandUtils  
 * Function: TODO ADD FUNCTION.  
 * Reason: TODO ADD REASON 
 * date: Apr 23, 2016 6:07:49 PM  
 * @author alterhu2020@gmail.com
 * @version 1.0.0
 * @since JDK 1.8
 */
public class CommandUtils {

	
	/**
	 * executeCommand: 
	 * @author alterhu2020@gmail.com
	 * @param command testing
	 * @return the command line output content
	 * @since JDK 1.8
	 * @deprecated take to use 
	 */
	public static String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {

			p = Runtime.getRuntime().exec(command);

			p.waitFor();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			// logger.info("Command output is:"+line);

		} catch (Exception e) {
			System.out.println("exception happened running command - here's what I know: ");
			e.printStackTrace();
			System.exit(-1);
		}

		return output.toString();

	}



	/**
	 * executeCommandwithAdminstrator: 
	 * @author alterhu2020@gmail.com
	 * @param command the command string you input.
	 * @return output command line content
	 * @since JDK 1.8
	 * @deprecated
	 */
	public static String executeCommandwithAdminstrator(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(
					"cmd.exe /c echo password123 | runas /profile /user:Administrator /savecred \"" + command + "\"");
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			System.out.println("exception happened running command - here's what I know: ");
			e.printStackTrace();
			System.exit(-1);
		}

		return output.toString();

	}

	/**
	 * executeCommand: 
	 * @author alterhu2020@gmail.com
	 * @param command input content command.
	 * @return command line content
	 * @since JDK 1.8
	 */
	public static String executeCommand(List<String> command) {

		StringBuffer output = new StringBuffer();
		Process process = null;
		try {
			ProcessBuilder pb = new ProcessBuilder(command);

			process = pb.start();

			// process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
				System.out.println(line);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.exit(-1);
		}
		return output.toString();
	}

	/**
	 * executeCommand: 
	 * @author alterhu2020@gmail.com
	 * @param workdirectory the directory you want to switch.
	 * @param command input command.
	 * @return command line content
	 * @since JDK 1.8
	 */
	public static String executeCommand(String workdirectory, List<String> command) {

		StringBuffer output = new StringBuffer();
		Process process = null;
		try {
			ProcessBuilder pb = new ProcessBuilder(command);
			System.out.println("command input is:" + command.toString());
			pb.directory(new File(workdirectory));
			process = pb.start();

			// process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
				System.out.println(line);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.exit(-1);
		}
		return output.toString();
	}

	/**
	 * destoryWindowsProcess: 
	 * @author alterhu2020@gmail.com
	 * @param processname the process name .
	 * @return command line content
	 * @since JDK 1.8
	 */
	public static String destoryWindowsProcess(String processname) {

		StringBuffer output = new StringBuffer();
		Process process = null;
		try {
			List<String> command = new ArrayList<String>();
			command.add("taskkill.exe");
			command.add("/F");
			command.add("/IM");
			command.add(processname);
			System.out.println("Destory Process Command>>> " + command.toString() + " !");
			ProcessBuilder pb = new ProcessBuilder(command);

			process = pb.start();
			Thread.sleep(1000);
			process.destroy();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
				System.out.println(line);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("exception happened running command - here's what I know: ");
			e.printStackTrace();
			System.exit(-1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output.toString();
	}

	/*
	 * apache command utility
	 */

	/**
	 * Execute a command on the operating system using Java's built-in Process
	 * class
	 *
	 * @param command
	 *            A string representation of the command to execute.
	 * @param getOutput
	 *            Whether or not to get the output/error streams of the process
	 *            you forked. This is helpful for debugging reasons.
	 * @return A string representation of output/error streams of the process.
	 */
	/*
	public static String executeCommandUsingJavaRuntime(String command, boolean getOutput) {
		return executeCommandUsingJavaRuntime(new String[] { command }, getOutput);
	}

	*/
	/**
	 * Execute a command on the operating system using Apache Commons Exec. This
	 * function runs asynchronously and dumps both stderr and stdout streams to
	 * a temp file.
	 *
	 * @param commandLine
	 *            The command to be executed.
	 * @param outputStreamHandler
	 *            An output stream to dump the process stderr and stdout to it.
	 */
	/*
	public static void executeCommandUsingApacheExec(CommandLine commandLine, OutputStream outputStreamHandler) {
		try {
			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			PumpStreamHandler streamHandler = new PumpStreamHandler(outputStreamHandler);
			logger.info("commandLine: " + commandLine.toString());
			Executor process = new DefaultExecutor();
			process.setExitValue(0);
			process.setStreamHandler(streamHandler);
			process.execute(commandLine, resultHandler);

			// resultHandler.waitFor();
		} catch (Exception ex) {
			logger.error("An exception was thrown.", ex);
		}
	}
	*/

	
	/**
	 * humanReadableCommandLineOutput: 
	 * @author alterhu2020@gmail.com
	 * @param arguments output list string.
	 * @return content of command line
	 * @since JDK 1.8
	 */
	public static String humanReadableCommandLineOutput(List<String> arguments) {
		String debugOutput = "";
		for (String argument : arguments) {
			debugOutput += argument + " ";
		}
		return debugOutput.trim();
	}

	
}


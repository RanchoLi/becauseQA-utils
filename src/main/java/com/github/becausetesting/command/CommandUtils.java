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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.OS;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * ClassName: CommandUtils Function: TODO ADD FUNCTION. Reason: TODO ADD REASON
 * date: Apr 23, 2016 6:07:49 PM
 * 
 * @author alterhu2020@gmail.com
 * @version 1.0.0
 * @since JDK 1.8
 */
public class CommandUtils {

	private static Logger logger = Logger.getLogger(CommandUtils.class);

	/**
	 * executeCommand:
	 * 
	 * @author alterhu2020@gmail.com
	 * @param workdirectory
	 *            the directory you want to switch.
	 * @param command
	 *            input command.
	 * @return command line content
	 * @since JDK 1.8
	 */
	public String runCommand(String workdirectory, List<String> commands) {

		StringBuffer output = new StringBuffer();
		Process process = null;
		try {
			ProcessBuilder pb = new ProcessBuilder(commands);
			logger.info("command input is: " + humanReadableCommandLineOutput(commands));
			if (workdirectory != null)
				pb.directory(new File(workdirectory));
			process = pb.start();

			// process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
				logger.info(line);
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
	 * 
	 * @author alterhu2020@gmail.com
	 * @param workdirectory
	 *            the directory you want to switch.
	 * @param command
	 *            input command.
	 * @return command line content
	 * @since JDK 1.8
	 */
	public String runCommand(String workdirectory, String... commands) {
		List<String> listcommands = new LinkedList<String>();
		for (String command : commands) {
			listcommands.add(command);
		}
		return runCommand(workdirectory, listcommands);
	}
	/*
	 * apache command utility
	 */

	/**
	 * Execute a command on the operating system using Java's built-in Process
	 * class
	 *
	 * @param command
	 *            A string array representation of the command to execute.
	 * @param getOutput
	 *            Whether or not to get the output/error streams of the process
	 *            you forked. This is helpful for debugging reasons.
	 * @return A string representation of output/error streams of the process.
	 */
	public static String runCommandUsingJavaRuntime(String[] command, boolean getOutput) {
		String output = "";

		try {
			Process p = Runtime.getRuntime().exec(command);

			// read the output from the command if requested by the user
			if (getOutput) {
				List<String> outErrStr = IOUtils.readLines(p.getInputStream());
				output = String.join("\n", outErrStr.toArray(new String[outErrStr.size()]));
			}
		} catch (Exception ex) {
			logger.error("An exception was thrown while executing the command (" + command + ")", ex);
		}

		return output;
	}

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

	public static void runCommandUsingApacheExec(CommandLine commandLine, OutputStream outputStreamHandler) {
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

	/**
	 * Constructs a CommandLine object depending on the current running
	 * operating system using the number of arguments passed to it.
	 *
	 * @param command
	 *            The native OS command to run or an absolute path to an
	 *            executable to run.
	 * @param parameters
	 *            The command parameters.
	 * @param arguments
	 *            String arguments of the command to formulate from.
	 * @return CommandLine object that represents the command you want to
	 *         execute.
	 */
	public static CommandLine createCommandLine(String command, String[] parameters, String... arguments) {
		CommandLine commanddLine = null;

		// add the command to be executed
		if (OS.isFamilyWindows()) {
			commanddLine = new CommandLine("\"" + command + "\"");
		} else if (OS.isFamilyMac() || OS.isFamilyUnix()) {
			commanddLine = new CommandLine(command.contains(" ") ? "'" + command + "'" : command);
		} else {
			throw new UnsupportedOperationException("Unsupported operating system.");
		}

		// add the command parameters
		if (OS.isFamilyWindows()) {
			for (String parameter : parameters) {
				commanddLine.addArgument("\"" + parameter + "\"", false);
			}
		} else if (OS.isFamilyMac() || OS.isFamilyUnix()) {
			for (String parameter : parameters) {
				commanddLine.addArgument(parameter.contains(" ") ? "'" + parameter + "'" : parameter, false);
			}
		}

		// add the command arguments
		for (String argument : arguments) {
			// you have to pass the false value and disable handling quoting
			// otherwise the OS won't be able to run the shell file on MAc OS
			commanddLine.addArgument(argument, false);
		}

		return commanddLine;
	}

	/**
	 * destoryWindowsProcess:
	 * 
	 * @author alterhu2020@gmail.com
	 * @param processname
	 *            the process name .
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
			logger.info("Destorying Process Command>>> " + command.toString() + " !");
			ProcessBuilder pb = new ProcessBuilder(command);

			process = pb.start();
			Thread.sleep(1000);
			process.destroy();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
				logger.info(line);
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

	/**
	 * humanReadableCommandLineOutput:
	 * 
	 * @author alterhu2020@gmail.com
	 * @param arguments
	 *            output list string.
	 * @return content of command line
	 * @since JDK 1.8
	 */
	public static String humanReadableCommandLineOutput(List<String> arguments) {
		String outputArguments = String.join(" ", arguments);
		return outputArguments.trim();
	}

}

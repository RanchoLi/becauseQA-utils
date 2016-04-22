package com.github.becausetesting.args;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(optionPrefixes="-",separators="=")
public class CommandLineArgs {

	@Parameter(names={"-m","--month"},required=true,description="test version",validateWith=MonthValidator.class)
	public int month;
	

	@Parameter(names = {"--help","-h"}, description="helper",help = true)
	public boolean help;
	@Parameter(names={"-v","--version"},required=true,description="tool version")
	public int version;
	
	

	
	
}

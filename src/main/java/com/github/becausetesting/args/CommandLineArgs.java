package com.github.becausetesting.args;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * ClassName: CommandLineArgs  
 * Function: TODO ADD FUNCTION.  
 * Reason: TODO ADD REASON 
 * date: Apr 23, 2016 6:05:37 PM  
 * @author alterhu2020@gmail.com
 * @version 1.0.0
 * @since JDK 1.8
 */
@Parameters(optionPrefixes="-",separators="=")
public class CommandLineArgs {

	@Parameter(names={"-m","--month"},required=true,description="test version",validateWith=MonthValidator.class)
	public int month;
	

	@Parameter(names = {"--help","-h"}, description="helper",help = true)
	public boolean help;
	@Parameter(names={"-v","--version"},required=true,description="tool version")
	public int version;
	
	

	
	
}

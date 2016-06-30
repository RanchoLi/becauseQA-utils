package com.github.becausetesting.args;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beust.jcommander.JCommander;
public class CommandLineArgsTest {

	
	private JCommander jCommander;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		CommandLineArgs parameters = new CommandLineArgs();
		jCommander = new JCommander(parameters);
//		jCommander=new JCommander()
		
		try {

			jCommander.setProgramName(CommandLineArgsTest.class.getSimpleName());
			jCommander.parse("--version", "333232321"); // the parameters need
														// to parse
			boolean help = parameters.help;
			if (help) {
				jCommander.usage();
			} else {
				// do the logic
				System.out.println(parameters.month);
			}
		} catch (Exception e) {
			//System.out.println(e);
			jCommander.usage();
		}

	}

}

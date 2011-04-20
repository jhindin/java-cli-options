package clioptions.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import clioptions.CliOptions;
import clioptions.CliSyntaxException;
import clioptions.OptionsSyntaxException;


public abstract class TestParsingShort {
	CliOptions cliOptions;
	
	@Before
	public  void setupOptions() throws Exception
	{
		cliOptions = new CliOptions("abcd:e#");
	}

	@Test 
	public void  simple()  throws Exception {
		String args[] = "abc def".split(" ");
		cliOptions.parse(args);
		String rargs[] = cliOptions.getRemaningArgs();
		assertArrayEquals(args, rargs);
		assertFalse(cliOptions.isOptionSet("a"));
		assertFalse(cliOptions.isOptionSet("z"));
		assertNull(cliOptions.getOptionValue("z"));
	}

	@Test 
	public void  optionsEndsImmediatelyWihDoubleDash()  throws Exception {
		String args[] = "-- abc def".split(" ");
		cliOptions.parse(args);
		String rargs[] = cliOptions.getRemaningArgs();
		assertArrayEquals("abc def".split(" "), rargs);
		assertFalse(cliOptions.isOptionSet("a"));
		assertFalse(cliOptions.isOptionSet("z"));
		assertNull(cliOptions.getOptionValue("z"));
	}

	@Test 
	public void  SingleOption()  throws Exception {
		String args[] = "-a abc def".split(" ");
		cliOptions.parse(args);
		String rargs[] = cliOptions.getRemaningArgs();
		assertArrayEquals("abc def".split(" "), rargs);
		assertTrue(cliOptions.isOptionSet("a"));
	}

	@Test(expected=CliSyntaxException.class)
	public void  OptionDNoVaue()  throws Exception {
		String args[] = "-d".split(" ");
		cliOptions.parse(args);
	}

	@Test(expected=CliSyntaxException.class)
	public void  OptionENoVaue()  throws Exception {
		String args[] = "-e".split(" ");
		cliOptions.parse(args);
	}

	@Test(expected=CliSyntaxException.class)
	public void  OptionDMultipleVaues()  throws Exception {
		String args[] = "-d aaa -d bbb".split(" ");
		cliOptions.parse(args);
	}
}

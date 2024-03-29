package clioptions.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;


import org.junit.Before;
import org.junit.Test;

import clioptions.CliOptions;
import clioptions.errors.creation.OptionError;
import clioptions.exceptions.parsing.ParsingException;


public  class TestParsingShort {
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
	public void  singleOption()  throws Exception {
		String args[] = "-a abc def".split(" ");
		cliOptions.parse(args);
		String rargs[] = cliOptions.getRemaningArgs();
		assertArrayEquals("abc def".split(" "), rargs);
		assertTrue(cliOptions.isOptionSet("a"));
	}

	@Test(expected=ParsingException.class)
	public void  OptionDNoVaue()  throws Exception {
		String args[] = "-d".split(" ");
		cliOptions.parse(args);
	}

	@Test(expected=ParsingException.class)
	public void  optionENoVaue()  throws Exception {
		String args[] = "-e".split(" ");
		cliOptions.parse(args);
	}

	@Test(expected=ParsingException.class)
	public void  optionEA()  throws Exception {
		String args[] = "-ae tyu".split(" ");
		cliOptions.parse(args);
	}

	@Test(expected=ParsingException.class)
	public void  optionDMultipleVaues()  throws Exception {
		String args[] = "-d aaa -d bbb".split(" ");
		cliOptions.parse(args);
	}

	@Test 
	public void  multipleOptionValues()  throws Exception {
		String args[] = "-a -d asd -e ght -e bnm abc def".split(" ");
		cliOptions.parse(args);
		String rargs[] = cliOptions.getRemaningArgs();
		assertArrayEquals("abc def".split(" "), rargs);
		assertTrue(cliOptions.isOptionSet("a"));
		assertFalse(cliOptions.isOptionSet("b"));
		assertEquals("asd", cliOptions.getOptionValue("d"));
		assertEquals("ght", cliOptions.getOptionValue("e"));
		assertEquals(Arrays.asList("ght", "bnm"), cliOptions.getAllOptionValues("e"));
	}

}

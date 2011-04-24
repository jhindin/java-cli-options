package clioptions.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import clioptions.CliOptions;


public class TestCreationAndParsingShort {
	@Test
	public void v1() throws Exception
	{
		CliOptions cliOptions = new CliOptions("abcd");

		cliOptions.parse("-a -d abc def".split(" "));
		
		assertArrayEquals("abc def".split(" "), cliOptions.getRemaningArgs());
		assertTrue(cliOptions.isOptionSet("a"));
		assertFalse(cliOptions.isOptionSet("b"));
		assertFalse(cliOptions.isOptionSet("c"));
		assertTrue(cliOptions.isOptionSet("d"));
	}

}

package clioptions.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
		
		cliOptions.parse("-a -c abc def".split(" "));
		
		assertArrayEquals("abc def".split(" "), cliOptions.getRemaningArgs());
		assertTrue(cliOptions.isOptionSet("a"));
		assertFalse(cliOptions.isOptionSet("b"));
		assertTrue(cliOptions.isOptionSet("c"));
		assertFalse(cliOptions.isOptionSet("d"));
		
		cliOptions.parse("-ac abc def".split(" "));
		
		assertArrayEquals("abc def".split(" "), cliOptions.getRemaningArgs());
		assertTrue(cliOptions.isOptionSet("a"));
		assertFalse(cliOptions.isOptionSet("b"));
		assertTrue(cliOptions.isOptionSet("c"));
		assertFalse(cliOptions.isOptionSet("d"));

		cliOptions = new CliOptions("abcde:");

		cliOptions.parse("-ac abc def".split(" "));
		
		assertArrayEquals("abc def".split(" "), cliOptions.getRemaningArgs());
		assertTrue(cliOptions.isOptionSet("a"));
		assertFalse(cliOptions.isOptionSet("b"));
		assertTrue(cliOptions.isOptionSet("c"));
		assertFalse(cliOptions.isOptionSet("d"));
		assertNull(cliOptions.getOptionValue("e"));
	
		cliOptions.parse("-ac -e def abc def".split(" "));
		
		assertArrayEquals("abc def".split(" "), cliOptions.getRemaningArgs());
		assertTrue(cliOptions.isOptionSet("a"));
		assertFalse(cliOptions.isOptionSet("b"));
		assertTrue(cliOptions.isOptionSet("c"));
		assertFalse(cliOptions.isOptionSet("d"));
		assertEquals("def", cliOptions.getOptionValue("e"));

	}

}

package clioptions.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import clioptions.CliOptions;


public class TestCreationAndParsingLong {
	@Test
	public void v1() throws Exception
	{
		CliOptions cliOptions = new CliOptions(Arrays.asList("aaa", "bbb", "ccc", "ddd"));

		cliOptions.parse("--aaa --ddd abc def".split(" "));
		
		assertArrayEquals("abc def".split(" "), cliOptions.getRemaningArgs());
		assertTrue(cliOptions.isOptionSet("aaa"));
		assertFalse(cliOptions.isOptionSet("bbb"));
		assertFalse(cliOptions.isOptionSet("ccc"));
		assertTrue(cliOptions.isOptionSet("ddd"));
		
		cliOptions.parse("--aaa --ccc abc def".split(" "));
		
		assertArrayEquals("abc def".split(" "), cliOptions.getRemaningArgs());
		assertTrue(cliOptions.isOptionSet("aaa"));
		assertFalse(cliOptions.isOptionSet("bbb"));
		assertTrue(cliOptions.isOptionSet("ccc"));
		assertFalse(cliOptions.isOptionSet("ddd"));
	}

	public void v2() throws Exception
	{
		CliOptions cliOptions = new CliOptions(Arrays.asList("aaa", "bbb", "ccc", "ddd",
				"eee:"));

		cliOptions.parse("--aaa --ddd abc def".split(" "));
		assertArrayEquals("abc def".split(" "), cliOptions.getRemaningArgs());
		assertTrue(cliOptions.isOptionSet("aaa"));
		assertFalse(cliOptions.isOptionSet("bbb"));
		assertFalse(cliOptions.isOptionSet("ccc"));
		assertTrue(cliOptions.isOptionSet("ddd"));
		assertNull(cliOptions.getOptionValue("eee"));

		cliOptions.parse("--aaa --ddd -eee dfg abc def".split(" "));
		assertArrayEquals("abc def".split(" "), cliOptions.getRemaningArgs());
		assertTrue(cliOptions.isOptionSet("aaa"));
		assertFalse(cliOptions.isOptionSet("bbb"));
		assertFalse(cliOptions.isOptionSet("ccc"));
		assertTrue(cliOptions.isOptionSet("ddd"));
		assertEquals("dfg", cliOptions.getOptionValue("eee"));
	}

}

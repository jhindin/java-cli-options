package clioptions.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import clioptions.CliOptions;
import clioptions.CliSyntaxException;


public abstract class AbstractTestParsingLong {
	CliOptions cliOptions;
	
	@Test 
	public void  simple()  throws Exception {
		String args[] = "abc def".split(" ");
		cliOptions.parse(args);
		String rargs[] = cliOptions.getRemaningArgs();
		assertArrayEquals(args, rargs);
		assertFalse(cliOptions.isOptionSet("aaa"));
		assertFalse(cliOptions.isOptionSet("zzz"));
		assertNull(cliOptions.getOptionValue("z"));
	}

	@Test 
	public void  optionsEndsImmediatelyWihDoubleDash()  throws Exception {
		String args[] = "-- abc def".split(" ");
		cliOptions.parse(args);
		String rargs[] = cliOptions.getRemaningArgs();
		assertArrayEquals("abc def".split(" "), rargs);
		assertFalse(cliOptions.isOptionSet("aaaa"));
		assertFalse(cliOptions.isOptionSet("zzz"));
		assertNull(cliOptions.getOptionValue("z"));
	}
	
	@Test 
	public void  singleShortOption()  throws Exception {
		String args[] = "-a abc def".split(" ");
		cliOptions.parse(args);
		String rargs[] = cliOptions.getRemaningArgs();
		assertArrayEquals("abc def".split(" "), rargs);
		assertTrue(cliOptions.isOptionSet("a"));
	}

	@Test 
	public void  singleLongOption()  throws Exception {
		String args[] = "--aaa abc def".split(" ");
		cliOptions.parse(args);
		String rargs[] = cliOptions.getRemaningArgs();
		assertArrayEquals("abc def".split(" "), rargs);
		assertTrue(cliOptions.isOptionSet("aaa"));
		assertFalse(cliOptions.isOptionSet("a"));
	}
	
	@Test(expected=CliSyntaxException.class)
	public void  optionEEENoVaue()  throws Exception {
		String args[] = "--eee".split(" ");
		cliOptions.parse(args);
	}

	@Test(expected=CliSyntaxException.class)
	public void  optionFFFNoVaue()  throws Exception {
		String args[] = "--fff".split(" ");
		cliOptions.parse(args);
	}

	@Test(expected=CliSyntaxException.class)
	public void  optionENoVaue()  throws Exception {
		String args[] = "-e".split(" ");
		cliOptions.parse(args);
	}
	
	@Test 
	public void  multipleFFFOptionValues()  throws Exception {
		String args[] = "-a -f lkj --fff ght --fff bnm -f oiu abc def".split(" ");
		cliOptions.parse(args);
		String rargs[] = cliOptions.getRemaningArgs();
		assertArrayEquals("abc def".split(" "), rargs);
		assertTrue(cliOptions.isOptionSet("a"));
		assertFalse(cliOptions.isOptionSet("b"));
		assertNull(cliOptions.getOptionValue("d"));
		assertNull(cliOptions.getOptionValue("e"));
		assertEquals(Arrays.asList("ght", "bnm"), cliOptions.getAllOptionValues("fff"));
		assertEquals(Arrays.asList("lkj", "oiu"), cliOptions.getAllOptionValues("f"));
	}
	
}

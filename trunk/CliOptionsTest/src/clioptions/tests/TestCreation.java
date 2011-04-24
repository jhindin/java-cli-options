package clioptions.tests;

import java.util.Arrays;


import org.junit.Test;

import clioptions.CliOptions;
import clioptions.exceptions.creation.OptionException;


public class TestCreation {
	@Test
	public void creation() throws Exception
	{
		CliOptions options;
		
		options = new CliOptions("avd:");
		
		String longOptions[] = {"ccc", "ddd"};
		options = new CliOptions(longOptions);
		
		options = new CliOptions("avd:", longOptions);
		
		options = new CliOptions("asdf:", Arrays.asList("rrr", "ttt"));

		options = new CliOptions("asdf:", Arrays.asList("rrr", "ttt:"));

		options = new CliOptions("asdf#m:", Arrays.asList("rrr", "ttt:", "ooo#"));
	}
	
	@Test(expected=OptionException.class)
	public void creationFailureEndsWithSemicolon() throws Exception 
	{
		CliOptions options;
		
		options = new CliOptions("a;");
	}

	@Test(expected=OptionException.class)
	public void creationFailureSemicolon() throws Exception 
	{
		CliOptions options;
		
		options = new CliOptions("a;b");
	}

	@Test(expected=OptionException.class)
	public void creationFailureDoubleColon() throws Exception 
	{
		CliOptions options;
		
		options = new CliOptions("a::");
	}

	@Test(expected=OptionException.class)
	public void creationFailureStartWithColon() throws Exception 
	{
		CliOptions options;
		
		options = new CliOptions(":t");
	}

	@Test(expected=OptionException.class)
	public void creationFailureLongIsColon() throws Exception 
	{
		CliOptions options;
		
		options = new CliOptions("t", Arrays.asList("abc", ":"));
	}

	@Test(expected=OptionException.class)
	public void creationFailureLongIsSemicolon() throws Exception 
	{
		CliOptions options;
		
		options = new CliOptions("t", Arrays.asList("abc", ";"));
	}

	@Test(expected=OptionException.class)
	public void creationFailureLongContainsSemicolon() throws Exception 
	{
		CliOptions options;
		
		options = new CliOptions("t", Arrays.asList("abc", "abc;def"));
	}

	@Test(expected=OptionException.class)
	public void creationFailureLongContainsSemicolonEndWithColon() throws Exception 
	{
		CliOptions options;
		
		options = new CliOptions("t", Arrays.asList("abc", "abc;def:"));
	}
}
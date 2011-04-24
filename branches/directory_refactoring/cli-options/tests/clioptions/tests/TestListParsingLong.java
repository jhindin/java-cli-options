package clioptions.tests;

import java.util.Arrays;

import org.junit.Before;

import clioptions.CliOptions;


public class TestListParsingLong extends AbstractTestParsingLong {

	
	@Before
	public void createCliOptions() throws Exception
	{
		cliOptions = new CliOptions("abcde:f#", Arrays.asList("aaa", "bbb", "ccc",
				"ddd", "eee:", "fff#"));
		
		
	}

}

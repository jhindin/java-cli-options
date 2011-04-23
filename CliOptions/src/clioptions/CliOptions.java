package clioptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CliOptions {
	protected HashMap<String, Option> longOptionsMap = new HashMap<String, Option>();
	protected HashMap<String, Option> shortOptionsMap = new HashMap<String, Option>();
	protected HashMap<String, List<String>> optionsValues = new HashMap<String, List<String>>();
	protected HashSet<String> optionsWithoutValues = new HashSet<String>();
	protected String remainingArgs[];
	
	public CliOptions(String shortOptions) throws OptionsSyntaxException
	{
		parseShortOptionsString(shortOptions);
	}
	
	public CliOptions(String longOptions[])  throws OptionsSyntaxException {
		fillLongOptions(longOptions);
	}
	
	public CliOptions(List<String> longOptions) throws OptionsSyntaxException {
		String o[] = new String[longOptions.size()];
		o = longOptions.toArray(o);
		fillLongOptions(o);
	}
	
	public CliOptions(String shortOptions, String longOptions[])  throws OptionsSyntaxException {
		parseShortOptionsString(shortOptions);
		fillLongOptions(longOptions);
	}

	public CliOptions(String shortOptions, List<String> longOptions) throws OptionsSyntaxException {
		parseShortOptionsString(shortOptions);

		String o[] = new String[longOptions.size()];
		o = longOptions.toArray(o);
		fillLongOptions(o);
	}
	
	
	public void parse(String args[]) throws CliSyntaxException {
		
		remainingArgs = null;
		optionsValues = new HashMap<String, List<String>>();
		optionsWithoutValues = new HashSet<String>();
		
		for (int i = 0; i < args.length; i++) {
			if (args[i].charAt(0) == '-') {
				if (args[i].length() == 1) 
					throw new CliSyntaxException("single - argument");
				
				if (args[i].equals("--")) {
					remainingArgs = Arrays.copyOfRange(args, i + 1, args.length);
					return;
				}
				
				if (args[i].charAt(1) == '-') {
					String longOption = args[i].substring(2);
					Option o = longOptionsMap.get(longOption);
					if (o == null) 
						throw new CliSyntaxException("unknown option " + longOption);
					
					if (o.requireValue) {
						if (i == (args.length - 1))
							throw new CliSyntaxException("option " + longOption + " without argument");
						
						putOptionWithArg(o, longOption, args[++i]);
					} else {
						putOption(longOption);
					}
				} else {
					for (int j = 1; j < args[i].length(); j++) {
						char shortOption = args[i].charAt(j);
						Option o = shortOptionsMap.get(Character.toString(shortOption));
						if (o==null)
							throw new CliSyntaxException("unknown option " + shortOption);
							
						if (o.requireValue) {
							if (args[i].length() != 2) 
								throw new CliSyntaxException("option " + shortOption + " require value," +
										" can't be set with other options");
							
							if (i == (args.length - 1))
								throw new CliSyntaxException("option " + shortOption + " without argument");
							
							putOptionWithArg(o, Character.toString(shortOption), args[++i]);
							break;
						} else { 
							putOption(Character.toString(shortOption));
						}
					}
				}
			} else { // no -
				remainingArgs = Arrays.copyOfRange(args, i, args.length);
				return;
			}
		}
	}
	
	public String[] getRemaningArgs()
	{
		return remainingArgs;
	}
	
	public boolean isOptionSet(String option) 
	{
		return optionsWithoutValues.contains(option);
	}

	public String getOptionValue(String option) 
	{
		List<String> values =  optionsValues.get(option);
		if (values == null) {
			return null;
		} else {
			return values.get(0);
		}
	}

	public List<String> getAllOptionValues(String option) 
	{
		return   optionsValues.get(option);
	}
	
	protected void putOptionWithArg(Option o, String option, String value) throws CliSyntaxException
	{
		List<String> valuesList = optionsValues.get(option);
		if (valuesList == null) {
			valuesList = new ArrayList<String>();
			valuesList.add(value);
			optionsValues.put(option, valuesList);
		} else {
			if (!o.allowMultipleValues)
				throw new CliSyntaxException("Multiple value for option " + option);

			valuesList.add(value);
		}
	}

	protected void putOption(String option)
	{
		optionsWithoutValues.add(option);
	}
	
	protected void fillLongOptions(String longOptions[]) throws OptionsSyntaxException
	{
		for (String o : longOptions) {
			boolean requireArg = false;
			boolean allowMutipleValues = false;
			if (o.endsWith(":") || o.endsWith("#")) {
				requireArg = true;
				if (o.length() == 1) {
					throw new OptionsSyntaxException(o);
				}
				o = o.substring(0, o.length() - 1);
				allowMutipleValues = o.endsWith("#");
			}
			for (char c : o.toCharArray()) {
				if (!Character.isLetter(c)) {
					throw new OptionsSyntaxException(o);
				}
			}
			longOptionsMap.put(o, new Option(o, requireArg, allowMutipleValues));
		}
	}
	
	protected void parseShortOptionsString(String shortOptions) throws OptionsSyntaxException
	{
		
		Character currOption = null;
		for (char c : shortOptions.toCharArray()) {
			if (Character.isLetter(c)) {
				if (currOption != null) {
					String optionString = Character.toString(currOption.charValue());
					Option o = new Option(optionString, false, true);
					shortOptionsMap.put(optionString, o);
				}
				currOption = c;
			} else if (c == ':' || c == '#') {
				if (currOption != null) {
					String optionString = Character.toString(currOption.charValue());
					Option o = new Option(optionString, true, c == '#');
					shortOptionsMap.put(optionString, o);
					currOption = null;
				} else {
					throw new OptionsSyntaxException(shortOptions);
				}
			} else {
				throw new OptionsSyntaxException(shortOptions);
			}
		}
		if (currOption != null) {
			String optionString = Character.toString(currOption.charValue());
			Option o = new Option(optionString, false, true);
			shortOptionsMap.put(optionString, o);
		}
	}
}

package clioptions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import clioptions.exceptions.creation.LongOptionError;
import clioptions.exceptions.creation.OptionError;
import clioptions.exceptions.creation.ShortOptionsError;
import clioptions.exceptions.parsing.MixedOptionParsingException;
import clioptions.exceptions.parsing.MultipleOptionValuesException;
import clioptions.exceptions.parsing.NoArgumentParsingException;
import clioptions.exceptions.parsing.ParsingException;
import clioptions.exceptions.parsing.SingleDashParsingException;
import clioptions.exceptions.parsing.UnknownOptionParsingException;




/** CliOptions is a class providing Unix-like CLI options parsing.
 * 
 * <p>The class has several constructors, accepting short option descriptor string and long 
 * options descriptors in different combinations. The short option descriptor string consists
 * of single-letter options, with optional qualifiers ':' and '#' ':' qualifier indicates option
 * with argument, the option may appear only once; '#' qualifier indicates option with argument
 * that may appear several times in the argument list. Options without arguments may appear several 
 * time in the argument list and can be combined in a single argument. Argument '--' denotes end of options list.
 * </p>
 * <p>
 * For example, short option descriptor line "abcd:e#" implies that the following command lines are valid:<br/>
 * <pre>-a -b -d foo arg1 arg2
 *-ab -d foo -e bar1 -e bar2 arg1 arg2
 *-ab -c -c -d foo -- -arg1 arg2 </pre>
 *  in the last case, <tt>-arg1</tt> is treated as argument and not part of options list.</p>
 *  <p>Long options can be passed as array or List, in both cases long option is presented as separate 
 *  entity. The same ':' and '#' qualifiers may be applied to the long option entry.</p> 
 *  For example, the  long options set <tt>{ "aaa". "bbb", "ccc:", "ddd#"}</tt> implies that the following
 *  command line is valid:
 *  <tt>--aaa --bbb --ccc foo --ddd bar1 --ddd bar2 arg1 arg2</tt> 
 *  -- terminates option parsing both with long and short options. Short and long options can be combined.</p>
 *  <p> For convenience, long options can be passed as array or Collection<String>. The following two fragments are equivalent:
 *  <pre>CliOptions options = new CliOptions(Arrays.asList("aaa", "bbb:", "ccc#", "ddd"));</pre>
 *  and
 *  <pre>String longOptionsDescription[] = {"aaa", "bbb:", "ccc#", "ddd"};
 * CliOptions options = new CliOptions(longOptionsDescription);</pre></p>
 * <p>Syntax errors in option descriptors cause OptionException to be raised from the CliOptions constructors.</p>
 * 
 * <p>After CliOptions instance is instantiated, it can be used for parsing the argument list and collecting options values.
 * The typical 
 * 
 * @author      Joseph Hindin
 */

public class CliOptions {
	protected HashMap<String, Option> longOptionsMap = new HashMap<String, Option>();
	protected HashMap<String, Option> shortOptionsMap = new HashMap<String, Option>();
	protected HashMap<String, List<String>> optionsValues = new HashMap<String, List<String>>();
	protected HashSet<String> optionsWithoutValues = new HashSet<String>();
	protected String remainingArgs[];

	/**
	 * Class constructor accepting short option descriptor string.
	 * @throws OptionError
	 */

	public CliOptions(String shortOptions) throws OptionError {
		parseShortOptionsString(shortOptions);
	}

	/**
	 * Class constructor accepting long options descriptors as array.
	 * @throws OptionError
	 */
	public CliOptions(String longOptions[]) throws OptionError {
		fillLongOptions(longOptions);
	}

	/**
	 * Class constructor accepting long options descriptors as List.
	 * @throws OptionError
	 */
	public CliOptions(Collection<String> longOptions) throws OptionError {
		String o[] = new String[longOptions.size()];
		o = longOptions.toArray(o);
		fillLongOptions(o);
	}

	/**
	 * Class constructor accepting short options descriptor string and long options descriptors as array.
	 * @throws OptionError
	 */
	public CliOptions(String shortOptions, String longOptions[])
			throws OptionError {
		parseShortOptionsString(shortOptions);
		fillLongOptions(longOptions);
	}

	/**
	 * Class constructor accepting short options descriptor string and long options descriptors as list.
	 * @throws OptionError
	 */
	public CliOptions(String shortOptions, Collection<String> longOptions)
			throws OptionError {
		parseShortOptionsString(shortOptions);

		String o[] = new String[longOptions.size()];
		o = longOptions.toArray(o);
		fillLongOptions(o);
	}

	/**
	 * Parsing command line; encountered options are accumulated internally for later querying.
	 * @throws ParsingException
	 */
	public void parse(String args[]) throws ParsingException {
		parse(args, null);
	}

	/**
	 * Parsing command line; encountered options are accumulated internally for later querying, with
	 * listener. One of CliOptionsListener methods is invoked on each successfully recognized option.
	 * @throws ParsingException
	 */
	public void parse(String args[], CliOptionsListener listener)
			throws ParsingException {

		remainingArgs = null;
		optionsValues = new HashMap<String, List<String>>();
		optionsWithoutValues = new HashSet<String>();

		for (int i = 0; i < args.length; i++) {
			if (args[i].charAt(0) == '-') {
				if (args[i].length() == 1)
					throw new SingleDashParsingException();

				if (args[i].equals("--")) {
					remainingArgs = Arrays
							.copyOfRange(args, i + 1, args.length);
					return;
				}

				if (args[i].charAt(1) == '-') {
					String longOption = args[i].substring(2);
					Option o = longOptionsMap.get(longOption);
					if (o == null)
						throw new UnknownOptionParsingException(longOption);

					if (o.requireValue) {
						if (i == (args.length - 1))
							throw new NoArgumentParsingException(longOption);

						String value = args[++i];
						if (listener != null)
							listener.optionWithArg(longOption, value);

						putOptionWithArg(o, longOption, value, listener);
					} else {
						if (listener != null)
							listener.option(longOption);
						putOption(longOption, listener);
					}
				} else {
					for (int j = 1; j < args[i].length(); j++) {
						char shortOption = args[i].charAt(j);
						Option o = shortOptionsMap.get(Character.toString(shortOption));
						if (o == null)
							throw new UnknownOptionParsingException(Character.toString(shortOption));

						if (o.requireValue) {
							if (args[i].length() != 2)
								throw new MixedOptionParsingException(args[i].substring(1));

							if (i == (args.length - 1))
								throw new NoArgumentParsingException(Character.toString(shortOption));

							String value = args[++i];
							if (listener != null)
								listener.optionWithArg(Character
										.toString(shortOption), value);

							putOptionWithArg(o,
									Character.toString(shortOption), value,
									listener);
							break;
						} else {
							putOption(Character.toString(shortOption), listener);
						}
					}
				}
			} else { // no -
				remainingArgs = Arrays.copyOfRange(args, i, args.length);
				return;
			}
		}
		remainingArgs = new String[0];
	}

	/** Returns array of remaining arguments. Value valid only after successful parse invocation. */
	public String[] getRemaningArgs() {
		return remainingArgs;
	}

	/** Query whether given options is set. Value valid only after successful parse invocation. */
	public boolean isOptionSet(String option) {
		return optionsWithoutValues.contains(option);
	}

	/** Query value for option with argument. For options with multiple arguments, returns first value.
	 *  Value valid only after successful parse invocation. */
	public String getOptionValue(String option) {
		List<String> values = optionsValues.get(option);
		if (values == null) {
			return null;
		} else {
			return values.get(0);
		}
	}

	/** Query all values for given option. Value valid only after successful parse invocation. */
	public List<String> getAllOptionValues(String option) {
		return optionsValues.get(option);
	}

	protected void putOptionWithArg(Option o, String option, String value,
			CliOptionsListener listener) throws ParsingException {
		if (listener != null)
			listener.optionWithArg(option, value);

		List<String> valuesList = optionsValues.get(option);
		if (valuesList == null) {
			valuesList = new ArrayList<String>();
			valuesList.add(value);
			optionsValues.put(option, valuesList);
		} else {
			if (!o.allowMultipleValues)
				throw new MultipleOptionValuesException(option);

			valuesList.add(value);
		}
	}

	protected void putOption(String option, CliOptionsListener listener) {
		if (listener != null)
			listener.option(option);
		optionsWithoutValues.add(option);
	}

	protected void fillLongOptions(String longOptions[])
			throws OptionError {
		for (String o : longOptions) {
			boolean requireArg = false;
			boolean allowMutipleValues = false;
			if (o.endsWith(":") || o.endsWith("#")) {
				requireArg = true;
				if (o.length() == 1) {
					throw new LongOptionError(o);
				}
				allowMutipleValues = o.endsWith("#");
				o = o.substring(0, o.length() - 1);
			}
			for (char c : o.toCharArray()) {
				if (!Character.isLetter(c)) {
					throw new LongOptionError(o);
				}
			}
			longOptionsMap
					.put(o, new Option(o, requireArg, allowMutipleValues));
		}
	}

	protected void parseShortOptionsString(String shortOptions)
			throws OptionError {

		Character currOption = null;
		for (char c : shortOptions.toCharArray()) {
			if (Character.isLetter(c)) {
				if (currOption != null) {
					String optionString = Character.toString(currOption
							.charValue());
					Option o = new Option(optionString, false, true);
					shortOptionsMap.put(optionString, o);
				}
				currOption = c;
			} else if (c == ':' || c == '#') {
				if (currOption != null) {
					String optionString = Character.toString
					(currOption.charValue());
					Option o = new Option(optionString, true, c == '#');
					shortOptionsMap.put(optionString, o);
					currOption = null;
				} else {
					throw new ShortOptionsError(shortOptions);
				}
			} else {
				throw new ShortOptionsError(shortOptions);
			}
		}
		if (currOption != null) {
			String optionString = Character.toString(currOption.charValue());
			Option o = new Option(optionString, false, true);
			shortOptionsMap.put(optionString, o);
		}
	}
}

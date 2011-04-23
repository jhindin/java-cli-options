package clioptions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/** CliOptions is a class providing Unix-like CLI options parsing.
 * 
 * <p>The class has several constructors, accepting short option descriptor string and long 
 * options descriptors in different combinations. The short option descriptor string consists
 * of single-letter options, with optional qualifiers ':' and '#' ':' qualifier indicates option
 * with argument, the option may appear only once; '#' qualifier indicates option with argument
 * that may appear several times in the argument list. Options without arguments may appear several 
 * time in the argument list and can be combined in a singe argument. Argument '--' denotes end of options list.
 * </p>
 * <p>
 * For example, short option descriptor line "abcd:e#" assume that the following command line are legal:<br/>
 * <pre>-a -b -d foo arg1 arg2
 *-ab -d foo -e bar1 -e bar2 arg1 arg2
 *-ab -c -c -d foo -- -arg1 arg2 </pre>
 *  in the last case, -arg1 is treated as argument and not part of options list.</p>
 *  <p>Long options can be passed as array or List, in both cases long option is presented as separate 
 *  entity. The same ':' and '#' qualifiers may be applied to the long option entry.</p> 
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
	 * @throws OptionsSyntaxException
	 */

	public CliOptions(String shortOptions) throws OptionsSyntaxException {
		parseShortOptionsString(shortOptions);
	}

	/**
	 * Class constructor accepting long options descriptors as array.
	 * @throws OptionsSyntaxException
	 */
	public CliOptions(String longOptions[]) throws OptionsSyntaxException {
		fillLongOptions(longOptions);
	}

	/**
	 * Class constructor accepting long options descriptors as List.
	 * @throws OptionsSyntaxException
	 */
	public CliOptions(List<String> longOptions) throws OptionsSyntaxException {
		String o[] = new String[longOptions.size()];
		o = longOptions.toArray(o);
		fillLongOptions(o);
	}

	/**
	 * Class constructor accepting short options descriptor string and long options descriptors as array.
	 * @throws OptionsSyntaxException
	 */
	public CliOptions(String shortOptions, String longOptions[])
			throws OptionsSyntaxException {
		parseShortOptionsString(shortOptions);
		fillLongOptions(longOptions);
	}

	/**
	 * Class constructor accepting short options descriptor string and long options descriptors as list.
	 * @throws OptionsSyntaxException
	 */
	public CliOptions(String shortOptions, List<String> longOptions)
			throws OptionsSyntaxException {
		parseShortOptionsString(shortOptions);

		String o[] = new String[longOptions.size()];
		o = longOptions.toArray(o);
		fillLongOptions(o);
	}

	/**
	 * Parsing command line; encountered options are accumulated internally for later querying.
	 * @throws CliSyntaxException
	 */
	public void parse(String args[]) throws CliSyntaxException {
		parse(args, null);
	}

	/**
	 * Parsing command line; encountered options are accumulated internally for later querying, with
	 * listener. One of CliOptionsListener methods is invoked on each successfully recognized option.
	 * @throws CliSyntaxException
	 */
	public void parse(String args[], CliOptionsListener listener)
			throws CliSyntaxException {

		remainingArgs = null;
		optionsValues = new HashMap<String, List<String>>();
		optionsWithoutValues = new HashSet<String>();

		for (int i = 0; i < args.length; i++) {
			if (args[i].charAt(0) == '-') {
				if (args[i].length() == 1)
					throw new CliSyntaxException("single - argument");

				if (args[i].equals("--")) {
					remainingArgs = Arrays
							.copyOfRange(args, i + 1, args.length);
					return;
				}

				if (args[i].charAt(1) == '-') {
					String longOption = args[i].substring(2);
					Option o = longOptionsMap.get(longOption);
					if (o == null)
						throw new CliSyntaxException("unknown option "
								+ longOption);

					if (o.requireValue) {
						if (i == (args.length - 1))
							throw new CliSyntaxException("option " + longOption
									+ " without argument");

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
						Option o = shortOptionsMap.get(Character
								.toString(shortOption));
						if (o == null)
							throw new CliSyntaxException("unknown option "
									+ shortOption);

						if (o.requireValue) {
							if (args[i].length() != 2)
								throw new CliSyntaxException("option "
										+ shortOption + " require value,"
										+ " can't be set with other options");

							if (i == (args.length - 1))
								throw new CliSyntaxException("option "
										+ shortOption + " without argument");

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
			CliOptionsListener listener) throws CliSyntaxException {
		if (listener != null)
			listener.optionWithArg(option, value);

		List<String> valuesList = optionsValues.get(option);
		if (valuesList == null) {
			valuesList = new ArrayList<String>();
			valuesList.add(value);
			optionsValues.put(option, valuesList);
		} else {
			if (!o.allowMultipleValues)
				throw new CliSyntaxException("Multiple value for option "
						+ option);

			valuesList.add(value);
		}
	}

	protected void putOption(String option, CliOptionsListener listener) {
		if (listener != null)
			listener.option(option);
		optionsWithoutValues.add(option);
	}

	protected void fillLongOptions(String longOptions[])
			throws OptionsSyntaxException {
		for (String o : longOptions) {
			boolean requireArg = false;
			boolean allowMutipleValues = false;
			if (o.endsWith(":") || o.endsWith("#")) {
				requireArg = true;
				if (o.length() == 1) {
					throw new OptionsSyntaxException(o);
				}
				allowMutipleValues = o.endsWith("#");
				o = o.substring(0, o.length() - 1);
			}
			for (char c : o.toCharArray()) {
				if (!Character.isLetter(c)) {
					throw new OptionsSyntaxException(o);
				}
			}
			longOptionsMap
					.put(o, new Option(o, requireArg, allowMutipleValues));
		}
	}

	protected void parseShortOptionsString(String shortOptions)
			throws OptionsSyntaxException {

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
					String optionString = Character.toString(currOption
							.charValue());
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

package example;

import java.util.Arrays;
import java.util.Collection;

import clioptions.CliOptions;
import clioptions.CliOptionsListener;
import clioptions.exceptions.parsing.ParsingException;

public class Main {
	public static void main(String args[]) {
		CliOptions cliOptions = new CliOptions("ab:c#dh", Arrays.asList("aaa",
				"bbb:", "ccc#", "ddd", "help"));

		try {
			cliOptions.parse(args);
			if (cliOptions.isOptionSet("h") || cliOptions.isOptionSet("help")) {
				usage();
				return;
			}

			String value;
			Collection<String> values;

			if (cliOptions.isOptionSet("a"))
				System.out.println("a set");

			if ((value = cliOptions.getOptionValue("b")) != null)
				System.out.println("b has value " + value);

			if ((value = cliOptions.getOptionValue("c")) != null)
				System.out.println("among -c values there is " + value);

			if ((values = cliOptions.getAllOptionValues("c")) != null) {
				System.out.print("All c values: [");
				for (String v : values)
					System.out.print(v + " ");

				System.out.println("]");
			}

			if (cliOptions.isOptionSet("d"))
				System.out.println("d set");

			if (cliOptions.isOptionSet("aaaa"))
				System.out.println("aaa set");

			if ((value = cliOptions.getOptionValue("bbb")) != null)
				System.out.println("bbb has value " + value);

			if ((value = cliOptions.getOptionValue("ccc")) != null)
				System.out.println("among ccc values there is " + value);

			if ((values = cliOptions.getAllOptionValues("ccc")) != null) {
				System.out.print("All " + values.size() + " ccc values: [");
				for (String v : values)
					System.out.print(v + " ");

				System.out.println("]");
			}

			if (cliOptions.isOptionSet("ddd"))
				System.out.println("ddd set");

			String rargs[] = cliOptions.getRemaningArgs();

			System.out.print(rargs.length + " arguments ");
			if (rargs.length > 0) {
				System.out.print("[");
				for (String a : rargs)
					System.out.print(a + " ");
				System.out.print("]");
			}
			System.out.println();
		} catch (ParsingException e) {
			System.err.println(e.getMessage());
			usage();
		}
		

		System.out.println("With listener");

		try {
			cliOptions.parse(args, new CliOptionsListener() {
				@Override
				public void optionWithArg(String option, String value) {
					System.out.println(option + " with value " + value);
				}

				@Override
				public void option(String option) {
					System.out.println(option);
				}
			});
		} catch (ParsingException e) {
			System.err.println(e.getMessage());
			usage();
		}
	}

	static void usage() {
		System.err
				.println("arguments : <|-adh]> [<-b arg>] [<-c arg>*] [--aaa] [--bbb arg] [-ccc arg] [-ddd] [--help]");
	}

}

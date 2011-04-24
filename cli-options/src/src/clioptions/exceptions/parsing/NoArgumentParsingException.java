package clioptions.exceptions.parsing;

/** Exception thrown by {@link clioptions.CliOptions} parser when option requires argument
 * but no argument is given
 * Argument-less option is reported as {@link ParsingException} error cause.
 * 
 * @author Joseph Hindin
 *
 */

public class NoArgumentParsingException extends ParsingException {

	public NoArgumentParsingException(String option) {
		super("No argument", option);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7134553541263695612L;

}

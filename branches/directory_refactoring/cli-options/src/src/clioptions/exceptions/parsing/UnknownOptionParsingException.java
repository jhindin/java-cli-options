package clioptions.exceptions.parsing;

/** Exception thrown by {@link clioptions.CliOptions} parser when unknown option is found.
 * Unknown option is reported as {@link ParsingException} error cause.
 * 
 * @author Joseph Hindin
 *
 */
public class UnknownOptionParsingException extends ParsingException {

	public UnknownOptionParsingException(String option) {
		super("Unknown option " + option, option);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7934893834367076661L;

}

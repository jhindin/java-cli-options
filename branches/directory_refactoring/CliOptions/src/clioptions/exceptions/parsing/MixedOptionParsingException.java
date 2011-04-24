package clioptions.exceptions.parsing;

/** Exception thrown by {@link clioptions.CliOptions} parser when short option
 * requiring argument is encountered in single argument with other options.
 * 
 * Problematic option is reported as {@link ParsingException} error cause.
 * 
 * @author Joseph Hindin
 *
 */
public class MixedOptionParsingException extends ParsingException {

	public MixedOptionParsingException(String errorCause) {
		super("Options with and without arguments mixed ", errorCause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4903385561041693549L;

}

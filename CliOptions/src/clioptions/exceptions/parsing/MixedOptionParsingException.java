package clioptions.exceptions.parsing;

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

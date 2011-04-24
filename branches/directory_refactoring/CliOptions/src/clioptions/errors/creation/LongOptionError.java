package clioptions.errors.creation;

/**
 * Error thrown when invalid long option description is passed to {@link clioptions.CliOptions} constructor
 * @author Joseph Hindin
 *
 */

public class LongOptionError extends OptionError {

	/**
	 * longOption parameter is passed upward to {@link OptionError} as errorCause
	 * @param longOption
	 */
	public LongOptionError(String longOption) {
		super("Invalid long options " + longOption,
				longOption);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -488770891863981833L;

}

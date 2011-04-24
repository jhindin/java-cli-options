package clioptions.errors.creation;

/**
 * Error thrown when invalid short options description string is passed to {@link clioptions.CliOptions} constructor
 * @author Joseph Hindin
 *
 */


public class ShortOptionsError extends OptionError {

	/**
	 * shortOptionsDescriptor parameter is passed upward to {@link OptionError}
	 * as errorCause
	 * 
	 * @param shortOptionsDescriptor
	 */
	public ShortOptionsError(String shortOptionsDescriptor) {
		super("Invalid short option descriptor " + shortOptionsDescriptor,
				shortOptionsDescriptor);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -488770891863981833L;

}

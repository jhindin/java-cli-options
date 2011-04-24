package clioptions.errors.creation;


public class ShortOptionsError extends OptionError {

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

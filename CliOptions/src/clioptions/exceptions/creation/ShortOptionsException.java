package clioptions.exceptions.creation;


public class ShortOptionsException extends OptionException {

	public ShortOptionsException(String shortOptionsDescriptor) {
		super("Invalid short option descriptor " + shortOptionsDescriptor,
				shortOptionsDescriptor);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -488770891863981833L;

}

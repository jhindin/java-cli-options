package clioptions.exceptions.creation;


public class LongOptionError extends OptionError {

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

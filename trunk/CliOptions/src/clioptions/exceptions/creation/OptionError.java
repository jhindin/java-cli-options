package clioptions.exceptions.creation;

public class OptionError extends Error {
	protected String errorCause;
	
	public OptionError(String msg, String errorCause) {
		super(msg); 
	}
	
	public String getErrorCause() {
		return errorCause;
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -488770891863981833L;

}

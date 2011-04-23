package clioptions.exceptions.creation;

public class OptionException extends Exception {
	protected String errorCause;
	
	public OptionException(String msg, String errorCause) {
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

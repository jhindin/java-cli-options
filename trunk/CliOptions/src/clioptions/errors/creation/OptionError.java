package clioptions.errors.creation;

/** Ancestor class for error thrown by {@link clioptions.CliOptions} constructors
 * 
 * @author Joseph Hindin
 *
 */

public class OptionError extends Error {
	protected String errorCause;
	
	/**
	 * msg and error cause are stored for later retrieval 
	 * @param msg
	 * @param errorCause
	 */
	OptionError(String msg, String errorCause) {
		super(msg); 
	}
	
	/**
	 * 
	 * @return error cause, passed into constructor
	 */
	public String getErrorCause() {
		return errorCause;
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -488770891863981833L;

}

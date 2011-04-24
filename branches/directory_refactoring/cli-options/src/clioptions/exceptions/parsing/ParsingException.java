package clioptions.exceptions.parsing;

/** Ancestor class for exceptions thrown by {@link clioptions.CliOptions} parser 
 * 
 * @author Joseph Hindin
 *
 */
public class ParsingException extends Exception {
	protected String errorCause;
	
	ParsingException(String reason, String errorCause) {
		super("Invalid CLI syntax: " + reason); 
		this.errorCause = errorCause;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @return error cause reported when exception was thrown
	 */
	public String getErrorCause()
	{
		return errorCause;
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -488770891863981833L;

}

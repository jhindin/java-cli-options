package clioptions.exceptions.parsing;

public class ParsingException extends Exception {
	protected String errorCause;
	
	public ParsingException(String reason, String option) {
		super("Invalid CLI syntax: " + reason); 
		this.errorCause = option;
		// TODO Auto-generated constructor stub
	}
	
	public String getErrorCause()
	{
		return errorCause;
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -488770891863981833L;

}

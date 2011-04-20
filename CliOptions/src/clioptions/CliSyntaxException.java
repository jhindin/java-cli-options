package clioptions;

public class CliSyntaxException extends Exception {
	public CliSyntaxException(String reason) {
		super("Invalid CLI syntax: " + reason); 
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -488770891863981833L;

}

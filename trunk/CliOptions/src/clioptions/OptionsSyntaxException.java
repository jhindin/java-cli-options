package clioptions;

public class OptionsSyntaxException extends Exception {
	protected String optionString;
	
	public OptionsSyntaxException(String optionString) {
		this.optionString = optionString; 
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getMessage() {
		return "Invalid short options string " + optionString;
	}
	
	public String getOptionString()
	{
		return optionString;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -488770891863981833L;

}

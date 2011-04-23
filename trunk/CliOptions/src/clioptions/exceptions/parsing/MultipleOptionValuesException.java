package clioptions.exceptions.parsing;

public class MultipleOptionValuesException extends ParsingException {

	public MultipleOptionValuesException(String option) {
		super("Mutliple value for option", option);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7671966422242046846L;

}

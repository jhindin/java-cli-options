package clioptions.exceptions.parsing;

/** Exception thrown by {@link clioptions.CliOptions} parser when option defined
 * as single argument is encountered several tumes
 * Problematic option is reported as {@link ParsingException} error cause.
 * 
 * @author Joseph Hindin
 *
 */
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

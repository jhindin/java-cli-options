package clioptions.exceptions.parsing;

/**
 * Exception thrown by {@link clioptions.CliOptions} parser when single '-' is found 
 * The exception put empty string as error cause of {@link ParsingException}
 * 
 * @author Joseph Hindin
 *
 */

public class SingleDashParsingException extends ParsingException {

	public SingleDashParsingException() {
		super("Single dash", "");
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3530065960041333585L;

}

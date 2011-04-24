package clioptions;

/** 
 * Listener interface for CLI options parser
 * 
 * @author Joseph Hindin
 *
 */
public interface CliOptionsListener {
	/* Method called for option without values
	 * @param option
	 */
	public void option(String option);
	
	/* Method called for option wit value
	 * @param option
	 * @param value
	 */
	public void optionWithArg(String option, String value);
}

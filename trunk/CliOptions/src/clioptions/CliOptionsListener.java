package clioptions;

public interface CliOptionsListener {
	public void option(String option);
	public void optionWithArg(String option, String value);
}

package clioptions;

class Option {
	boolean requireValue, allowMultipleValues;
	String option;
	
	public Option(String option, boolean requireValue, boolean allowMultipleValues) {
		this.option = option;
		this.requireValue = requireValue;
		this.allowMultipleValues = allowMultipleValues;
	}

	public Option(Character option, boolean requireValue) {
		this.option = Character.toString(option.charValue());
		this.requireValue = requireValue;
	}

}

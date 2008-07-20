package no.knubo.bok.client.validation;

public class YearValidator extends ValidatorBase {

	public YearValidator(String errorText) {
		super(errorText);
	}

	public YearValidator(String error, String mouseover) {
		this(error);
		this.mouseOver = mouseover;
	}

	protected boolean validate(Validateable val) {
		int year = 0;

		/* Handle blank using mandatory validators. */
		if (val.getText().length() == 0) {
			return true;
		}

		try {
			year = Integer.parseInt(val.getText());
		} catch (NumberFormatException e) {
			return false;
		}

		return year < 9999 && year > 0;
	}
}

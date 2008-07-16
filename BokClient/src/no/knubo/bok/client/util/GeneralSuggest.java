package no.knubo.bok.client.util;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class GeneralSuggest implements Suggestion {

	private final String display;
	private final String replace;

	public GeneralSuggest(String display, String replace) {
		this.display = display;
		this.replace = replace;

	}

	public GeneralSuggest(String suggest) {
		this.display = suggest;
		this.replace = suggest;
	}

	public String getDisplayString() {
		return display;
	}

	public String getReplacementString() {
		return replace;
	}

}
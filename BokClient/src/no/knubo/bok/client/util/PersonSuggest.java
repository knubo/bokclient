package no.knubo.bok.client.util;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class PersonSuggest implements Suggestion {

	private final String display;
	private final String replace;

	public PersonSuggest(String display, String replace) {
		this.display = display;
		this.replace = replace;

	}

	public PersonSuggest(String suggest) {
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
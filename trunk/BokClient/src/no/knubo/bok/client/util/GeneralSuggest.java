package no.knubo.bok.client.util;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class GeneralSuggest implements Suggestion {

	private final String display;
	private final String replace;
	private final int id;
	private final Picked picked;

	public GeneralSuggest(String display, String replace, int id, Picked picked) {
		this.display = display;
		this.replace = replace;
		this.id = id;
		this.picked = picked;

	}

	public GeneralSuggest(String suggest, int id, Picked picked) {
		this.display = suggest;
		this.replace = suggest;
		this.id = id;
		this.picked = picked;
	}

	public String getDisplayString() {
		return display;
	}

	public String getReplacementString() {
		if(picked != null) {
			picked.idPicked(id, replace);
		}
		
		return replace;
	}

}
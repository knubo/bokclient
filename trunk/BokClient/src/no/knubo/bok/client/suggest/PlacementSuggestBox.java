package no.knubo.bok.client.suggest;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.views.editors.NamedEditor;

import com.google.gwt.user.client.ui.SuggestOracle;

public class PlacementSuggestBox extends GeneralSuggestBox {

	public PlacementSuggestBox(Constants constants, Messages messages,
			Elements elements) {
		super("placement", constants, messages, elements);
	}

	public SuggestOracle getOracle() {
		return PlacementSuggestBuilder.createPlacementOracle(constants,
				messages, picked);
	}

	@Override
	public void openEditor() {
		NamedEditor personEditor = NamedEditor.getInstance("placements",elements,
				constants, messages);
		personEditor.setReceiver(picked);
	}

}

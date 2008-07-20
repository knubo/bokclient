package no.knubo.bok.client.suggest;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.views.editors.NamedEditor;

import com.google.gwt.user.client.ui.SuggestOracle;

public class SeriesSuggestBox extends GeneralSuggestBox {

	public SeriesSuggestBox(Constants constants, Messages messages,
			Elements elements) {
		super("category", constants, messages, elements);
	}

	public SuggestOracle getOracle() {
		return SeriesSuggestBuilder.createSeriesOracle(constants, messages, picked);
	}

	@Override
	public void openEditor() {
		NamedEditor personEditor = NamedEditor.getInstance("series",elements,
				constants, messages);
		personEditor.setReceiver(picked);
	}


}

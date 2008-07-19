package no.knubo.bok.client.suggest;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;

import com.google.gwt.user.client.Window;
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
		Window.alert("To be made");
	}

}

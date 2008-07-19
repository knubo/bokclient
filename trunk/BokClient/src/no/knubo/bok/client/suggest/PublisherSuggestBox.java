package no.knubo.bok.client.suggest;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SuggestOracle;

public class PublisherSuggestBox extends GeneralSuggestBox {

	public PublisherSuggestBox(Constants constants, Messages messages,
			Elements elements) {
		super("publisher", constants, messages, elements);
	}

	public SuggestOracle getOracle() {
		return PublisherSuggestBuilder.createPublisherOracle(constants, messages, picked);
	}

	@Override
	public void openEditor() {
		Window.alert("To be made");
	}

}

package no.knubo.bok.client.suggest;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.views.editors.PersonEditor;

import com.google.gwt.user.client.ui.SuggestOracle;

public class PersonSuggestBox extends GeneralSuggestBox {

	public PersonSuggestBox(String type, Constants constants,
			Messages messages, Elements elements) {
		super(type, constants, messages, elements);
	}

	public void openEditor() {
		PersonEditor personEditor = PersonEditor.getInstance(elements,
				constants, messages);
		personEditor.setReceiver(picked);
	}

	@Override
	public SuggestOracle getOracle() {
		return PersonSuggestBuilder.createPeopleOracle(constants, messages,
				type, picked);
	}

}

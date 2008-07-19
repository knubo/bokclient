package no.knubo.bok.client.suggest;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SuggestOracle;

public class CategorySuggestBox extends GeneralSuggestBox {

	public CategorySuggestBox(Constants constants, Messages messages,
			Elements elements) {
		super("category", constants, messages, elements);
	}

	public SuggestOracle getOracle() {
		return CategorySuggestBuilder.createCategoryOracle(constants, messages, picked);
	}

	@Override
	public void openEditor() {
		Window.alert("To be made");
	}

}

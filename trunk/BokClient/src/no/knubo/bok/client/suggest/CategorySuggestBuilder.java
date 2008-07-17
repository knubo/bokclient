package no.knubo.bok.client.suggest;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.util.DelayedServerOracle;

import com.google.gwt.user.client.ui.SuggestOracle;

public class CategorySuggestBuilder {

	public static SuggestOracle createCategoryOracle(final Constants constants,
			final Messages messages) {

		return new DelayedServerOracle() {

			@Override
			public void fetchSuggestions() {
				NameSuggestFetcher.fetch(constants, messages, currentRequest,
						currentCallback, "categories");
			}
		};
	}

}

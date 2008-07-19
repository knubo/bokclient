package no.knubo.bok.client.suggest;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.util.DelayedServerOracle;
import no.knubo.bok.client.util.Picked;

import com.google.gwt.user.client.ui.SuggestOracle;

public class SeriesSuggestBuilder {

	public static SuggestOracle createSeriesOracle(final Constants constants,
			final Messages messages, final Picked picked) {

		return new DelayedServerOracle() {

			@Override
			public void fetchSuggestions() {
				NameSuggestFetcher.fetch(constants, messages, currentRequest, currentCallback, "series", picked);
			}
		};
	}



}

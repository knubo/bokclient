package no.knubo.bok.client.suggest;

import java.util.LinkedList;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponse;
import no.knubo.bok.client.util.DelayedServerOracle;
import no.knubo.bok.client.util.GeneralSuggest;
import no.knubo.bok.client.util.Picked;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.SuggestOracle;

public class PersonSuggestBuilder {

	public static SuggestOracle createPeopleOracle(final Constants constants,
			final Messages messages, final String type, final Picked picked) {

		return new DelayedServerOracle() {

			@Override
			public void fetchSuggestions() {
				fetch(type, constants, messages, currentRequest,
						currentCallback, picked);
			}

			protected void fetch(String type, Constants constants,
					Messages messages, Request request, Callback callback,
					Picked picked) {
				StringBuffer sb = new StringBuffer();
				sb.append("action=search");
				Util.addPostParam(sb, "search", request.getQuery());
				Util.addPostParam(sb, "limit", String.valueOf(request
						.getLimit()));
				Util.addPostParam(sb, "type", type);

				AuthResponder.post(constants, messages, requestCallback(
						callback, request, picked), sb, "registers/people.php");
			}

			private ServerResponse requestCallback(final Callback callback,
					final Request suggestRequest, final Picked picked) {
				return new ServerResponse() {

					public void serverResponse(JSONValue responseObj) {
						JSONArray array = responseObj.isArray();
						LinkedList<GeneralSuggest> res = new LinkedList<GeneralSuggest>();

						for (int i = 0; i < array.size(); i++) {
							JSONValue value = array.get(i);

							JSONObject object = value.isObject();

							int id = Util.getInt(object.get("id"));
							String firstname = Util
									.str(object.get("firstname"));
							String lastname = Util.str(object.get("lastname"));
							String suggest = lastname + ", " + firstname;
							res.add(new GeneralSuggest(suggest, id, picked));
						}
						callback.onSuggestionsReady(suggestRequest,
								new Response(res));
					}

				};
			}
		};
	}

}

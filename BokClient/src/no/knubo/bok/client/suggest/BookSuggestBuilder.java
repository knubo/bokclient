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
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;

public class BookSuggestBuilder {

	public static SuggestOracle createBookOracle(final Constants constants,
			final Messages messages, final String type, final Picked picked) {
		return new DelayedServerOracle() {

			@Override
			public void fetchSuggestions() {
				StringBuffer sb = new StringBuffer();
				sb.append("action=search");
				Util.addPostParam(sb, "search", currentRequest.getQuery());
				Util.addPostParam(sb, "limit", String.valueOf(currentRequest
						.getLimit()));
				Util.addPostParam(sb, "type", type);

				AuthResponder.post(constants, messages, requestCallback(
						currentCallback, currentRequest, picked, type), sb,
						"registers/books.php");
			}
		};
	}

	static ServerResponse requestCallback(final Callback callback,
			final Request suggestRequest, final Picked picked, final String type) {
		return new ServerResponse() {

			public void serverResponse(JSONValue responseObj) {
				JSONArray array = responseObj.isArray();
				LinkedList<GeneralSuggest> res = new LinkedList<GeneralSuggest>();

				for (int i = 0; i < array.size(); i++) {
					JSONValue value = array.get(i);

					JSONObject object = value.isObject();

					String suggest = null;

					if (type.equals("title")) {
						suggest = Util.str(object.get("title"));
					} else if(type.equals("ISBN")) {
						suggest = Util.str(object.get("ISBN"));
					}
					
					int id = Util.getInt(object.get("id"));
					res.add(new GeneralSuggest(suggest, id, picked));
				}
				callback.onSuggestionsReady(suggestRequest, new Response(res));
			}
		};
	}
}

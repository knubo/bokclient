package no.knubo.bok.client.suggest;

import java.util.LinkedList;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponse;
import no.knubo.bok.client.util.GeneralSuggest;
import no.knubo.bok.client.util.Picked;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;

final public class NameSuggestFetcher {
	private NameSuggestFetcher() {
		/* Empty */
	}

	public static void fetch(Constants constants, Messages messages,
			Request request, Callback callback, String registerType, Picked picked) {
		StringBuffer sb = new StringBuffer();
		sb.append("action=search");
		Util.addPostParam(sb, "search", request.getQuery());
		Util.addPostParam(sb, "limit", String.valueOf(request.getLimit()));

		AuthResponder.post(constants, messages, requestCallback(callback,
				request, picked), sb, "registers/" + registerType + ".php");
	}

	private static ServerResponse requestCallback(final Callback callback,
			final Request suggestRequest, final Picked picked) {
		return new ServerResponse() {

			public void serverResponse(JSONValue responseObj) {
				JSONArray array = responseObj.isArray();
				LinkedList<GeneralSuggest> res = new LinkedList<GeneralSuggest>();

				for (int i = 0; i < array.size(); i++) {
					JSONValue value = array.get(i);

					JSONObject object = value.isObject();

					String category = Util.str(object.get("name"));
					int id = Util.getInt(object.get("id"));
					res.add(new GeneralSuggest(category, id, picked));
				}
				callback.onSuggestionsReady(suggestRequest, new Response(res));
			}

		};
	}
}

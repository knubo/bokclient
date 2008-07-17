package no.knubo.bok.client.suggest;

import java.util.LinkedList;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponse;
import no.knubo.bok.client.util.DelayedServerOracle;
import no.knubo.bok.client.util.GeneralSuggest;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;

public class CategorySuggestBuilder {

	public static SuggestOracle createCategoryOracle(final Constants constants,
			final Messages messages) {

		return new DelayedServerOracle() {

			@Override
			public void fetchSuggestions() {
				fetch(constants, messages, currentRequest, currentCallback);
			}
		};
	}

	protected static void fetch(Constants constants, Messages messages,
			Request request, Callback callback) {
		StringBuffer sb = new StringBuffer();
		sb.append("action=search");
		Util.addPostParam(sb, "search", request.getQuery());
		Util.addPostParam(sb, "limit", String.valueOf(request.getLimit()));

		AuthResponder.post(constants, messages, requestCallback(callback,
				request), sb, "registers/categories.php");
	}

	private static ServerResponse requestCallback(final Callback callback,
			final Request suggestRequest) {
		return new ServerResponse() {

			public void serverResponse(JSONValue responseObj) {
				JSONArray array = responseObj.isArray();
				LinkedList<GeneralSuggest> res = new LinkedList<GeneralSuggest>();

				for (int i = 0; i < array.size(); i++) {
					JSONValue value = array.get(i);

					JSONObject object = value.isObject();

					String category = Util.str(object.get("name"));
					res.add(new GeneralSuggest(category));
				}
				callback.onSuggestionsReady(suggestRequest, new Response(res));
			}

		};
	}

}

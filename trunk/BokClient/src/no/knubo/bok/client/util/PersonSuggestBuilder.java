package no.knubo.bok.client.util;

import java.util.LinkedList;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponse;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;

public class PersonSuggestBuilder {

	public static SuggestOracle createPeopleOracle(final Constants constants,
			final Messages messages, final String type) {

		return new DelayedServerOracle(constants, messages, type);
	}

	protected static void fetchSuggestions(String type, Constants constants,
			Messages messages, Request request, Callback callback) {
		StringBuffer sb = new StringBuffer();
		sb.append("action=search");
		Util.addPostParam(sb, "search", request.getQuery());
		Util.addPostParam(sb, "limit", String.valueOf(request.getLimit()));
		Util.addPostParam(sb, "type", type);

		AuthResponder.post(constants, messages, requestCallback(callback,
				request), sb, "registers/people.php");
	}

	private static ServerResponse requestCallback(final Callback callback,
			final Request suggestRequest) {
		return new ServerResponse() {

			public void serverResponse(JSONValue responseObj) {
				JSONArray array = responseObj.isArray();
				LinkedList<PersonSuggest> res = new LinkedList<PersonSuggest>();

				for (int i = 0; i < array.size(); i++) {
					JSONValue value = array.get(i);

					JSONObject object = value.isObject();

					String firstname = Util.str(object.get("firstname"));
					String lastname = Util.str(object.get("lastname"));
					String suggest = lastname + ", " + firstname;
					res.add(new PersonSuggest(suggest));
				}
				callback.onSuggestionsReady(suggestRequest, new Response(res));
			}

		};
	}

	static class DelayedServerOracle extends SuggestOracle {

		private final Constants constants;
		private final Messages messages;
		private final String type;
		private Request currentRequest;
		private Callback currentCallback;
		private Timer timer;

		DelayedServerOracle(final Constants constants, final Messages messages,
				final String type) {
			this.constants = constants;
			this.messages = messages;
			this.type = type;
		}

		@Override
		public void requestSuggestions(Request request, Callback callback) {
			this.currentRequest = request;
			this.currentCallback = callback;

			startTimer();
		}

		private void startTimer() {
			if (timer == null) {
				timer = new Timer() {

					@Override
					public void run() {
						fetchSuggestions(type, constants, messages,
								currentRequest, currentCallback);
						timer = null;
					}

				};
				timer.schedule(500);
			}
		}

	}
}

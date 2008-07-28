package no.knubo.bok.client.suggest;

import java.util.LinkedList;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponse;
import no.knubo.bok.client.util.DelayedServerOracle;
import no.knubo.bok.client.util.SimpleSuggest;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.SuggestOracle;

public class CoAuthorSuggestBuilder {

    public static SuggestOracle createCoAuthorOracle(final Constants constants, final Messages messages) {

        return new DelayedServerOracle() {

            @Override
            public void fetchSuggestions() {
                fetch(constants, messages, currentRequest, currentCallback);
            }

            protected void fetch(Constants constants, Messages messages, Request request, Callback callback) {
                StringBuffer sb = new StringBuffer();
                sb.append("action=search");
                Util.addPostParam(sb, "search", request.getQuery());
                Util.addPostParam(sb, "limit", String.valueOf(request.getLimit()));
                Util.addPostParam(sb, "type", "coauthor");

                AuthResponder.post(constants, messages, requestCallback(callback, request), sb, "registers/books.php");
            }

            private ServerResponse requestCallback(final Callback callback, final Request suggestRequest) {
                return new ServerResponse() {

                    public void serverResponse(JSONValue responseObj) {
                        JSONArray array = responseObj.isArray();
                        LinkedList<SimpleSuggest> res = new LinkedList<SimpleSuggest>();

                        for (int i = 0; i < array.size(); i++) {
                            JSONValue value = array.get(i);

                            JSONObject object = value.isObject();

                            String coauthor = Util.str(object.get("coauthor"));
                            res.add(new SimpleSuggest(coauthor));
                        }
                        callback.onSuggestionsReady(suggestRequest, new Response(res));
                    }

                };
            }
        };
    }

}

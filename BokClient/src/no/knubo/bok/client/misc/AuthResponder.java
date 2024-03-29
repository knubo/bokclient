package no.knubo.bok.client.misc;

import java.util.ArrayList;

import no.knubo.bok.client.BokGWT;
import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

public class AuthResponder implements RequestCallback {

    private final Constants constants;
    private final ServerResponse callback;
    private final Messages messages;
    private final Logger logger;

    private AuthResponder(Constants constants, Messages messages, ServerResponse callback) {
        this.constants = constants;
        this.messages = messages;
        this.callback = callback;
        if (callback == null) {
            throw new RuntimeException("Callback cannot be null");
        }
        this.logger = new Logger(this.constants);
        BokGWT.setLoading();

    }

    public void onError(Request request, Throwable exception) {
        /* Not needed? */
    }

    public void onResponseReceived(Request request, Response response) {
        BokGWT.setDoneLoading();
        if (response.getStatusCode() == 510) {
            BokGWT.loginMode();
        } else if (response.getStatusCode() == 511) {
            Window.alert(messages.no_access());
        } else if (response.getStatusCode() == 512) {
            logger.error("database", response.getText());
            Window.alert("DB error:" + response.getText());
        } else if (response.getStatusCode() == 513) {
            JSONValue parse = JSONParser.parse(response.getText());
            ArrayList<String> fields = new ArrayList<String>();
            JSONArray array = parse.isArray();

            for (int i = 0; i < array.size(); i++) {
                fields.add(Util.str(array.get(i)));
            }

            if (callback instanceof ServerResponseWithValidation) {
                ((ServerResponseWithValidation) callback).validationError(fields);
            } else {
                Window.alert("Validation error:" + fields);
            }
        } else {
            String data = response.getText();
            if (data == null || data.length() == 0) {
                logger.error("error", "no server response");
                Window.alert(messages.no_server_response());
                return;
            }

            if (callback instanceof ServerResponsePlainText) {
                ((ServerResponsePlainText) callback).plainText(data);
                return;
            }

            data = data.trim();

            JSONValue jsonValue = null;

            try {
                jsonValue = JSONParser.parse(data);
            } catch (Exception e) {
                /* We catch this below in bad return data */
            }

            if (jsonValue == null) {
                if (callback instanceof ServerResponseWithErrorFeedback) {
                    ((ServerResponseWithErrorFeedback) callback).onError();
                } else {
                    logger.error("baddata", data);
                    Window.alert("Bad return data:" + data);
                }
            } else {
                callback.serverResponse(jsonValue);
            }
        }
    }

    public static void get(Constants constants, Messages messages, ServerResponse callback, String url) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, constants.baseurl() + url);

        try {
            builder.sendRequest("", new AuthResponder(constants, messages, callback));
        } catch (RequestException e) {
            Window.alert("Failed to send the request: " + e.getMessage());
        }
    }

    public static void getPlain(Constants constants, Messages messages, ServerResponse callback, String url) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, constants.baseurl() + url);

        try {
            AuthResponder authResponder = new AuthResponder(constants, messages, callback);
            builder.sendRequest("", authResponder);
        } catch (RequestException e) {
            Window.alert("Failed to send the request: " + e.getMessage());
        }
    }

    public static void post(Constants constants, Messages messages, ServerResponse callback, StringBuffer parameters, String url) {

        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, constants.baseurl() + url);

        try {
            builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
            builder.sendRequest(parameters.toString(), new AuthResponder(constants, messages, callback));
        } catch (RequestException e) {
            Window.alert("Failed to send the request: " + e.getMessage());
        }

    }
}

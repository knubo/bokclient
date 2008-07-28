package no.knubo.bok.client.views;

import no.knubo.bok.client.BokGWT;
import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponse;
import no.knubo.bok.client.ui.NamedButton;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Widget;

public class LogoutView extends Composite implements ClickListener, ServerResponse {

    private static LogoutView instance;
    private static Messages messages;
    private static Constants constants;
    private static Elements elements;

    public static LogoutView getInstance(Constants constants, Messages messages,
            Elements elements) {
        LogoutView.messages = messages;
        LogoutView.constants = constants;
        LogoutView.elements = elements;
        if (instance == null) {
            instance = new LogoutView();
        }
        return instance;
    }

    private LogoutView() {
        DockPanel dp = new DockPanel();

        NamedButton logoutButton = new NamedButton("logout", elements.logout());
        logoutButton.addClickListener(this);
        dp.add(logoutButton, DockPanel.NORTH);

        initWidget(dp);
        setTitle(elements.logout());
    }

    public void onClick(Widget sender) {
        StringBuffer sb = new StringBuffer();
        sb.append("action=logout");
        AuthResponder.post(constants, messages, this, sb, "authenticate.php");
    }

    public void serverResponse(JSONValue val) {
        JSONObject obj = val.isObject();

        if ("1".equals(Util.str(obj.get("result")))) {
            BokGWT.normalMode();
        }
    }
}

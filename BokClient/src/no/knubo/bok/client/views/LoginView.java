package no.knubo.bok.client.views;

import no.knubo.bok.client.BokGWT;
import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponse;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends Composite implements ClickListener, ServerResponse {

    private static LoginView me;
    private TextBox userBox;
    private HTML infoLabel;
    private PasswordTextBox passBox;
    private Constants constants;
    private Messages messages;

    public static LoginView getInstance(Elements elements, Constants constants, Messages messages) {
        if (me != null) {
            return me;
        }
        me = new LoginView(elements, constants, messages);

        return me;
    }

    public LoginView(Elements elements, Constants constants, Messages messages) {
        this.constants = constants;
        this.messages = messages;
        DockPanel dp = new DockPanel();
        dp.setStyleName("middle");
        FlexTable table = new FlexTable();
        table.setStyleName("edittable");

        dp.add(table, DockPanel.CENTER);

        Button loginButton = new Button(elements.login());
        loginButton.addClickListener(this);

        userBox = new TextBox();
        userBox.setWidth("12em");
        userBox.setMaxLength(12);
        passBox = new PasswordTextBox();
        passBox.setWidth("12em");
        passBox.addKeyboardListener(new KeyboardListener() {

            public void onKeyDown(Widget sender, char keyCode, int modifiers) {
                /* Not used */
            }

            public void onKeyPress(Widget sender, char keyCode, int modifiers) {
                if (keyCode == KEY_ENTER) {
                    doLogin();
                }
            }

            public void onKeyUp(Widget sender, char keyCode, int modifiers) {
                /* Not used */
            }

        });
        infoLabel = new HTML();
        table.setText(0, 0, elements.login());
        table.getFlexCellFormatter().setColSpan(0, 0, 2);
        table.setText(1, 0, elements.user());
        table.setWidget(1, 1, userBox);
        table.setText(2, 0, elements.password());
        table.setWidget(2, 1, passBox);
        table.setWidget(3, 1, loginButton);
        table.setWidget(4, 1, infoLabel);
        table.getFlexCellFormatter().setColSpan(4, 1, 2);
        userBox.setFocus(true);

        initWidget(dp);
        setTitle(elements.login());
    }

    public void init() {
        passBox.setText("");
        infoLabel.setText("");
        userBox.setText("");
    }

    public void onClick(Widget sender) {
        doLogin();
    }

    private void doLogin() {
        String user = this.userBox.getText();
        String password = this.passBox.getText();

        AuthResponder.get(constants, messages, this, "../../BokServer/services/authenticate.php?user=" + user + "&password=" + password);
    }

    public void serverResponse(JSONValue resonseObj) {
        JSONObject isObject = resonseObj.isObject();

        JSONValue error = isObject.get("error");

        if (error != null) {
            JSONString string = error.isString();
            infoLabel.setText(string.stringValue());
        } else {
            BokGWT.normalMode();
        }
    }

}

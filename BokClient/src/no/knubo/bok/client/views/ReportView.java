package no.knubo.bok.client.views;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponsePlainText;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class ReportView extends Composite {

    private static ReportView me;
    private Constants constants;
    private Messages messages;
    private HTML htmlReport;

    public ReportView(Elements elements, Constants constants, Messages messages) {
        this.constants = constants;
        this.messages = messages;
        htmlReport = new HTML();
        initWidget(htmlReport);
    }

    public static ReportView getInstance(String report, String title, Elements elements, Constants constants, Messages messages) {

        if (me == null) {
            me = new ReportView(elements, constants, messages);
        }
        me.init(report);
        me.setTitle(title);
        return me;
    }

    private void init(String report) {
        ServerResponsePlainText callback = new ServerResponsePlainText() {

            public void plainText(String text) {
                htmlReport.setHTML(text);
            }

            public void serverResponse(JSONValue responseObj) {
            }

        };
        AuthResponder.get(constants, messages, callback, report);

    }

}

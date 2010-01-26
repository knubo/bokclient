package no.knubo.bok.client.views;

import java.util.List;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponsePlainText;
import no.knubo.bok.client.ui.TableRowSelected;
import no.knubo.bok.client.ui.TableUtils;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class ReportView extends Composite {

    private static ReportView me;
    private Constants constants;
    private Messages messages;
    private HTML htmlReport;

    public ReportView(Constants constants, Messages messages) {
        this.constants = constants;
        this.messages = messages;
        htmlReport = new HTML();
        initWidget(htmlReport);
    }

    public static ReportView getInstance(String report, String title, Elements elements, Constants constants, Messages messages) {

        if (me == null) {
            me = new ReportView(constants, messages);
        }
        me.init(report);
        me.setTitle(title);
        return me;
    }

    public void addBookLookup(final ViewCallback viewCallback) {

        TableRowSelected lookup = new TableRowSelected() {

            public void selected(String id) {
                viewCallback.editBook(Integer.parseInt(id));

            }

            public void selectedWithShift(List<String> data) {
                /* Empty */
            }

        };
        TableUtils.addTableSelect(this, lookup);
    }

    private void init(String report) {
        ServerResponsePlainText callback = new ServerResponsePlainText() {

            public void plainText(String text) {
                htmlReport.setHTML(text);
            }

            public void serverResponse(JSONValue responseObj) {
                /* Empty */
            }

        };
        AuthResponder.get(constants, messages, callback, report);

    }

}

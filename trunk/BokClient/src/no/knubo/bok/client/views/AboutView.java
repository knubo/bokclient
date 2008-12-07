package no.knubo.bok.client.views;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponse;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

public class AboutView extends Composite {

    private static AboutView instance;
    private final Messages messages;
    private final Constants constants;
    private FlexTable table;
    private Elements elements;

    /** This must match Version.php's version. */
    public static final String CLIENT_VERSION = "1.21";

    public AboutView(Messages messages, Constants constants, Elements elements) {
        this.messages = messages;
        this.constants = constants;
        this.elements = elements;

        table = new FlexTable();
        table.setStyleName("tableborder");

        DockPanel dp = new DockPanel();

        Label header = new Label();
        header.setText(elements.title_about());
        header.addStyleName("pageheading");
        dp.add(header, DockPanel.NORTH);
        dp.add(table, DockPanel.NORTH);

        table.setText(0, 0, elements.book_count());
        table.setText(1, 0, elements.book_next_user_number());

        initWidget(dp);
        setTitle(elements.menuitem_about()+"-"+CLIENT_VERSION);
    }

    public static AboutView getInstance(Constants constants, Messages messages, Elements elements) {
        if (instance == null) {
            instance = new AboutView(messages, constants, elements);
        }

        return instance;
    }

    public void init() {
        while (table.getRowCount() > 2) {
            table.removeRow(2);
        }
        table.setText(2, 0, elements.book_person_info());

        ServerResponse callback = new ServerResponse() {

            public void serverResponse(JSONValue responseObj) {
                JSONObject object = responseObj.isObject();

                checkServerVersion(object);
                table.setText(0, 1, Util.str(object.get("bookCount")));
                table.setText(1, 1, Util.str(object.get("nextUserNumber")));
                JSONArray arr = object.get("people").isArray();

                for (int i = 0; i < arr.size(); i++) {
                    JSONObject one = arr.get(i).isObject();

                    StringBuffer sb = new StringBuffer();
                    sb.append(Util.str(one.get("c")));
                    sb.append(" som er ");

                    int count = (Util.getBoolean(one.get("author")) ? 1 : 0) + (Util.getBoolean(one.get("editor")) ? 1 : 0)
                            + (Util.getBoolean(one.get("translator")) ? 1 : 0) + (Util.getBoolean(one.get("illustrator")) ? 1 : 0)
                            + (Util.getBoolean(one.get("reader")) ? 1 : 0);

                    if (Util.getBoolean(one.get("author"))) {
                        sb.append(elements.book_author().toLowerCase());
                        addAndComma(sb, --count);
                    }
                    if (Util.getBoolean(one.get("editor"))) {
                        sb.append(elements.book_editor().toLowerCase());
                        addAndComma(sb, --count);
                    }
                    if (Util.getBoolean(one.get("translator"))) {
                        sb.append(elements.book_translator().toLowerCase());
                        addAndComma(sb, --count);
                    }
                    if (Util.getBoolean(one.get("illustrator"))) {
                        sb.append(elements.book_illustrator().toLowerCase());
                        addAndComma(sb, --count);
                    }
                    if (Util.getBoolean(one.get("reader"))) {
                        sb.append(elements.book_read_by().toLowerCase());
                    }
                    sb.append(".");
                    table.setText(2 + i, 1, sb.toString());
                }
            }
        };
        AuthResponder.get(constants, messages, callback, "about.php");
    }

    protected void addAndComma(StringBuffer sb, int count) {
        if (count > 1) {
            sb.append(", ");
        }
        if (count == 1) {
            sb.append(" og ");
        }
    }

    private void checkServerVersion(JSONObject object) {

        String serverVersion = Util.str(object.get("serverversion"));

        if (!(CLIENT_VERSION.equals(serverVersion))) {
            Window.alert(messages.version_mismatch(CLIENT_VERSION, serverVersion));
        }
    }

}
package no.knubo.bok.client.views.registers;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponse;
import no.knubo.bok.client.misc.ServerResponsePlainText;
import no.knubo.bok.client.ui.NamedButton;
import no.knubo.bok.client.ui.TableRowSelected;
import no.knubo.bok.client.ui.TableUtils;
import no.knubo.bok.client.ui.TextBoxWithErrorText;
import no.knubo.bok.client.util.Picked;
import no.knubo.bok.client.views.editors.PersonEditor;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PersonEditView extends Composite implements ClickListener, TableRowSelected, Picked {

    private static PersonEditView me;
    private Constants constants;
    private Messages messages;
    private TextBoxWithErrorText firstName;
    private TextBoxWithErrorText lastName;
    private NamedButton newSearchButton;
    private NamedButton searchButton;
    private HTML searchResult;
    private final Elements elements;

    public static PersonEditView getInstance(Elements elements, Constants constants, Messages messages) {

        if (me == null) {
            me = new PersonEditView(elements, constants, messages);
        }
        return me;
    }

    public PersonEditView(Elements elements, Constants constants, Messages messages) {
        this.elements = elements;
        this.constants = constants;
        this.messages = messages;

        DockPanel dp = new DockPanel();

        Label header = new Label(elements.menuitem_book_people());
        header.addStyleName("pageheading");
        dp.add(header, DockPanel.NORTH);

        FlexTable table = new FlexTable();
        table.addStyleName("edittable");
        table.setText(0, 0, elements.person_firstname());
        table.setText(1, 0, elements.person_lastname());

        firstName = new TextBoxWithErrorText("firstName");
        lastName = new TextBoxWithErrorText("lastName");
        table.setWidget(0, 1, firstName);
        table.setWidget(1, 1, lastName);

        newSearchButton = new NamedButton("newSearchButton", elements.clear());
        searchButton = new NamedButton("searchButton", elements.search());
        searchButton.addClickListener(this);
        newSearchButton.addClickListener(this);

        HorizontalPanel hp = new HorizontalPanel();
        hp.add(searchButton);
        hp.add(newSearchButton);
        table.setWidget(2, 0, hp);
        table.getFlexCellFormatter().setColSpan(2, 0, 2);
        dp.add(table, DockPanel.NORTH);

        searchResult = new HTML();
        dp.add(searchResult, DockPanel.NORTH);

        TableUtils.addTableSelect(this);
        initWidget(dp);
        setTitle(elements.menuitem_book_people());
    }

    public void onClick(Widget sender) {
        if (sender == searchButton) {
            doSearch();
        } else {
            firstName.setText("");
            lastName.setText("");
        }
    }

    private void doSearch() {
        StringBuffer parameters = new StringBuffer();
        parameters.append("action=detailedsearch");

        Util.addPostParam(parameters, "firstname", firstName.getText());
        Util.addPostParam(parameters, "lastname", lastName.getText());

        ServerResponsePlainText callback = new ServerResponsePlainText() {

            public void plainText(String text) {
                searchResult.setHTML(text);
            }

            public void serverResponse(JSONValue responseObj) {
            }

        };
        AuthResponder.post(constants, messages, callback, parameters, "registers/people.php");
    }

    public void selected(String id) {
        PersonEditor personEditor = PersonEditor.getInstance(elements, constants, messages);
        personEditor.setReceiver(this);
        personEditor.idPicked(Integer.parseInt(id), null);
    }

    public void idPicked(final int id, String info) {
        ServerResponse callback = new ServerResponse() {

            public void serverResponse(JSONValue responseObj) {
                JSONObject object = responseObj.isObject();
                String firstNameStr = Util.str(object.get("firstname"));
                String lastNameStr = Util.str(object.get("lastname"));
                boolean author = Util.getBoolean(object.get("author"));
                boolean illustrator = Util.getBoolean(object.get("illustrator"));
                boolean editor = Util.getBoolean(object.get("editor"));
                boolean translator = Util.getBoolean(object.get("translator"));

                TableUtils.setTableText(id, firstNameStr, lastNameStr, author ? "f" : "", translator ? "o" : "", editor ? "r" : "", illustrator ? "i"
                        : "");
            }

        };
        AuthResponder.get(constants, messages, callback, "registers/people.php?action=get&id=" + id);

    }
}
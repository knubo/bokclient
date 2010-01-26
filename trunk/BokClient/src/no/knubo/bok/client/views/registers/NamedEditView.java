package no.knubo.bok.client.views.registers;

import java.util.HashMap;
import java.util.List;

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
import no.knubo.bok.client.views.editors.NamedEditor;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class NamedEditView extends Composite implements ClickListener, TableRowSelected, Picked, KeyboardListener {

    private static HashMap<String, NamedEditView> me = new HashMap<String, NamedEditView>();
    private Constants constants;
    private Messages messages;
    private TextBoxWithErrorText name;
    private TextBoxWithErrorText info;
    private NamedButton newSearchButton;
    private NamedButton searchButton;
    private HTML searchResult;
    private final Elements elements;
    private final String type;

    public static NamedEditView getInstance(String type, Elements elements, Constants constants, Messages messages) {

        NamedEditView instance = null;
        if (!me.containsKey("type")) {
            instance = new NamedEditView(type, elements, constants, messages);
            me.put(type, instance);
        } else {
            instance = me.get(type);
        }

        return instance;
    }

    public NamedEditView(String type, Elements elements, Constants constants, Messages messages) {
        this.type = type;
        this.elements = elements;
        this.constants = constants;
        this.messages = messages;

        DockPanel dp = new DockPanel();

        Label header = new Label(elements.getString("menuitem_book_" + type));
        header.addStyleName("pageheading");
        dp.add(header, DockPanel.NORTH);

        FlexTable table = new FlexTable();
        table.addStyleName("edittable");

        name = new TextBoxWithErrorText("name");
        name.getTextBox().addKeyboardListener(this);
        info = new TextBoxWithErrorText("info");
        info.getTextBox().addKeyboardListener(this);
        table.setText(0, 0, elements.getString(type));
        table.setWidget(0, 1, name);

        if (type.equals("placement")) {
            table.setText(1, 0, elements.info());
            table.setWidget(1, 1, info);
        }

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

        TableUtils.addTableSelect(this, this);
        initWidget(dp);
        setTitle(elements.getString("menuitem_book_" + type));
    }

    public void onClick(Widget sender) {
        if (sender == searchButton) {
            doSearch();
        } else {
            name.setText("");
            info.setText("");
        }
    }

    private void doSearch() {
        StringBuffer parameters = new StringBuffer();
        parameters.append("action=detailedsearch");

        Util.addPostParam(parameters, "search", name.getText());

        ServerResponsePlainText callback = new ServerResponsePlainText() {

            public void plainText(String text) {
                searchResult.setHTML(text);
            }

            public void serverResponse(JSONValue responseObj) {
                /* Empty */
            }

        };
        AuthResponder.post(constants, messages, callback, parameters, "registers/" + type + ".php");
    }

    public void selected(String id) {
        NamedEditor namedEditor = NamedEditor.getInstance(type, elements, constants, messages);
        namedEditor.setReceiver(this);
        namedEditor.idPicked(Integer.parseInt(id), null);
    }

    public void idPicked(final int id, String info) {
        ServerResponse callback = new ServerResponse() {

            public void serverResponse(JSONValue responseObj) {
                JSONObject object = responseObj.isObject();
                if (object.containsKey("info")) {
                    String infoStr = Util.str(object.get("info"));
                    String placement = Util.str(object.get("placement"));
                    TableUtils.setTableText(id, placement, infoStr);
                } else {
                    String nameStr = Util.str(object.get("name"));
                    TableUtils.setTableText(id, nameStr);
                }

            }

        };
        AuthResponder.get(constants, messages, callback, "registers/" + type + ".php?action=get&id=" + id);

    }

    public void onKeyDown(Widget sender, char keyCode, int modifiers) {
        /* Empty */
    }

    public void onKeyPress(Widget sender, char keyCode, int modifiers) {
        if (keyCode == KEY_ENTER) {
            doSearch();
        }

    }

    public void onKeyUp(Widget sender, char keyCode, int modifiers) {
        /* Empty */
    }

    public void selectedWithShift(List<String> data) {
        /* Empty */
    }
}
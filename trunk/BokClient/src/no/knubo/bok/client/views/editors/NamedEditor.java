package no.knubo.bok.client.views.editors;

import java.util.List;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponse;
import no.knubo.bok.client.misc.ServerResponseWithValidation;
import no.knubo.bok.client.suggest.CategorySuggestBuilder;
import no.knubo.bok.client.suggest.PlacementSuggestBuilder;
import no.knubo.bok.client.suggest.PublisherSuggestBuilder;
import no.knubo.bok.client.suggest.SeriesSuggestBuilder;
import no.knubo.bok.client.ui.NamedButton;
import no.knubo.bok.client.ui.TextBoxWithErrorText;
import no.knubo.bok.client.util.Picked;
import no.knubo.bok.client.validation.MasterValidator;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class NamedEditor extends DialogBox implements ClickListener, Picked {

    private static NamedEditor me;
    private TextBoxWithErrorText nameBox;
    private Picked receiver;
    private NamedButton saveButton;
    private NamedButton cancelButton;
    private HTML mainErrorLabel;
    private TextBox nameSearchBox;
    private final Constants constants;
    private final Messages messages;
    private int id;
    private final Elements elements;
    private String type;
    private FlexTable table;
    private SuggestBox suggestBox;
    private TextBoxWithErrorText infoBox;

    public static NamedEditor getInstance(String type, Elements elements, Constants constants, Messages messages) {
        if (me == null) {
            me = new NamedEditor(elements, constants, messages);
        }

        me.init(type);
        me.center();
        me.nameSearchBox.setFocus(true);
        return me;
    }

    private void init(String type) {
        this.type = type;
        id = 0;
        nameBox.setText("");
        setText(elements.getString("alter_" + type));
        table.setText(1, 0, elements.getString(type));

        nameSearchBox = new TextBox();
        mainErrorLabel.setText("");

        if (type.equals("publishers")) {
            suggestBox = new SuggestBox(PublisherSuggestBuilder.createPublisherOracle(constants, messages, this), nameSearchBox);
        } else if (type.equals("placements")) {
            suggestBox = new SuggestBox(PlacementSuggestBuilder.createPlacementOracle(constants, messages, this), nameSearchBox);

        } else if (type.equals("categories")) {
            suggestBox = new SuggestBox(CategorySuggestBuilder.createCategoryOracle(constants, messages, this), nameSearchBox);
        } else if (type.equals("series")) {
            suggestBox = new SuggestBox(SeriesSuggestBuilder.createSeriesOracle(constants, messages, this), nameSearchBox);
        } else {
            throw new RuntimeException("Unexpected type:" + type);
        }

        table.setWidget(0, 1, suggestBox);

        if (!type.equals("placements") && table.getRowCount() > 2) {
            table.removeRow(2);
        } else if (table.getRowCount() == 2) {
            table.setText(2, 0, elements.info());
            table.setWidget(2, 1, infoBox);
        }

        if (type.equals("placements")) {
            infoBox.setText("");
        }
    }

    NamedEditor(Elements elements, Constants constants, Messages messages) {
        this.elements = elements;
        this.constants = constants;
        this.messages = messages;
        DockPanel dp = new DockPanel();
        table = new FlexTable();
        dp.add(table, DockPanel.NORTH);
        table.setStyleName("edittable");

        nameBox = new TextBoxWithErrorText("name");
        infoBox = new TextBoxWithErrorText("info");

        table.setText(0, 0, elements.search());
        table.setWidget(1, 1, nameBox);

        saveButton = new NamedButton("save", elements.save());
        saveButton.addClickListener(this);
        cancelButton = new NamedButton("cancel", elements.cancel());
        cancelButton.addClickListener(this);

        mainErrorLabel = new HTML();
        mainErrorLabel.setStyleName("error");

        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(mainErrorLabel);
        dp.add(buttonPanel, DockPanel.NORTH);
        setWidget(dp);

    }

    public void setReceiver(Picked receiver) {
        this.receiver = receiver;

    }

    public void onClick(Widget sender) {
        if (sender == cancelButton) {
            hide();
            return;
        }
        if (sender == saveButton && validateSave()) {
            save();
        }
    }

    private boolean validateSave() {
        MasterValidator mv = new MasterValidator();
        Widget[] widgets = new Widget[] { nameBox };
        mv.mandatory(messages.required_field(), widgets);
        return mv.validateStatus();
    }

    private void save() {

        StringBuffer params = new StringBuffer();
        params.append("action=save");
        Util.addPostParam(params, "id", String.valueOf(id));

        if (type.equals("placements")) {
            Util.addPostParam(params, "placement", nameBox.getText());
            Util.addPostParam(params, "info", infoBox.getText());
        } else {
            Util.addPostParam(params, "name", nameBox.getText());
        }

        ServerResponseWithValidation callback = new ServerResponseWithValidation() {

            public void serverResponse(JSONValue responseObj) {
                JSONObject object = responseObj.isObject();
                id = Util.getInt(object.get("id"));
                String name = Util.str(object.get(type.equals("placements") ? "placement" : "name"));
                receiver.idPicked(id, name);
                hide();
            }

            public void validationError(List<String> fields) {
                if (fields.contains("duplicate")) {
                    mainErrorLabel.setText(messages.duplicate());
                }
            }

        };

        AuthResponder.post(constants, messages, callback, params, "registers/" + type + ".php");

    }

    public void idPicked(int id, String info) {
        this.id = id;
        nameSearchBox.setEnabled(false);

        ServerResponse callback = new ServerResponse() {

            public void serverResponse(JSONValue responseObj) {
                JSONObject object = responseObj.isObject();
                nameBox.setText(Util.str(object.get(type.equals("placements") ? "placement" : "name")));

                if (object.containsKey("info")) {
                    infoBox.setText(Util.str(object.get("info")));
                }
            }

        };
        AuthResponder.get(constants, messages, callback, "registers/" + type + ".php?action=get&id=" + id);
    }
}

package no.knubo.bok.client.ui;

import no.knubo.bok.client.Util;
import no.knubo.bok.client.validation.Validateable;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;

public class ListBoxWithErrorText extends ErrorLabelWidget implements Validateable {

    private ListBox listbox;

    public String getText() {
        return Util.getSelected(listbox);
    }

    public ListBoxWithErrorText(String id) {
        super(new ListBox());
        listbox = (ListBox) widget;

        HorizontalPanel hp = new HorizontalPanel();

        hp.add(listbox);
        hp.add(label);
        initWidget(hp);
        DOM.setElementAttribute(listbox.getElement(), "id", id);
    }

    public ListBoxWithErrorText(String id, HTML errorLabel) {
        super(new ListBox(), errorLabel);
        listbox = (ListBox) widget;

        initWidget(listbox);
        DOM.setElementAttribute(listbox.getElement(), "id", id);
    }

    public ListBoxWithErrorText(String id, boolean multiSelect) {
        super(new ListBox(multiSelect));
        listbox = (ListBox) widget;

        HorizontalPanel hp = new HorizontalPanel();

        hp.add(listbox);
        hp.add(label);
        initWidget(hp);
        DOM.setElementAttribute(listbox.getElement(), "id", id);
    }

    public ListBox getListbox() {
        return listbox;
    }

    public void setSelectedIndex(int i) {
        setErrorText("");
        listbox.setSelectedIndex(i);
    }

    public void setIndexByValue(JSONValue value) {
        Util.setIndexByValue(listbox, Util.str(value));
    }

    public void clear() {
        listbox.clear();
    }

    public void addItem(JSONValue description, JSONValue value) {
        listbox.addItem(Util.str(description), Util.str(value));
    }
}

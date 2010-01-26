package no.knubo.bok.client.ui;

import java.util.ArrayList;
import java.util.List;

import no.knubo.bok.client.Util;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.ui.Widget;

public class TableUtils implements EventPreview, ActiveWidget {

    private List<Widget> callbackWidgets = new ArrayList<Widget>();
    private List<TableRowSelected> callbacks = new ArrayList<TableRowSelected>();
    private Element previousElement;
    private Widget activeWidget;
    private static TableUtils me;

    private TableUtils() {
        DOM.addEventPreview(this);

    };

    public static void addTableSelect(Widget widget, TableRowSelected callback) {
        if (me == null) {
            me = new TableUtils();
        }
        me.addCallback(widget, callback);
    }

    public static ActiveWidget getAsListener() {
        if (me == null) {
            me = new TableUtils();
        }
        return me;
    }

    private void addCallback(Widget widget, TableRowSelected callback) {
        callbackWidgets.add(widget);
        callbacks.add(callback);
    }

    public boolean onEventPreview(Event event) {
        if (event.getTarget().getParentElement() != null && event.getTarget().getParentElement().getId().startsWith("row")) {

            if (event.getType().equals("click")) {
                String id = event.getTarget().getParentElement().getId().substring(3);

                TableRowSelected eventOwner = findEventOwner();
                if (event.getShiftKey()) {
                    eventOwner.selectedWithShift(getDataForId(id));
                } else {
                    if (eventOwner != null) {
                        eventOwner.selected(id);
                    }
                }
            } else if (event.getType().equals("mousemove")) {
                setStyle(event.getTarget().getParentElement());
            }
        }
        return true;
    }

    private List<String> getDataForId(String id) {
        ArrayList<String> data = new ArrayList<String>();

        Element tr = DOM.getElementById("row" + id);
        Element td = tr.getFirstChildElement();

        while (td != null) {
            data.add(td.getInnerText());
            td = td.getNextSiblingElement();
        }

        return data;
    }

    private TableRowSelected findEventOwner() {

        int pos = 0;
        for (Widget callback : callbackWidgets) {
            if (callback == activeWidget) {
                return callbacks.get(pos);
            }
            pos++;
        }

        return null;
    }

    private void setStyle(Element parentElement) {

        if (previousElement != null) {
            setColStyles(previousElement.getFirstChildElement(), "black");
            previousElement.getStyle().setProperty("color", "black");
        }

        setColStyles(parentElement.getFirstChildElement(), "blue");

        previousElement = parentElement;
    }

    private void setColStyles(Element elementIn, String color) {
        Element element = elementIn;
        do {
            element.getStyle().setProperty("color", color);
            element = element.getNextSiblingElement();
        } while (element != null);
    }

    public static void setTableText(int id, String... data) {
        Element tr = DOM.getElementById("row" + id);
        Element td = tr.getFirstChildElement();

        for (String string : data) {
            td.setInnerHTML(string);
            td = td.getNextSiblingElement();
        }
    }

    public void changedTo(Widget widget) {
        Util.log("Change to widget:"+widget.getTitle());
        activeWidget = widget;
    }

}

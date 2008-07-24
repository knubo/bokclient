package no.knubo.bok.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;

public class TableUtils implements EventPreview {

    private TableRowSelected callback;
    private Element previousElement;

    private TableUtils() {
        /* Empty */
    };

    public static void addTableSelect(TableRowSelected callback) {
        TableUtils t = new TableUtils();
        t.callback = callback;
        DOM.addEventPreview(t);
    }

    public boolean onEventPreview(Event event) {
        if (event.getTarget().getParentElement() != null && event.getTarget().getParentElement().getId().startsWith("row")) {
            if (event.getType().equals("click")) {
                String id = event.getTarget().getParentElement().getId().substring(3);
                callback.selected(id);
            }

            if (event.getType().equals("mousemove")) {
                setStyle(event.getTarget().getParentElement());
            }
        }
        return true;
    }

    private void setStyle(Element parentElement) {

        if (previousElement != null) {
            setColStyles(previousElement.getFirstChildElement(), "black");
            previousElement.getStyle().setProperty("color", "black");
        }

        setColStyles(parentElement.getFirstChildElement(), "blue");

        previousElement = parentElement;
    }

    private void setColStyles(Element element, String color) {
        do {
            element.getStyle().setProperty("color", color);
            element = element.getNextSiblingElement();
        } while (element != null);
    }

    public static void setTableText(int id, String... data) {
        Element tr = DOM.getElementById("row"+id);
        Element td = tr.getFirstChildElement();

        for (String string : data) {
            td.setInnerHTML(string);
            td = td.getNextSiblingElement();
        }
    }
    
}

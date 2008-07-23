package no.knubo.bok.client.views.registers;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


public class PersonEditView extends Composite implements ClickListener {

    private static PersonEditView me;
    private Constants constants;
    private Messages messages;

    public static PersonEditView getInstance(Elements elements, Constants constants, Messages messages) {

        if (me == null) {
            me = new PersonEditView(elements, constants, messages);
        }
        return me;
    }

    public PersonEditView(Elements elements, Constants constants, Messages messages) {
        this.constants = constants;
        this.messages = messages;

        DockPanel dp = new DockPanel();

        Label header = new Label(elements.title_edit_person());
        header.addStyleName("pageheading");
        dp.add(header, DockPanel.NORTH);
    }

    public void onClick(Widget sender) {
        // TODO Auto-generated method stub
        
    }
}

package no.knubo.bok.client.views;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

public class AboutView extends Composite  {

    private static AboutView instance;
    private final Messages messages;
    private final Constants constants;
    private FlexTable table;
	private Elements elements;

    public AboutView(Messages messages, Constants constants, Elements elements) {
        this.messages = messages;
        this.constants = constants;
        this.elements = elements;

        table = new FlexTable();
        table.setStyleName("tableborder");

        table.setText(0, 0, elements.title_about());
        
        initWidget(table);
    }
    

    public static AboutView getInstance(Constants constants, Messages messages,
            Elements elements) {
        if (instance == null) {
            instance = new AboutView(messages, constants, elements);
        }
        return instance;
    }

    public void init() {

    }
}
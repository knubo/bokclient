package no.knubo.bok.client.views.net;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.model.Book;
import no.knubo.bok.client.model.BookInfo;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class IsbnLookupView extends DialogBox implements ClickListener {

    private static IsbnLookupView me;
    private FlexTable table;
    private Button cancelButton;
    private Button useButton;
    private BookInfo bookEditView;
    private ExternalBookHelper bibsysBookHelper;
    private ExternalBookHelper bokkildenBookHelper;

    public IsbnLookupView(Elements elements, Constants constants, Messages messages) {

        setTitle(elements.external_isbn());

        DockPanel dp = new DockPanel();

        table = new FlexTable();
        table.addStyleName("edittable");
        table.setText(0, 0, elements.external_bibsys());
        table.setText(0, 3, elements.external_bokkilden());
        table.setText(1, 0, elements.book_title());
        table.setText(2, 0, elements.book_author());
        table.setText(4, 0, elements.book_publisher());
        table.setText(6, 0, elements.book_year_written());
        table.setText(7, 0, elements.external_note());

        bibsysBookHelper = new ExternalBookHelper(constants, messages, elements);
        bokkildenBookHelper = new ExternalBookHelper(constants, messages, elements);
        table.setWidget(1, 2, bibsysBookHelper.getTitleCheckBox());
        table.setWidget(3, 2, bibsysBookHelper.getAuthorCheckBox());
        table.setWidget(5, 2, bibsysBookHelper.getPublisherCheckBox());
        table.setWidget(6, 2, bibsysBookHelper.getYearWrittenCheckBox());

        table.setWidget(1, 3, bokkildenBookHelper.getTitleCheckBox());
        table.setWidget(3, 3, bokkildenBookHelper.getAuthorCheckBox());
        table.setWidget(5, 3, bokkildenBookHelper.getPublisherCheckBox());
        table.setWidget(6, 3, bokkildenBookHelper.getYearWrittenCheckBox());

        
        table.setWidget(1, 1, bibsysBookHelper.getTitleLabel());
        table.setWidget(2, 1, bibsysBookHelper.getAuthorLabel());
        table.setWidget(3, 1, bibsysBookHelper.getAuthorBox());
        table.setWidget(4, 1, bibsysBookHelper.getPublisherLabel());
        table.setWidget(5, 1, bibsysBookHelper.getPublisherBox());
        table.setWidget(6, 1, bibsysBookHelper.getWrittenYearLabel());
        table.setWidget(7, 1, bibsysBookHelper.getNoteLabel());
        table.setWidget(8, 1, bibsysBookHelper.getExternalURL());

        table.setWidget(1, 3, bokkildenBookHelper.getTitleLabel());
        table.setWidget(2, 3, bokkildenBookHelper.getAuthorLabel());
        table.setWidget(3, 3, bokkildenBookHelper.getAuthorBox());
        table.setWidget(4, 3, bokkildenBookHelper.getPublisherLabel());
        table.setWidget(5, 3, bokkildenBookHelper.getPublisherBox());
        table.setWidget(6, 3, bokkildenBookHelper.getWrittenYearLabel());
        table.setWidget(7, 3, bokkildenBookHelper.getNoteLabel());
        table.setWidget(8, 3, bokkildenBookHelper.getExternalURL());

        bibsysBookHelper.link(bokkildenBookHelper);
        
        HorizontalPanel hp = new HorizontalPanel();
        cancelButton = new Button(elements.cancel());
        cancelButton.addClickListener(this);
        useButton = new Button(elements.use());
        useButton.addClickListener(this);
        hp.add(cancelButton);
        hp.add(useButton);

        dp.add(table, DockPanel.NORTH);
        dp.add(hp, DockPanel.NORTH);

        setWidget(dp);
    }

    public static IsbnLookupView getInstance(String isbn, BookInfo bookEditView, Elements elements, Constants constants, Messages messages) {
        if (me == null) {
            me = new IsbnLookupView(elements, constants, messages);
        }

        me.init(isbn, bookEditView);
        me.center();
        return me;
    }

    private void init(String isbn, BookInfo bookEditView) {
        this.bookEditView = bookEditView;
        useButton.setVisible(this.bookEditView != null);
        bibsysBookHelper.init("webscrape/webscrape.php?action=bibsys&isbn=" + isbn);
        bokkildenBookHelper.init("webscrape/webscrape.php?action=bokkilden&isbn=" + isbn);
    }

    public void onClick(Widget sender) {
        if (sender == cancelButton) {
            hide();
        } else if (sender == useButton) {
            returnToSender();
            hide();
        }
    }

    private void returnToSender() {
        Book book = new Book();
        bibsysBookHelper.fillBookInfo(book);
        bokkildenBookHelper.fillBookInfo(book);
        bookEditView.setBookInfo(book);

    }

}

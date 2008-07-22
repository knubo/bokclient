package no.knubo.bok.client.views;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponsePlainText;
import no.knubo.bok.client.suggest.CategorySuggestBox;
import no.knubo.bok.client.suggest.PersonSuggestBox;
import no.knubo.bok.client.suggest.PlacementSuggestBox;
import no.knubo.bok.client.suggest.PublisherSuggestBox;
import no.knubo.bok.client.suggest.SeriesSuggestBox;
import no.knubo.bok.client.ui.NamedButton;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

public class BookSearchView extends Composite implements ClickListener {

    private static BookSearchView me;
    private Constants constants;
    private Messages messages;
    private TextBox bookNumber;
    private TextBoxBase titleBox;
    private TextBoxBase isbnBox;
    private NamedButton newSearchButton;
    private HTML searchResultHTML;
    private NamedButton searchButton;
    private PersonSuggestBox authorSuggestBox;
    private PersonSuggestBox editorSuggestBox;
    private PersonSuggestBox translatorSuggestBox;
    private SeriesSuggestBox seriesSuggestBox;
    private CategorySuggestBox categorySuggestBox;
    private PublisherSuggestBox publisherSuggestBox;
    private PersonSuggestBox illustratorSuggestbox;
    private PlacementSuggestBox placementSuggestbox;
    private TextBox yearWritten;

    public static BookSearchView getInstance(Elements elements, Constants constants, Messages messages) {

        if (me == null) {
            me = new BookSearchView(elements, constants, messages);
        }
        return me;
    }

    public BookSearchView(Elements elements, Constants constants, Messages messages) {
        this.constants = constants;
        this.messages = messages;

        DockPanel dp = new DockPanel();

        Label header = new Label(elements.title_search());
        header.addStyleName("pageheading");
        dp.add(header, DockPanel.NORTH);

        FlexTable table = new FlexTable();

        table.setStyleName("edittable");

        table.setText(0, 0, elements.book_number());
        table.setText(0, 3, elements.book_title());
        table.setText(0, 6, elements.book_isbn());
        table.setText(1, 0, elements.book_author());
        table.setText(1, 3, elements.book_editor());
        table.setText(1, 6, elements.book_translator());
        table.setText(2, 0, elements.category());
        table.setText(2, 3, elements.series());
        table.setText(2, 6, elements.book_publisher());
        table.setText(3, 0, elements.book_placement());
        table.setText(3, 3, elements.book_year_written());
        table.setText(3, 6, elements.book_illustrator());

        bookNumber = new TextBox();
        titleBox = new TextBox();
        isbnBox = new TextBox();

        newSearchButton = new NamedButton("newSearchButton", elements.clear());
        searchButton = new NamedButton("searchButton", elements.search());
        searchButton.addClickListener(this);
        newSearchButton.addClickListener(this);
        authorSuggestBox = new PersonSuggestBox("A", constants, messages, elements);
        authorSuggestBox.hideAddImage();
        editorSuggestBox = new PersonSuggestBox("E", constants, messages, elements);
        editorSuggestBox.hideAddImage();
        translatorSuggestBox = new PersonSuggestBox("T", constants, messages, elements);
        translatorSuggestBox.hideAddImage();
        seriesSuggestBox = new SeriesSuggestBox(constants, messages, elements);
        seriesSuggestBox.hideAddImage();
        categorySuggestBox = new CategorySuggestBox(constants, messages, elements);
        categorySuggestBox.hideAddImage();
        publisherSuggestBox = new PublisherSuggestBox(constants, messages, elements);
        publisherSuggestBox.hideAddImage();
        illustratorSuggestbox = new PersonSuggestBox("I", constants, messages, elements);
        illustratorSuggestbox.hideAddImage();
        placementSuggestbox = new PlacementSuggestBox(constants, messages, elements);
        placementSuggestbox.hideAddImage();
        yearWritten = new TextBox();

        table.setWidget(0, 1, bookNumber);
        table.setWidget(0, 4, titleBox);
        table.setWidget(0, 7, isbnBox);
        table.setWidget(1, 1, authorSuggestBox.getSuggestBox());
        table.setWidget(1, 2, authorSuggestBox.getImageContainer());
        table.setWidget(1, 4, editorSuggestBox.getSuggestBox());
        table.setWidget(1, 5, editorSuggestBox.getImageContainer());
        table.setWidget(1, 7, translatorSuggestBox.getSuggestBox());
        table.setWidget(1, 8, translatorSuggestBox.getImageContainer());
        table.setWidget(2, 1, categorySuggestBox.getSuggestBox());
        table.setWidget(2, 2, categorySuggestBox.getImageContainer());
        table.setWidget(2, 4, seriesSuggestBox.getSuggestBox());
        table.setWidget(2, 5, seriesSuggestBox.getImageContainer());
        table.setWidget(2, 7, publisherSuggestBox.getSuggestBox());
        table.setWidget(2, 8, publisherSuggestBox.getImageContainer());
        table.setWidget(3, 1, placementSuggestbox.getSuggestBox());
        table.setWidget(3, 1, placementSuggestbox.getSuggestBox());
        table.setWidget(3, 2, placementSuggestbox.getImageContainer());
        table.setWidget(3, 4, yearWritten);
        table.setWidget(3, 7, illustratorSuggestbox.getSuggestBox());
        table.setWidget(3, 8, illustratorSuggestbox.getImageContainer());

        HorizontalPanel hp = new HorizontalPanel();
        hp.add(searchButton);
        hp.add(newSearchButton);

        table.setWidget(4, 0, hp);
        table.getFlexCellFormatter().setColSpan(4, 0, 4);

        searchResultHTML = new HTML();

        dp.add(table, DockPanel.NORTH);
        dp.add(searchResultHTML, DockPanel.NORTH);

        initWidget(dp);
    }

    public void onClick(Widget sender) {
        if (sender == searchButton) {
            doSearch();
        } else {
            bookNumber.setText("");
            publisherSuggestBox.clear();
            placementSuggestbox.clear();
            illustratorSuggestbox.clear();
            seriesSuggestBox.clear();
            translatorSuggestBox.clear();
            authorSuggestBox.clear();
            editorSuggestBox.clear();
            yearWritten.setText("");
            titleBox.setText("");
            isbnBox.setText("");
        }
    }

    private void doSearch() {
        StringBuffer parameters = new StringBuffer();
        parameters.append("action=detailedsearch");

        if (titleBox.getText().length() > 0) {
            Util.addPostParam(parameters, "title", titleBox.getText() + "%");
        }
        if(isbnBox.getText().length() > 0) {
            Util.addPostParam(parameters, "ISBN", isbnBox.getText() + "%");
        }
        
        Util.addPostParam(parameters, "usernumber", bookNumber.getText());
        Util.addPostParam(parameters, "year_written", yearWritten.getText());
        Util.addPostParam(parameters, "author_id", authorSuggestBox.getId());
        Util.addPostParam(parameters, "editor_id", editorSuggestBox.getId());
        Util.addPostParam(parameters, "translator_id", translatorSuggestBox.getId());
        Util.addPostParam(parameters, "illustrator_id", illustratorSuggestbox.getId());
        Util.addPostParam(parameters, "publisher_id", publisherSuggestBox.getId());
        Util.addPostParam(parameters, "series", seriesSuggestBox.getId());
        Util.addPostParam(parameters, "placement_id", placementSuggestbox.getId());

        ServerResponsePlainText callback = new ServerResponsePlainText() {

            public void plainText(String text) {
                searchResultHTML.setHTML(text);
            }

            public void serverResponse(JSONValue responseObj) {
            }

        };
        AuthResponder.post(constants, messages, callback, parameters, "registers/books.php");
    }

}

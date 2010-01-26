package no.knubo.bok.client.views;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ImageFactory;
import no.knubo.bok.client.misc.ServerResponse;
import no.knubo.bok.client.suggest.BookSuggestBuilder;
import no.knubo.bok.client.ui.NamedButton;
import no.knubo.bok.client.ui.TextBoxWithErrorText;
import no.knubo.bok.client.util.Picked;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

public class QuickBookSearch extends Composite implements Picked, ClickListener {

    private static QuickBookSearch me;
    private Constants constants;
    private Messages messages;
    private TextBoxWithErrorText bookNumber;
    private SuggestBox titleSuggestBox;
    private SuggestBox isbnSuggestBox;
    private Image searchImage;
    private TextBoxBase titleBox;
    private TextBoxBase isbnBox;
    private NamedButton newSearchButton;
    private Label infoLabel;
    QuickBookSearchFields data;

    public static QuickBookSearch getInstance(ViewCallback viewCallback, Elements elements, Constants constants, Messages messages) {

        if (me == null) {
            me = new QuickBookSearch(viewCallback, elements, constants, messages);
        }
        me.onClick(null);
        return me;
    }

    public QuickBookSearch(ViewCallback viewCallback, Elements elements, Constants constants, Messages messages) {
        data = new QuickBookSearchFields(viewCallback, elements);
        this.constants = constants;
        this.messages = messages;

        DockPanel dp = new DockPanel();

        Label header = new Label(elements.menuitem_book_fast_search());
        header.addStyleName("pageheading");
        dp.add(header, DockPanel.NORTH);

        FlexTable table = new FlexTable();

        table.setStyleName("edittable");

        table.setText(0, 0, elements.book_number());
        table.setText(1, 0, elements.book_title());
        table.setText(2, 0, elements.book_isbn());

        bookNumber = new TextBoxWithErrorText("bookNumber");
        bookNumber.getTextBox().addKeyboardListener(enterTriggerSearch());
        searchImage = ImageFactory.searchImage("searchBook");
        searchImage.addClickListener(this);
        titleBox = new TextBox();
        titleSuggestBox = new SuggestBox(BookSuggestBuilder.createBookOracle(constants, messages, "title", this), titleBox);
        isbnBox = new TextBox();
        isbnSuggestBox = new SuggestBox(BookSuggestBuilder.createBookOracle(constants, messages, "ISBN", this), isbnBox);
        newSearchButton = new NamedButton("newSearchButton", elements.clear());
        newSearchButton.addClickListener(this);
        table.setWidget(0, 1, bookNumber);
        table.setWidget(0, 2, searchImage);
        table.setWidget(1, 1, titleSuggestBox);
        table.setWidget(2, 1, isbnSuggestBox);
        table.setWidget(3, 1, newSearchButton);

        data.drawBookLabels();

        infoLabel = new Label();
        dp.add(table, DockPanel.NORTH);
        dp.add(infoLabel, DockPanel.NORTH);
        dp.add(data.getInfoTable(), DockPanel.NORTH);

        initWidget(dp);
        setTitle(elements.menuitem_book_fast_search());
    }

    private KeyboardListener enterTriggerSearch() {
        return new KeyboardListener() {

            public void onKeyDown(Widget sender, char keyCode, int modifiers) {
                /* Empty */
            }

            public void onKeyPress(Widget sender, char keyCode, int modifiers) {

                if (keyCode == KEY_ENTER) {
                    getBookByBookNumber();
                }
            }

            public void onKeyUp(Widget sender, char keyCode, int modifiers) {
                /* Empty */
            }

        };
    }

    public void idPicked(int id, String info) {
        getBookInfo(id);
    }

    private void getBookInfo(int id) {
        infoLabel.setText("");
        ServerResponse callback = new ServerResponse() {

            public void serverResponse(JSONValue responseObj) {
                JSONObject obj = responseObj.isObject();
                setBookInfo(obj);
            }

        };
        AuthResponder.get(constants, messages, callback, "registers/books.php?action=getfull&id=" + id);
    }

    void setBookInfo(JSONObject obj) {
        infoLabel.setText("");
        data.setBookInfo(obj);
    }

    public void onClick(Widget sender) {
        if (sender == newSearchButton) {
            titleSuggestBox.setText("");
            isbnSuggestBox.setText("");
            bookNumber.setText("");
        } else if (sender == searchImage) {
            getBookByBookNumber();
        }
    }

    private void getBookByBookNumber() {
        ServerResponse callback = new ServerResponse() {

            public void serverResponse(JSONValue responseObj) {
                JSONObject object = responseObj.isObject();

                if (object != null && object.containsKey("usernumber")) {
                    setBookInfo(object);
                } else {
                    infoLabel.setText(messages.unknown_book_number());
                }
            }

        };
        AuthResponder.get(constants, messages, callback, "registers/books.php?action=getfull&userNumber=" + bookNumber.getText());
    }

}

package no.knubo.bok.client.views;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
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
    private FlexTable infoTable;
    private Label infoLabel;
    private Image editImage;
    private final ViewCallback viewCallback;
    private int currentId;

    public static QuickBookSearch getInstance(ViewCallback viewCallback, Elements elements, Constants constants, Messages messages) {

        if (me == null) {
            me = new QuickBookSearch(viewCallback, elements, constants, messages);
        }
        me.onClick(null);
        return me;
    }

    public QuickBookSearch(ViewCallback viewCallback, Elements elements, Constants constants, Messages messages) {
        this.viewCallback = viewCallback;
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

        infoTable = new FlexTable();
        infoTable.addStyleName("tableborder");
        int row = 0;
        infoTable.setText(row++, 0, elements.book_number());
        infoTable.setText(row++, 0, elements.book_isbn());
        infoTable.setText(row++, 0, elements.book_title());
        infoTable.setText(row++, 0, elements.book_org_title());
        infoTable.setText(row++, 0, elements.book_subtitle());
        infoTable.setText(row++, 0, elements.category());
        infoTable.setText(row++, 0, elements.book_author());
        infoTable.setText(row++, 0, elements.book_coauthor());
        infoTable.setText(row++, 0, elements.book_editor());
        infoTable.setText(row++, 0, elements.book_translator());
        infoTable.setText(row++, 0, elements.book_publisher());
        infoTable.setText(row++, 0, elements.book_year_written());
        infoTable.setText(row++, 0, elements.book_year_published());
        infoTable.setText(row++, 0, elements.book_edition());
        infoTable.setText(row++, 0, elements.book_impression());
        infoTable.setText(row++, 0, elements.book_price());
        infoTable.setText(row++, 0, elements.book_series());
        infoTable.setText(row++, 0, elements.book_serie_nmb());
        infoTable.setText(row++, 0, elements.book_placement());
        infoTable.setText(row++, 0, elements.book_illustrator());

        infoLabel = new Label();
        editImage = ImageFactory.editImage("editBook");
        editImage.addClickListener(this);
        infoTable.setWidget(0, 2, editImage);
        dp.add(table, DockPanel.NORTH);
        dp.add(infoLabel, DockPanel.NORTH);
        dp.add(infoTable, DockPanel.NORTH);

        initWidget(dp);
        setTitle(elements.menuitem_book_fast_search());
    }

    private KeyboardListener enterTriggerSearch() {
        return new KeyboardListener() {

            public void onKeyDown(Widget sender, char keyCode, int modifiers) {
            }

            public void onKeyPress(Widget sender, char keyCode, int modifiers) {

                if (keyCode == KEY_ENTER) {
                    getBookByBookNumber();
                }
            }

            public void onKeyUp(Widget sender, char keyCode, int modifiers) {
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
        int row = 0;
        currentId = Util.getInt(obj.get("id"));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("usernumber"))+Util.strSkipNull(obj.get("subbook")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("ISBN")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("title")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("org_title")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("subtitle")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("category")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("author")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("coauthor")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("editor")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("translator")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("publisher")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("written_year")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("published_year")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("edition")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("impression")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("price")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("series")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("number_in_series")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("placement")));
        infoTable.setText(row++, 1, Util.strSkipNull(obj.get("illustrator")));
        
        for(int i=1;i<row;i++) {
            infoTable.getFlexCellFormatter().setColSpan(i, 1, 2);
        }
    }

    public void onClick(Widget sender) {
        if (sender == newSearchButton) {
            titleSuggestBox.setText("");
            isbnSuggestBox.setText("");
            bookNumber.setText("");
        } else if (sender == searchImage) {
            getBookByBookNumber();
        } else if (sender == editImage && currentId > 0) {
            viewCallback.editBook(currentId);
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

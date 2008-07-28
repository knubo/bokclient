package no.knubo.bok.client.views;

import java.util.List;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.FocusCallback;
import no.knubo.bok.client.misc.ImageFactory;
import no.knubo.bok.client.misc.ServerResponse;
import no.knubo.bok.client.misc.ServerResponseWithValidation;
import no.knubo.bok.client.suggest.CategorySuggestBox;
import no.knubo.bok.client.suggest.CoAuthorSuggestBuilder;
import no.knubo.bok.client.suggest.GeneralSuggestBox;
import no.knubo.bok.client.suggest.PersonSuggestBox;
import no.knubo.bok.client.suggest.PlacementSuggestBox;
import no.knubo.bok.client.suggest.PublisherSuggestBox;
import no.knubo.bok.client.suggest.SeriesSuggestBox;
import no.knubo.bok.client.ui.ErrorLabelWidget;
import no.knubo.bok.client.ui.NamedButton;
import no.knubo.bok.client.ui.TextBoxWithErrorText;
import no.knubo.bok.client.validation.MasterValidator;
import no.knubo.bok.client.validation.Validateable;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class BookEditView extends Composite implements ClickListener {

    private static BookEditView instance;
    private final Messages messages;
    private final Constants constants;
    private FlexTable table;
    private TextBoxWithErrorText bookNumber;
    private TextBoxWithErrorText bookISBN;
    private TextBoxWithErrorText bookTitle;
    private TextBoxWithErrorText bookOrgTitle;
    private PlacementSuggestBox placementSuggestBox;
    private int row;
    private int column;
    private TextBoxWithErrorText bookPrice;
    private TextBoxWithErrorText bookSeriesNmb;
    private TextBoxWithErrorText bookYearWritten;
    private TextBoxWithErrorText bookYearPublished;
    private TextBoxWithErrorText bookEdition;
    private TextBoxWithErrorText bookImpression;
    private TextArea bookSubtitle;
    private int tabIndex = 1;
    private int maxRow;
    private NamedButton registerButton;
    private PersonSuggestBox authorSuggestBox;
    private PersonSuggestBox readBySuggestBox;
    private PersonSuggestBox editorSuggestBox;
    private PersonSuggestBox translatorSuggestBox;
    private PersonSuggestBox illustratorSuggestBox;
    private CategorySuggestBox categorySuggestBox;
    private SeriesSuggestBox seriesSuggestBox;
    private PublisherSuggestBox publisherSuggestBox;
    private HTML bookErrorLabel;
    private HTML bookNumberErrorLabel;
    private HTML mainErrorLabel;
    private int id;
    private Image isbnSearch;
    private final Elements elements;
    private NamedButton deleteButton;
    private Label header;
    private NamedButton backButton;
    private SuggestBox coAuthorSuggestBox;
    private TextBoxWithErrorText bookSubNumber;

    public BookEditView(Messages messages, Constants constants, Elements elements) {
        this.messages = messages;
        this.constants = constants;
        this.elements = elements;

        table = new FlexTable();
        table.setStyleName("edittable");

        bookNumberErrorLabel = new HTML();
        bookNumber = new TextBoxWithErrorText("bookNumber", bookNumberErrorLabel);
        bookNumber.addFocusListener(fetchesUserNumber());
        bookNumber.setMaxLength(6);
        bookSubNumber = new TextBoxWithErrorText("subbook");
        bookSubNumber.setMaxLength(1);
        bookSubNumber.setStyleName("letter");

        bookISBN = new TextBoxWithErrorText("bookISBN");
        bookISBN.setMaxLength(40);

        isbnSearch = ImageFactory.searchImage("isbnSearch");
        isbnSearch.addClickListener(this);

        bookErrorLabel = new HTML();
        bookTitle = new TextBoxWithErrorText("bookTitle", bookErrorLabel);
        bookTitle.setMaxLength(40);
        bookOrgTitle = new TextBoxWithErrorText("bookOrgTitle");
        bookOrgTitle.setMaxLength(40);

        authorSuggestBox = new PersonSuggestBox("A", constants, messages, elements);
        readBySuggestBox = new PersonSuggestBox("R", constants, messages, elements);
        editorSuggestBox = new PersonSuggestBox("E", constants, messages, elements);
        translatorSuggestBox = new PersonSuggestBox("T", constants, messages, elements);
        illustratorSuggestBox = new PersonSuggestBox("I", constants, messages, elements);
        coAuthorSuggestBox = new SuggestBox(CoAuthorSuggestBuilder.createCoAuthorOracle(constants, messages));
        placementSuggestBox = new PlacementSuggestBox(constants, messages, elements);
        categorySuggestBox = new CategorySuggestBox(constants, messages, elements);
        publisherSuggestBox = new PublisherSuggestBox(constants, messages, elements);

        bookPrice = new TextBoxWithErrorText("bookPrice");
        seriesSuggestBox = new SeriesSuggestBox(constants, messages, elements);
        bookSeriesNmb = new TextBoxWithErrorText("bookSeriesNmb");
        bookYearWritten = new TextBoxWithErrorText("bookYearWritten");
        bookYearPublished = new TextBoxWithErrorText("bookYearPublished");
        bookEdition = new TextBoxWithErrorText("bookEdition");
        bookImpression = new TextBoxWithErrorText("bookImpression");
        bookSubtitle = new TextArea();
        bookSubtitle.setWidth("180px");
        bookSubtitle.setHeight("4em");

        row = 1;
        column = 0;
        
        HorizontalPanel hpBook = new HorizontalPanel();
        hpBook.add(bookNumber);
        hpBook.add(bookSubNumber);
        addElement(elements.book_number() + "*", hpBook, bookNumberErrorLabel);
        addElement(elements.book_isbn(), bookISBN, isbnSearch);
        addElement(elements.book_title() + "*", bookTitle, bookErrorLabel);
        addElement(elements.book_org_title(), bookOrgTitle);
        addElement(elements.book_subtitle(), bookSubtitle);
        addElement(elements.category() + "*", categorySuggestBox.getSuggestBox(), categorySuggestBox.getImageContainer());
        addElement(elements.book_author() + "*", authorSuggestBox.getSuggestBox(), authorSuggestBox.getImageContainer());
        addElement(elements.book_coauthor(), coAuthorSuggestBox);
        addElement(elements.book_editor(), editorSuggestBox.getSuggestBox(), editorSuggestBox.getImageContainer());
        addElement(elements.book_translator(), translatorSuggestBox.getSuggestBox(), translatorSuggestBox.getImageContainer());
        addElement(elements.book_publisher(), publisherSuggestBox.getSuggestBox(), publisherSuggestBox.getImageContainer());
        table.setWidget(1, 3, ImageFactory.blankImage(10, 10));

        row = 1;
        column = 4;
        addElement(elements.book_year_written(), bookYearWritten);
        addElement(elements.book_year_published(), bookYearPublished);
        addElement(elements.book_edition(), bookEdition);
        addElement(elements.book_impression(), bookImpression);
        addElement("", ImageFactory.blankImage(1, 1));
        addElement(elements.book_price(), bookPrice);
        addElement(elements.book_series(), seriesSuggestBox.getSuggestBox(), seriesSuggestBox.getImageContainer());
        addElement(elements.book_serie_nmb(), bookSeriesNmb);
        addElement(elements.book_placement(), placementSuggestBox.getSuggestBox(), placementSuggestBox.getImageContainer());
        addElement(elements.book_illustrator(), illustratorSuggestBox.getSuggestBox(), illustratorSuggestBox.getImageContainer());
        addElement(elements.book_read_by(), readBySuggestBox.getSuggestBox(), readBySuggestBox.getImageContainer());

        registerButton = new NamedButton("registerButton", elements.book_register_book());
        mainErrorLabel = new HTML();
        deleteButton = new NamedButton("deleteButton", elements.delete());
        backButton = new NamedButton("backButton", elements.back());
        setTabIndex(registerButton);
        registerButton.addClickListener(this);
        deleteButton.addClickListener(this);
        backButton.addClickListener(this);
        HorizontalPanel hp = new HorizontalPanel();
        hp.add(registerButton);
        hp.add(deleteButton);
        hp.add(backButton);
        hp.add(mainErrorLabel);
        table.setWidget(maxRow, 0, hp);
        table.getFlexCellFormatter().setColSpan(maxRow, 0, 8);

        DockPanel dp = new DockPanel();

        header = new Label(elements.title_new_book());
        header.addStyleName("pageheading");
        dp.add(header, DockPanel.NORTH);
        dp.add(table, DockPanel.NORTH);

        initWidget(dp);
        setTitle(elements.title_new_book());
    }

    private FocusCallback fetchesUserNumber() {
        return new FocusCallback() {

            public void onFocus(Validateable me) {
            }

            public void onLostFocus(ErrorLabelWidget me) {
                if (me.getText().length() == 0) {
                    fetchNextUserNumber();
                }
            }

        };
    }

    protected void fetchNextUserNumber() {
        ServerResponse callback = new ServerResponse() {

            public void serverResponse(JSONValue responseObj) {
                JSONObject object = responseObj.isObject();

                bookNumber.setText(Util.str(object.get("nextUserNumber")));
            }

        };
        AuthResponder.get(constants, messages, callback, "registers/books.php?action=nextUserNumber");
    }

    private void addElement(String title, Widget... fields) {
        table.setText(row, column, title);
        table.getCellFormatter().setStyleName(row, column, "logline");

        int pos = 1;
        for (Widget w : fields) {
            setTabIndex(w);
            table.setWidget(row, column + pos++, w);

        }
        row++;
        if (row > maxRow) {
            maxRow = row;
        }
    }

    private void setTabIndex(Widget w) {
        if (w instanceof SuggestBox) {
            SuggestBox sb = (SuggestBox) w;
            sb.setTabIndex(tabIndex++);
        } else if (w instanceof TextBoxWithErrorText) {
            TextBoxWithErrorText tb = (TextBoxWithErrorText) w;
            tb.getTextBox().setTabIndex(tabIndex++);
        } else if (w instanceof TextArea) {
            TextArea ta = (TextArea) w;
            ta.setTabIndex(tabIndex++);
        } else if(w instanceof HorizontalPanel) {
            HorizontalPanel hp = ((HorizontalPanel)w);
            
            for(int i= 0; i < hp.getWidgetCount(); i++) {
                setTabIndex(hp.getWidget(i));
            }
        }
    }

    public static BookEditView getInstance(Constants constants, Messages messages, Elements elements) {
        if (instance == null) {
            instance = new BookEditView(messages, constants, elements);
        }
        return instance;
    }

    public void init() {
        bookEdition.setText("");
        bookErrorLabel.setText("");
        bookImpression.setText("");
        bookISBN.setText("");
        bookNumber.setText("");
        bookSubNumber.setText("");
        bookOrgTitle.setText("");
        bookPrice.setText("");
        bookSeriesNmb.setText("");
        bookSubtitle.setText("");
        bookTitle.setText("");
        bookYearPublished.setText("");
        bookYearWritten.setText("");
        seriesSuggestBox.clear();
        categorySuggestBox.clear();
        placementSuggestBox.clear();
        publisherSuggestBox.clear();
        authorSuggestBox.clear();
        readBySuggestBox.clear();
        translatorSuggestBox.clear();
        illustratorSuggestBox.clear();
        editorSuggestBox.clear();
        bookNumberErrorLabel.setText("");
        id = 0;

        registerButton.setText(elements.book_register_book());
        header.setText(elements.title_new_book());
        setTitle(elements.title_new_book());
        bookNumber.setFocus(true);
        deleteButton.setVisible(false);
        backButton.setVisible(false);
    }

    public void onClick(Widget sender) {
        if (sender == registerButton && validate()) {
            save();
        } else if(sender == deleteButton && Window.confirm(messages.delete_book_question())) {
            delete();
        } else if(sender == backButton) {
            History.back();
        }
    }

    private void delete() {
        ServerResponse callback = new ServerResponse() {

            public void serverResponse(JSONValue responseObj) {
                init();
            }
            
        };
        AuthResponder.get(constants, messages, callback , "registers/books.php?action=delete&id="+id);
    }

    private boolean validate() {
        MasterValidator mv = new MasterValidator();

        mv.year(messages.illegal_year(), new Widget[] { bookYearPublished, bookYearWritten });
        mv.range(messages.field_to_low_zero(), new Integer(1), null, new Widget[] { bookEdition, bookImpression });
        mv.mandatory(messages.required_field(), new Widget[] { bookTitle, bookNumber });

        mv.fail(authorSuggestBox, authorSuggestBox.getCurrentId() == 0, messages.required_field());
        mv.fail(categorySuggestBox, categorySuggestBox.getCurrentId() == 0, messages.required_field());
        mv.money(messages.field_money(), new Widget[] { bookPrice });

        return mv.validateStatus();
    }

    private void save() {
        Util.timedMessage(mainErrorLabel, "", 0);

        ServerResponseWithValidation callback = new ServerResponseWithValidation() {

            public void serverResponse(JSONValue responseObj) {
                JSONObject object = responseObj.isObject();

                id = Util.getInt(object.get("id"));
                mainErrorLabel.setText(messages.save_ok());
                Util.timedMessage(mainErrorLabel, "", 10);
            }

            public void validationError(List<String> fields) {
                if (fields.contains("usernumber")) {
                    mainErrorLabel.setText(messages.duplicate_user_number());
                }
            }

        };
        StringBuffer parameters = buildPostParams();
        AuthResponder.post(constants, messages, callback, parameters, "registers/books.php");
    }

    private StringBuffer buildPostParams() {
        StringBuffer sb = new StringBuffer();

        sb.append("action=save");
        Util.addPostParam(sb, "id", String.valueOf(id));
        Util.addPostParam(sb, "usernumber", bookNumber.getText());
        Util.addPostParam(sb, "subbook", bookSubNumber.getText());
        Util.addPostParam(sb, "title", bookTitle.getText());
        Util.addPostParam(sb, "subtitle", bookSubtitle.getText());
        Util.addPostParam(sb, "org_title", bookOrgTitle.getText());
        Util.addPostParam(sb, "ISBN", bookISBN.getText());
        Util.addPostParam(sb, "author_id", authorSuggestBox.getId());
        Util.addPostParam(sb, "read_by_id", readBySuggestBox.getId());
        Util.addPostParam(sb, "illustrator_id", illustratorSuggestBox.getId());
        Util.addPostParam(sb, "translator_id", translatorSuggestBox.getId());
        Util.addPostParam(sb, "editor_id", editorSuggestBox.getId());
        Util.addPostParam(sb, "publisher_id", publisherSuggestBox.getId());
        Util.addPostParam(sb, "price", bookPrice.getText());
        Util.addPostParam(sb, "published_year", bookYearPublished.getText());
        Util.addPostParam(sb, "written_year", bookYearWritten.getText());
        Util.addPostParam(sb, "category_id", categorySuggestBox.getId());
        Util.addPostParam(sb, "placement_id", placementSuggestBox.getId());
        Util.addPostParam(sb, "edition", bookEdition.getText());
        Util.addPostParam(sb, "impression", bookImpression.getText());
        Util.addPostParam(sb, "series", seriesSuggestBox.getId());
        Util.addPostParam(sb, "number_in_series", bookSeriesNmb.getText());

        return sb;
    }

    public void init(int idToOpen) {
        init();
        id = idToOpen;
        registerButton.setText(elements.save());
        header.setText(elements.title_change_book());
        setTitle(elements.title_change_book());
        deleteButton.setVisible(true);
        backButton.setVisible(true);

        ServerResponse callback = new ServerResponse() {

            public void serverResponse(JSONValue responseObj) {
                JSONObject obj = responseObj.isObject();

                setText(bookNumber, obj, "usernumber");
                setText(bookSubNumber, obj, "subbook");
                setText(bookISBN, obj, "ISBN");
                setText(bookTitle, obj, "title");
                setText(bookOrgTitle, obj, "org_title");
                setText(bookYearWritten, obj, "written_year");
                setText(bookYearPublished, obj, "published_year");
                setText(bookEdition, obj, "edition");
                setText(bookImpression, obj, "impression");
                setText(bookPrice, obj, "price");
                setText(bookSeriesNmb, obj, "number_in_series");

                bookSubtitle.setText(Util.strSkipNull((obj.get("subtitle"))));

                setSuggest(categorySuggestBox, obj, "category_id", "category");
                setSuggest(authorSuggestBox, obj, "author_id", "author");
                setSuggest(readBySuggestBox, obj, "read_by_id", "readby");
                setSuggest(editorSuggestBox, obj, "editor_id", "editor");
                setSuggest(publisherSuggestBox, obj, "publisher_id", "publisher");
                setSuggest(seriesSuggestBox, obj, "series_id", "series");
                setSuggest(placementSuggestBox, obj, "placement_id", "placement");
                setSuggest(placementSuggestBox, obj, "illustrator_id", "illustrator");
            }

        };
        AuthResponder.get(constants, messages, callback, "registers/books.php?action=getfull&id=" + id);
    }

    protected void setText(TextBoxWithErrorText textbox, JSONObject obj, String string) {
        textbox.setText(Util.strSkipNull((obj.get(string))));
    }

    protected void setSuggest(GeneralSuggestBox box, JSONObject obj, String string, String string2) {
        box.setValue(Util.strSkipNull(obj.get(string)), Util.strSkipNull(obj.get(string2)));
    }
}

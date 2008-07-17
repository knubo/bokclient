package no.knubo.bok.client.views;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.misc.ImageFactory;
import no.knubo.bok.client.suggest.CategorySuggestBuilder;
import no.knubo.bok.client.suggest.PersonSuggestBuilder;
import no.knubo.bok.client.suggest.PlacementSuggestBuilder;
import no.knubo.bok.client.suggest.SeriesSuggestBuilder;
import no.knubo.bok.client.ui.TextBoxWithErrorText;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class BookEditView extends Composite {

	private static BookEditView instance;
	private final Messages messages;
	private final Constants constants;
	private FlexTable table;
	private Elements elements;
	private TextBoxWithErrorText bookNumber;
	private TextBoxWithErrorText bookISBN;
	private TextBoxWithErrorText bookTitle;
	private TextBoxWithErrorText bookOrgTitle;
	private SuggestBox bookAuthor;
	private SuggestBox bookEditor;
	private SuggestBox bookCOAuthor;
	private SuggestBox bookPlacement;
	private SuggestBox bookCategory;
	private int row;
	private int column;
	private Image addAuthor;
	private Image addCOAuthor;
	private Image addEditor;
	private Image addCategory;
	private Image addPlacement;
	private TextBoxWithErrorText bookPrice;
	private SuggestBox bookSeries;
	private TextBoxWithErrorText bookSeriesNmb;
	private Image addIllustrator;
	private SuggestBox bookIllustrator;
	private TextBoxWithErrorText bookYearWritten;
	private TextBoxWithErrorText bookYearPublished;
	private TextBoxWithErrorText bookEdition;
	private TextBoxWithErrorText bookImpression;
	private TextArea bookSubtitle;
	private int tabIndex = 1;

	public BookEditView(Messages messages, Constants constants,
			Elements elements) {
		this.messages = messages;
		this.constants = constants;
		this.elements = elements;

		table = new FlexTable();
		table.setStyleName("edittable");

		table.setText(0, 0, elements.title_new_book());
		table.getRowFormatter().setStyleName(0, "header");

		bookNumber = new TextBoxWithErrorText("bookNumber");
		bookNumber.setMaxLength(6);

		bookISBN = new TextBoxWithErrorText("bookISBN");
		bookISBN.setMaxLength(40);
		bookTitle = new TextBoxWithErrorText("bookTitle");
		bookTitle.setMaxLength(40);
		bookOrgTitle = new TextBoxWithErrorText("bookOrgTitle");
		bookOrgTitle.setMaxLength(40);
		bookAuthor = new SuggestBox(PersonSuggestBuilder.createPeopleOracle(
				constants, messages, "A"));
		bookCOAuthor = new SuggestBox(PersonSuggestBuilder.createPeopleOracle(
				constants, messages, "A"));
		bookEditor = new SuggestBox(PersonSuggestBuilder.createPeopleOracle(
				constants, messages, "E"));
		bookIllustrator = new SuggestBox(PersonSuggestBuilder
				.createPeopleOracle(constants, messages, "I"));
		bookPlacement = new SuggestBox(PlacementSuggestBuilder
				.createPlacementOracle(constants, messages));
		bookCategory = new SuggestBox(CategorySuggestBuilder
				.createCategoryOracle(constants, messages));
		bookPrice = new TextBoxWithErrorText("bookPrice");
		bookSeries = new SuggestBox(SeriesSuggestBuilder.createSeriesOracle(
				constants, messages));
		bookSeriesNmb = new TextBoxWithErrorText("bookSeriesNmb");
		bookYearWritten = new TextBoxWithErrorText("bookYearWritten");
		bookYearPublished = new TextBoxWithErrorText("bookYearPublished");
		bookEdition = new TextBoxWithErrorText("bookEdition");
		bookImpression = new TextBoxWithErrorText("bookImpression");
		bookSubtitle = new TextArea();
		bookSubtitle.setWidth("95%");
		bookSubtitle.setHeight("4em");

		addAuthor = ImageFactory.chooseImage("addAuthor");
		addCOAuthor = ImageFactory.chooseImage("addCOAuthor");
		addEditor = ImageFactory.chooseImage("addEditor");
		addCategory = ImageFactory.chooseImage("addCategory");
		addPlacement = ImageFactory.chooseImage("addPlacement");
		addIllustrator = ImageFactory.chooseImage("addIllustrator");

		row = 1;
		column = 0;
		addElement(elements.book_number(), bookNumber);
		addElement(elements.book_isbn(), bookISBN);
		addElement(elements.book_title(), bookTitle);
		addElement(elements.book_org_title(), bookOrgTitle);
		addElement(elements.book_subtitle(), bookSubtitle);
		addElement(elements.category(), bookCategory, addCategory);
		addElement(elements.book_author(), bookAuthor, addAuthor);
		addElement(elements.book_coauthor(), bookCOAuthor, addCOAuthor);
		addElement(elements.book_editor(), bookEditor, addEditor);
		addElement(elements.book_price(), bookPrice);

		row = 1;
		column = 3;
		addElement(elements.book_year_written(), bookYearWritten);
		addElement(elements.book_year_published(), bookYearPublished);
		addElement(elements.book_edition(), bookEdition);
		addElement(elements.book_impression(), bookImpression);
		addElement("", ImageFactory.blankImage(1, 1));
		addElement(elements.book_series(), bookSeries);
		addElement(elements.book_serie_nmb(), bookSeriesNmb);
		addElement(elements.book_placement(), bookPlacement, addPlacement);
		addElement(elements.book_illustrator(), bookIllustrator, addIllustrator);

		initWidget(table);
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
		}
	}

	public static BookEditView getInstance(Constants constants,
			Messages messages, Elements elements) {
		if (instance == null) {
			instance = new BookEditView(messages, constants, elements);
		}
		return instance;
	}

	public void init() {

	}

}

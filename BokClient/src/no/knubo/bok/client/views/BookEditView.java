package no.knubo.bok.client.views;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.ui.TextBoxWithErrorText;
import no.knubo.bok.client.util.PersonSuggestBuilder;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SuggestBox;

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

	public BookEditView(Messages messages, Constants constants,
			Elements elements) {
		this.messages = messages;
		this.constants = constants;
		this.elements = elements;

		table = new FlexTable();
		table.setStyleName("edittable");

		table.setText(0, 0, elements.title_new_book());
		table.getRowFormatter().setStyleName(0, "header");
		table.setText(1, 0, elements.book_number());
		table.setText(2, 0, elements.book_isbn());
		table.setText(3, 0, elements.book_title());
		table.setText(4, 0, elements.book_org_title());
		table.setText(5, 0, elements.book_author());
		table.setText(6, 0, elements.book_coauthor());

		bookNumber = new TextBoxWithErrorText("bookNumber");
		bookNumber.setMaxLength(6);
		bookISBN = new TextBoxWithErrorText("bookISBN");
		bookISBN.setMaxLength(40);
		bookTitle = new TextBoxWithErrorText("bookTitle");
		bookTitle.setMaxLength(40);
		bookOrgTitle = new TextBoxWithErrorText("orgTitle");
		bookOrgTitle.setMaxLength(40);
		bookAuthor = new SuggestBox(PersonSuggestBuilder.createPeopleOracle(constants, messages, "A"));
		bookCOAuthor = new SuggestBox(PersonSuggestBuilder.createPeopleOracle(constants, messages, "A"));
		bookEditor = new SuggestBox(PersonSuggestBuilder.createPeopleOracle(constants, messages, "E"));
		
		table.setWidget(1, 1, bookNumber);
		table.setWidget(2, 1, bookISBN);
		table.setWidget(3, 1, bookTitle);
		table.setWidget(4, 1, bookOrgTitle);
		table.setWidget(5, 1, bookAuthor);
		table.setWidget(6, 1, bookCOAuthor);

		initWidget(table);
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

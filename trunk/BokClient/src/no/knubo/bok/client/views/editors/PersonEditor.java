package no.knubo.bok.client.views.editors;

import java.util.List;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponse;
import no.knubo.bok.client.misc.ServerResponseWithValidation;
import no.knubo.bok.client.suggest.PersonSuggestBuilder;
import no.knubo.bok.client.ui.NamedButton;
import no.knubo.bok.client.ui.NamedCheckBox;
import no.knubo.bok.client.ui.TextBoxWithErrorText;
import no.knubo.bok.client.util.Picked;
import no.knubo.bok.client.validation.MasterValidator;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PersonEditor extends DialogBox implements ClickListener, Picked {

	private static PersonEditor me;
	private SuggestBox personSearch;
	private TextBoxWithErrorText firstName;
	private TextBoxWithErrorText lastName;
	private NamedCheckBox authorBox;
	private NamedCheckBox editorBox;
	private NamedCheckBox illustratorBox;
	private NamedCheckBox translatorBox;
	private Picked receiver;
	private NamedButton saveButton;
	private NamedButton cancelButton;
	private HTML mainErrorLabel;
	private TextBox personSearchBox;
	private final Constants constants;
	private final Messages messages;
	private int id;

	public static PersonEditor getInstance(Elements elements,
			Constants constants, Messages messages) {
		if (me == null) {
			me = new PersonEditor(elements, constants, messages);
		}

		me.init();
		me.center();
		me.personSearchBox.setFocus(true);
		return me;
	}

	private void init() {
		id = 0;
		firstName.setText("");
		lastName.setText("");
		personSearch.setText("");
		personSearchBox.setEnabled(true);
		mainErrorLabel.setText("");
		authorBox.setChecked(false);
		editorBox.setChecked(false);
		translatorBox.setChecked(false);
		illustratorBox.setChecked(false);
	}

	PersonEditor(Elements elements, Constants constants, Messages messages) {
		this.constants = constants;
		this.messages = messages;
		DockPanel dp = new DockPanel();
		FlexTable table = new FlexTable();
		dp.add(table, DockPanel.NORTH);
		table.setStyleName("edittable");

		setText(elements.create_new());

		personSearchBox = new TextBox();
		personSearch = new SuggestBox(PersonSuggestBuilder.createPeopleOracle(
				constants, messages, "AEIT", this), personSearchBox);
		firstName = new TextBoxWithErrorText("firstName");
		lastName = new TextBoxWithErrorText("lastName");
		authorBox = new NamedCheckBox("authorBox");
		editorBox = new NamedCheckBox("editorBox");
		translatorBox = new NamedCheckBox("translatorBox");
		illustratorBox = new NamedCheckBox("illustratorBox");

		table.setText(0, 0, elements.search());
		table.setText(1, 0, elements.person_firstname());
		table.setText(2, 0, elements.person_lastname());
		table.setText(3, 0, elements.book_author());
		table.setText(4, 0, elements.book_editor());
		table.setText(5, 0, elements.book_translator());
		table.setText(6, 0, elements.book_illustrator());

		table.setWidget(0, 1, personSearch);
		table.setWidget(1, 1, firstName);
		table.setWidget(2, 1, lastName);
		table.setWidget(3, 1, authorBox);
		table.setWidget(4, 1, editorBox);
		table.setWidget(5, 1, translatorBox);
		table.setWidget(6, 1, illustratorBox);

		saveButton = new NamedButton("savePerson", elements.save());

		saveButton.addClickListener(this);
		cancelButton = new NamedButton("cancelPerson", elements.cancel());
		cancelButton.addClickListener(this);

		mainErrorLabel = new HTML();
		mainErrorLabel.setStyleName("error");

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(mainErrorLabel);
		dp.add(buttonPanel, DockPanel.NORTH);
		setWidget(dp);

	}

	public void setReceiver(Picked receiver) {
		this.receiver = receiver;

	}

	public void onClick(Widget sender) {
		if (sender == cancelButton) {
			hide();
			return;
		}
		if (sender == saveButton && validateSave()) {
			save();
		}
	}

	private boolean validateSave() {
		MasterValidator mv = new MasterValidator();
		Widget[] widgets = new Widget[] { firstName, lastName };
		mv.mandatory(messages.required_field(), widgets);
		return mv.validateStatus();
	}

	private void save() {

		StringBuffer params = new StringBuffer();
		params.append("action=save");
		Util.addPostParam(params, "id", String.valueOf(id));
		Util.addPostParam(params, "firstname", firstName.getText());
		Util.addPostParam(params, "lastname", lastName.getText());
		Util.addPostParam(params, "author", authorBox.getStrBool());
		Util.addPostParam(params, "editor", editorBox.getStrBool());
		Util.addPostParam(params, "translator", translatorBox.getStrBool());
		Util.addPostParam(params, "illustrator", illustratorBox.getStrBool());

		ServerResponseWithValidation callback = new ServerResponseWithValidation() {

			public void serverResponse(JSONValue responseObj) {
				JSONObject object = responseObj.isObject();
				id = Util.getInt(object.get("id"));
				String strFirstName = Util.str(object.get("firstname"));
				String strLastName = Util.str(object.get("lastname"));
				receiver.idPicked(id, strLastName + ", " + strFirstName);
				hide();
			}

			public void validationError(List<String> fields) {
				if (fields.contains("duplicate")) {
					mainErrorLabel.setText(messages.person_duplicate());
				}
			}

		};

		AuthResponder.post(constants, messages, callback, params,
				"registers/people.php");

	}

	public void idPicked(int id, String info) {
		this.id = id;
		personSearchBox.setEnabled(false);

		ServerResponse callback = new ServerResponse() {

			public void serverResponse(JSONValue responseObj) {
				JSONObject object = responseObj.isObject();
				firstName.setText(Util.str(object.get("firstname")));
				lastName.setText(Util.str(object.get("lastname")));
				authorBox.setChecked(Util.getBoolean(object.get("author")));
				illustratorBox.setChecked(Util.getBoolean(object
						.get("illustrator")));
				editorBox.setChecked(Util.getBoolean(object.get("editor")));
				translatorBox.setChecked(Util.getBoolean(object
						.get("translator")));
			}

		};
		AuthResponder.get(constants, messages, callback,
				"registers/people.php?action=get&id=" + id);
	}
}

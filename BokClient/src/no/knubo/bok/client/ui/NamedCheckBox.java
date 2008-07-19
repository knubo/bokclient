package no.knubo.bok.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.CheckBox;

public class NamedCheckBox extends CheckBox {

	public NamedCheckBox(String id) {
		DOM.setElementAttribute(this.getElement(), "id", id);
	}

	public String getStrBool() {
		return isChecked() ? "1" : "0";
	}
}

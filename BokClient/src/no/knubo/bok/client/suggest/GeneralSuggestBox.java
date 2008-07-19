package no.knubo.bok.client.suggest;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.misc.ImageFactory;
import no.knubo.bok.client.util.Picked;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

abstract class GeneralSuggestBox implements ClickListener {
	protected final Constants constants;
	protected final Messages messages;
	protected final Elements elements;
	protected TextBox box;
	protected Picked picked;
	protected int currentId;
	protected Image addImage;
	protected Image editImage;
	protected HorizontalPanel imageContainer;
	protected SuggestBox suggestBox;
	protected final String type;

	public GeneralSuggestBox(String type, Constants constants,
			Messages messages, Elements elements) {
		this.type = type;
		this.constants = constants;
		this.messages = messages;
		this.elements = elements;

		box = new TextBox();

		addImage = ImageFactory.chooseImage("add" + type);
		editImage = ImageFactory.editImage("edit" + type);
		addImage.addClickListener(this);
		editImage.addClickListener(this);

		imageContainer = new HorizontalPanel();
		imageContainer.add(addImage);
		imageContainer.add(editImage);
		editImage.setVisible(false);

		picked = new Picked() {
			public void idPicked(int id, String info) {
				currentId = id;
				box.setEnabled(false);
				suggestBox.setText(info);
				editImage.setVisible(true);
				addImage.setVisible(false);

			}
		};
		suggestBox = new SuggestBox(getOracle(), box);
	}

	
	
	public HorizontalPanel getImageContainer() {
		return imageContainer;
	}

	public void setImageContainer(HorizontalPanel imageContainer) {
		this.imageContainer = imageContainer;
	}

	public SuggestBox getSuggestBox() {
		return suggestBox;
	}

	abstract public void openEditor();
	abstract public SuggestOracle getOracle();
	
	public void onClick(Widget sender) {
		if (sender == addImage) {
			openEditor();
		} else if (sender == editImage) {
			editImage.setVisible(false);
			addImage.setVisible(true);
			box.setEnabled(true);
			box.setText("");
			box.setFocus(true);
		}
	}

}

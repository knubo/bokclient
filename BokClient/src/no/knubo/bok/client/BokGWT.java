package no.knubo.bok.client;

import no.knubo.bok.client.misc.ImageFactory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BokGWT implements EntryPoint {

	private DockPanel activeView;
	private static BokGWT meBook;
	private MenuBar topMenu;
	private Image blankImage;
	private Image loadingImage;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		meBook = this;
		DockPanel docPanel = new DockPanel();
		docPanel.setWidth("100%");
		docPanel.setHeight("100%");

		topMenu = new MenuBar();
		topMenu.setWidth("100%");
		blankImage = ImageFactory.blankImage(16, 16);
		blankImage.setVisible(true);
		loadingImage = ImageFactory.loadingImage("loading");
		loadingImage.setVisible(false);
		docPanel.add(blankImage, DockPanel.WEST);
		docPanel.add(loadingImage, DockPanel.WEST);
		docPanel.add(topMenu, DockPanel.NORTH);
		docPanel.setCellHeight(topMenu, "10px   ");

		activeView = new DockPanel();
		activeView.setStyleName("activeview");
		docPanel.add(activeView, DockPanel.CENTER);

		RootPanel.get().add(docPanel);
	}

	public static void setLoading() {
		if (meBook.loadingImage != null) {
			meBook.loadingImage.setVisible(true);
		}
		if (meBook.blankImage != null) {
			meBook.blankImage.setVisible(false);
		}
	}

	public static void setDoneLoading() {
		if (meBook.loadingImage != null) {
			meBook.loadingImage.setVisible(false);
		}
		if (meBook.blankImage != null) {
			meBook.blankImage.setVisible(true);
		}
	}

	public static void loginMode() {
		meBook.topMenu.setVisible(false);
	}

	public static void normalMode() {
		meBook.topMenu.setVisible(true);
	}

}

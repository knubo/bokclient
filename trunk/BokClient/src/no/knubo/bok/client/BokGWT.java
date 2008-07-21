package no.knubo.bok.client;

import no.knubo.bok.client.misc.ImageFactory;
import no.knubo.bok.client.misc.WidgetIds;
import no.knubo.bok.client.views.AboutView;
import no.knubo.bok.client.views.BackupView;
import no.knubo.bok.client.views.BookEditView;
import no.knubo.bok.client.views.LogView;
import no.knubo.bok.client.views.LoginView;
import no.knubo.bok.client.views.LogoutView;
import no.knubo.bok.client.views.QuickBookSearch;
import no.knubo.bok.client.views.registers.UsersEditView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BokGWT implements EntryPoint {

	private DockPanel activeView;
	private static BokGWT meBook;
	private MenuBar topMenu;
	private Image blankImage;
	private Image loadingImage;
	private Messages messages;
	private Constants constants;
	private Elements elements;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		meBook = this;
		messages = (Messages) GWT.create(Messages.class);
		constants = (Constants) GWT.create(Constants.class);
		elements = (Elements) GWT.create(Elements.class);

		DockPanel docPanel = new DockPanel();
		docPanel.setWidth("100%");
		docPanel.setHeight("100%");

		topMenu = new MenuBar();
		topMenu.setWidth("100%");
		setupMenu();

		blankImage = ImageFactory.blankImage(16, 16);
		blankImage.setVisible(true);
		loadingImage = ImageFactory.loadingImage("loading");
		loadingImage.setVisible(false);
		docPanel.add(blankImage, DockPanel.WEST);
		docPanel.add(loadingImage, DockPanel.WEST);
		docPanel.add(topMenu, DockPanel.NORTH);
		docPanel.setCellHeight(topMenu, "10px");

		activeView = new DockPanel();
		activeView.setStyleName("activeview");
		docPanel.add(activeView, DockPanel.CENTER);

		new Commando(WidgetIds.ABOUT, elements.menuitem_about()).execute();

		RootPanel.get().add(docPanel);
	}

	private void setupMenu() {
		MenuBar booksMenu = addTopMenu(topMenu, elements.menu_books());
		MenuBar reportsMenu = addTopMenu(topMenu, elements.menu_reports());
		MenuBar settingsMenu = addTopMenu(topMenu, elements.menu_settings());
		MenuBar logoutMenu = addTopMenu(topMenu, elements.menu_logout());
		MenuBar aboutMenu = addTopMenu(topMenu, elements.menu_info());
		
		addMenuItem(reportsMenu, elements.menuitem_report_all_books(),WidgetIds.REPORT_ALL_BOOKS);
		addMenuItem(reportsMenu, elements.menuitem_report_books_author(), WidgetIds.REPORT_AUTHOR);
		addMenuItem(reportsMenu, elements.menuitem_report_placement(), WidgetIds.REPORT_PLACEMENT);
		addMenuItem(reportsMenu, elements.menuitem_report_top_author(), WidgetIds.REPORT_TOP_AUTHORS);
		
		addMenuItem(booksMenu, elements.menuitem_new_book(),
				WidgetIds.REGISTER_BOOK);
		addMenuItem(booksMenu, elements.menuitem_book_fast_search(),
				WidgetIds.QUICK_SEARCH);
		addMenuItem(booksMenu, elements.menuitem_book_search(),
				WidgetIds.SEARCH_BOOKS);
		booksMenu.addSeparator();
		addMenuItem(booksMenu, elements.menuitem_book_people(),
				WidgetIds.PEOPLE);
		addMenuItem(booksMenu, elements.menuitem_book_placements(),
				WidgetIds.PLACEMENTS);
		addMenuItem(booksMenu, elements.menuitem_book_categories(),
				WidgetIds.CATEGORIES);
		addMenuItem(booksMenu, elements.menuitem_book_publishers(),
				WidgetIds.PUBLISHERS);
		addMenuItem(booksMenu, elements.menuitem_book_series(),
				WidgetIds.SERIES);

		
		addMenuItem(settingsMenu, elements.menuitem_useradm(),
				WidgetIds.EDIT_USERS);

		addMenuItem(aboutMenu, elements.menuitem_about(), WidgetIds.ABOUT);
		addMenuItem(aboutMenu, elements.menuitem_log(), WidgetIds.LOGGING);
		addMenuItem(aboutMenu, elements.menuitem_backup(), WidgetIds.BACKUP);
		addMenuItem(logoutMenu, elements.menuitem_logout(), WidgetIds.LOGOUT);

	}

	private void addMenuItem(MenuBar menu, String title, WidgetIds widgetId) {
		menu.addItem(title, true, new Commando(widgetId, title));
	}

	private MenuBar addTopMenu(MenuBar topMenu, String header) {
		MenuBar menu = new MenuBar(true);
		topMenu.addItem(new MenuItem(header, menu));
		return menu;
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
		meBook.loginModeInt();
	}

	private void loginModeInt() {
		topMenu.setVisible(false);
		new Commando(WidgetIds.LOGIN, elements.login()).execute();
	}

	public static void normalMode() {
		meBook.normalModeInt();
	}

	private void normalModeInt() {
		topMenu.setVisible(true);
		new Commando(WidgetIds.ABOUT, elements.menuitem_about()).execute();
	}

	class Commando implements Command {

		WidgetIds action;

		private String title;

		Commando(WidgetIds action, String title) {
			this.action = action;
			this.title = title;
		}

		public void execute() {
			Widget widget = null;

			switch (action) {
			case ABOUT:
				widget = AboutView.getInstance(constants, messages, elements);
				((AboutView) widget).init();
				break;
			case BACKUP:
				widget = BackupView.getInstance(constants, messages, elements);
				((BackupView) widget).init();
				break;
			case EDIT_USERS:
				widget = UsersEditView.show(messages, constants, elements);
				((UsersEditView) widget).init();
				break;
			case LOGGING:
				widget = LogView.show(messages, constants, elements);
				((LogView) widget).init();
				break;
			case LOGIN:
				widget = LoginView.getInstance(elements, constants, messages);
				((LoginView) widget).init();
				break;
			case LOGOUT:
				widget = LogoutView.getInstance(constants, messages, elements);
				break;
			case REGISTER_BOOK:
				widget = BookEditView
						.getInstance(constants, messages, elements);
				((BookEditView) widget).init();
				break;
			case QUICK_SEARCH:
				widget = QuickBookSearch.getInstance(elements, constants, messages);
				break;
			}

			if (widget == null) {
				Window.alert("No action");
				return;
			}
			setActiveWidget(widget);
			if (widget.getTitle() != null && widget.getTitle().length() > 0) {
				Window.setTitle(widget.getTitle());
			} else {
				Window.setTitle(title);
			}
		}
	}

	private void setActiveWidget(Widget widget) {
		activeView.clear();
		activeView.add(widget, DockPanel.CENTER);
		activeView.setCellHeight(widget, "100%");
		activeView.setCellVerticalAlignment(widget,
				HasVerticalAlignment.ALIGN_TOP);

	}
}

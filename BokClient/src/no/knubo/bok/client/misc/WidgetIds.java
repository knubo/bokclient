package no.knubo.bok.client.misc;

public enum WidgetIds {
	AUTHOR(1), SEARCH_BOOKS(2), REGISTER_BOOK(3), ILLUSTRATOR(4), EDIT_USERS(5), ABOUT(6), LOGGING(7), BACKUP(8), LOGOUT(9), LOGIN(10);
    private final int helpPageValue;

    WidgetIds(int value) {
        this.helpPageValue = value;
    }

    public int getHelpPageValue() {
        return helpPageValue;
    }

}

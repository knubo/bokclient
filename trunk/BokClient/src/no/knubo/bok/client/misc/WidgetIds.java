package no.knubo.bok.client.misc;

public enum WidgetIds {
    PEOPLE(1), SEARCH_BOOKS(2), REGISTER_BOOK(3), QUICK_SEARCH(4), EDIT_USERS(5), ABOUT(6), LOGGING(7), BACKUP(8), LOGOUT(9), LOGIN(10), PUBLISHERS(
            11), PLACEMENTS(12), CATEGORIES(13), SERIES(14), REPORT_PLACEMENT(17), REPORT_TOP_AUTHORS(18), REPORT_NO_PLACEMENT(19), REPORT_CATEGORY(20);
    private final int helpPageValue;

    WidgetIds(int value) {
        this.helpPageValue = value;
    }

    public int getHelpPageValue() {
        return helpPageValue;
    }

}

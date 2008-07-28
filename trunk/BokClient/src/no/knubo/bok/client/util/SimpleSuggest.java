package no.knubo.bok.client.util;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class SimpleSuggest implements Suggestion {

    private final String display;
    private final String replace;

    public SimpleSuggest(String display, String replace) {
        this.display = display;
        this.replace = replace;
    }
    
    public SimpleSuggest(String x) {
        this.display = x;
        this.replace = x;
    }

    public String getDisplayString() {
        return display;
    }

    public String getReplacementString() {
        return replace;
    }

}

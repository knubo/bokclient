package no.knubo.bok.client.util;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class GeneralSuggest extends SimpleSuggest implements Suggestion {

    private final int id;
    private final Picked picked;

    public GeneralSuggest(String display, String replace, int id, Picked picked) {
        super(display, replace);
        this.id = id;
        this.picked = picked;

    }

    public GeneralSuggest(String suggest, int id, Picked picked) {
        super(suggest, suggest);
        this.id = id;
        this.picked = picked;
    }

    @Override
    public String getReplacementString() {
        String rep = super.getReplacementString();
        if (picked != null) {
            picked.idPicked(id, rep);
        }

        return rep;
    }

}
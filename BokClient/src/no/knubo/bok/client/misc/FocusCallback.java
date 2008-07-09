package no.knubo.bok.client.misc;

import no.knubo.bok.client.ui.ErrorLabelWidget;
import no.knubo.bok.client.validation.Validateable;


public interface FocusCallback {

    void onLostFocus(ErrorLabelWidget me);

    void onFocus(Validateable me);
}

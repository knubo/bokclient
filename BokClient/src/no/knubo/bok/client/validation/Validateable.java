package no.knubo.bok.client.validation;

public interface Validateable {

    public String getText();

    /**
     * Sets error to be displayed if error occurs.
     * 
     * @param text
     *            The text.
     */
    public void setErrorText(String text);

    public void setFocus(boolean b);

    public void setMouseOver(String mouseOver);
}

package no.knubo.bok.client.views.net;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.ServerResponse;
import no.knubo.bok.client.model.Book;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ExternalBookHelper {
    private ListBox authorBox;
    private ListBox publisherBox;

    private CheckBox titleCheckBox;
    private CheckBox authorCheckBox;
    private CheckBox publisherCheckBox;
    private CheckBox yearWrittenCheckBox;

    private Label titleLabel;
    private Label writtenYearLabel;
    private Label authorLabel;
    private Label publisherLabel;
    private HTML externalURL;
    private final Constants constants;
    private final Messages messages;
    private final Elements elements;
    private HTML noteLabel;

    public ListBox getAuthorBox() {
        return authorBox;
    }

    public ListBox getPublisherBox() {
        return publisherBox;
    }

    public CheckBox getTitleCheckBox() {
        return titleCheckBox;
    }

    public CheckBox getAuthorCheckBox() {
        return authorCheckBox;
    }

    public CheckBox getPublisherCheckBox() {
        return publisherCheckBox;
    }

    public CheckBox getYearWrittenCheckBox() {
        return yearWrittenCheckBox;
    }

    public ExternalBookHelper(Constants constants, Messages messages, Elements elements) {
        this.constants = constants;
        this.messages = messages;
        this.elements = elements;
        titleCheckBox = new CheckBox();
        authorCheckBox = new CheckBox();
        publisherCheckBox = new CheckBox();
        yearWrittenCheckBox = new CheckBox();
        authorBox = new ListBox();
        publisherBox = new ListBox();
        externalURL = new HTML();
        titleLabel = new Label();
        writtenYearLabel = new Label();
        authorLabel = new Label();
        publisherLabel = new Label();
        noteLabel = new HTML();
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public HTML getExternalURL() {
        return externalURL;
    }

    public void init(String initURL) {
        titleLabel.setText("");
        publisherLabel.setText("");
        authorLabel.setText("");
        writtenYearLabel.setText("");
        noteLabel.setText("");
        externalURL.setHTML("");
        authorBox.clear();
        publisherBox.clear();
        titleCheckBox.setValue(false);
        authorCheckBox.setValue(false);
        publisherCheckBox.setValue(false);
        yearWrittenCheckBox.setValue(false);
        fetchData(initURL);
    }

    private void fetchData(String initURL) {
        ServerResponse callback = new ServerResponse() {

            public void serverResponse(JSONValue responseObj) {
                JSONObject object = responseObj.isObject();

                if (object.containsKey("failed")) {
                    return;
                }

                externalURL.setHTML("<a target=\"_BLANK\" href=\"" + Util.str(object.get("url")) + "\">" + elements.external_link() + "</a>");

                JSONObject info = object.get("info").isObject();

                titleCheckBox.setValue(true);
                titleLabel.setText(Util.strSkipNull(info.get("title")));

                if (info.containsKey("author")) {
                    String authorStr = Util.strSkipNull(info.get("author"));
                    fetchAuthorInfo(authorStr);
                    authorLabel.setText(authorStr);
                }
                authorCheckBox.setValue(info.containsKey("author"));

                if (info.containsKey("publisher")) {
                    String publisherStr = Util.strSkipNull(info.get("publisher"));
                    fetchPublisherInfo(publisherStr);
                    publisherLabel.setText(publisherStr);
                }
                publisherCheckBox.setValue(info.containsKey("publisher"));

                writtenYearLabel.setText(Util.strSkipNull(info.get("written_year")));
                yearWrittenCheckBox.setValue(true);

                if (info.containsKey("note")) {
                    noteLabel.setHTML(Util.strSkipNull(info.get("note")));
                }
            }

        };
        AuthResponder.get(constants, messages, callback, initURL);
    }

    protected void fetchPublisherInfo(String publisherStr) {
        StringBuffer sb = new StringBuffer();
        sb.append("action=search");
        Util.addPostParam(sb, "search", publisherStr.replace(".", " ").replace(",", " ").trim());
        Util.addPostParam(sb, "limit", "10");

        AuthResponder.post(constants, messages, publisherCallback(), sb, "registers/publishers.php");
    }

    private ServerResponse publisherCallback() {
        return new ServerResponse() {

            public void serverResponse(JSONValue responseObj) {
                JSONArray array = responseObj.isArray();
                for (int i = 0; i < array.size(); i++) {
                    JSONValue value = array.get(i);

                    JSONObject object = value.isObject();

                    String id = Util.str(object.get("id"));
                    String name = Util.str(object.get("name"));

                    publisherBox.addItem(name, id);
                }
            }

        };
    }

    protected void fetchAuthorInfo(String authorStr) {
        StringBuffer sb = new StringBuffer();
        sb.append("action=search");
        Util.addPostParam(sb, "search", authorStr.replace(".", " ").replace(",", " ").trim());
        Util.addPostParam(sb, "limit", "10");
        Util.addPostParam(sb, "type", "A");

        AuthResponder.post(constants, messages, authorCallback(), sb, "registers/people.php");
    }

    private ServerResponse authorCallback() {
        return new ServerResponse() {

            public void serverResponse(JSONValue responseObj) {
                JSONArray array = responseObj.isArray();
                for (int i = 0; i < array.size(); i++) {
                    JSONValue value = array.get(i);

                    JSONObject object = value.isObject();

                    String id = Util.str(object.get("id"));
                    String firstname = Util.str(object.get("firstname"));
                    String lastname = Util.str(object.get("lastname"));
                    String suggest = lastname + ", " + firstname;

                    authorBox.addItem(suggest, id);
                }
            }

        };
    }

    public void fillBookInfo(Book book) {

        if (titleCheckBox.getValue()) {
            book.setTitle(titleLabel.getText());
        }

        if (authorCheckBox.getValue() && authorBox.getSelectedIndex() >= 0) {
            book.setAuthorId(Util.getInt(authorBox.getValue(authorBox.getSelectedIndex())));
            book.setAuthor(authorBox.getItemText(authorBox.getSelectedIndex()));
        }

        if (publisherCheckBox.getValue() && publisherBox.getSelectedIndex() >= 0) {
            book.setPublisherId(Util.getInt(publisherBox.getValue(publisherBox.getSelectedIndex())));
            book.setPublisher(publisherBox.getItemText(publisherBox.getSelectedIndex()));
        }

        if (yearWrittenCheckBox.getValue()) {
            book.setWrittenYear(Util.getInt(writtenYearLabel.getText()));
        }
    }

    public Label getWrittenYearLabel() {
        return writtenYearLabel;
    }

    public Label getAuthorLabel() {
        return authorLabel;
    }

    public Label getPublisherLabel() {
        return publisherLabel;
    }

    public HTML getNoteLabel() {
        return noteLabel;
    }

    public void link(ExternalBookHelper otherHelper) {
        linkCheckbox(titleCheckBox, otherHelper.getTitleCheckBox());
        linkCheckbox(authorCheckBox, otherHelper.getAuthorCheckBox());
        linkCheckbox(publisherCheckBox, otherHelper.getPublisherCheckBox());
        linkCheckbox(yearWrittenCheckBox, otherHelper.getYearWrittenCheckBox());
    }

    private void linkCheckbox(final CheckBox check1, final CheckBox check2) {
        ClickListener cl = new ClickListener() {

            public void onClick(Widget sender) {
                
                if(sender == check1 && check1.getValue()) {
                    check2.setValue(false);
                } else if(sender == check2 && check2.getValue()) {
                    check1.setValue(false);
                }
            }
            
        };
        check1.addClickListener(cl);
        check2.addClickListener(cl);
    }


}

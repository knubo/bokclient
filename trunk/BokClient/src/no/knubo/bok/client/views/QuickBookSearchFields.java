package no.knubo.bok.client.views;

import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.ImageFactory;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class QuickBookSearchFields implements ClickListener {
    private FlexTable infoTable;
    private Image editImage;
    private int currentId;
    private ViewCallback viewCallback;
    private final Elements elements;

    public QuickBookSearchFields(ViewCallback viewCallback, Elements elements) {
        this.viewCallback = viewCallback;
        this.elements = elements;
    }

    public FlexTable getInfoTable() {
        return infoTable;
    }

    public void setInfoTable(FlexTable infoTable) {
        this.infoTable = infoTable;
    }

    public Image getEditImage() {
        return editImage;
    }

    public void setEditImage(Image editImage) {
        this.editImage = editImage;
    }

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public void drawBookLabels() {
        setInfoTable(new FlexTable());
        getInfoTable().addStyleName("tableborder");
        int row = 0;
        getInfoTable().setText(row++, 0, elements.book_number());
        getInfoTable().setText(row++, 0, elements.book_isbn());
        getInfoTable().setText(row++, 0, elements.book_title());
        getInfoTable().setText(row++, 0, elements.book_org_title());
        getInfoTable().setText(row++, 0, elements.book_subtitle());
        getInfoTable().setText(row++, 0, elements.category());
        getInfoTable().setText(row++, 0, elements.book_author());
        getInfoTable().setText(row++, 0, elements.book_coauthor());
        getInfoTable().setText(row++, 0, elements.book_editor());
        getInfoTable().setText(row++, 0, elements.book_translator());
        getInfoTable().setText(row++, 0, elements.book_publisher());
        getInfoTable().setText(row++, 0, elements.book_year_written());
        getInfoTable().setText(row++, 0, elements.book_year_published());
        getInfoTable().setText(row++, 0, elements.book_edition());
        getInfoTable().setText(row++, 0, elements.book_impression());
        getInfoTable().setText(row++, 0, elements.book_price());
        getInfoTable().setText(row++, 0, elements.book_series());
        getInfoTable().setText(row++, 0, elements.book_serie_nmb());
        getInfoTable().setText(row++, 0, elements.book_placement());
        getInfoTable().setText(row++, 0, elements.book_illustrator());

        setEditImage(ImageFactory.editImage("editBook"));
        getEditImage().addClickListener(this);
        getInfoTable().setWidget(0, 2, getEditImage());

    }

    public void onClick(Widget sender) {
        viewCallback.editBook(getCurrentId());
    }

    private void setBookFields(JSONObject obj) {
        String[] fields = { "ISBN", "title", "org_title", "subtitle", "category", "author", "coauthor", "editor", "translator", "publisher",
                "written_year", "published_year", "edition", "impression", "price", "series", "number_in_series", "placement", "illustrator" };
    
        int row = 0;
        getInfoTable().setText(row++, 1, Util.strSkipNull(obj.get("usernumber")) + Util.strSkipNull(obj.get("subbook")));
    
        for (String keyX : fields) {
            getInfoTable().setText(row++, 1, Util.strSkipNull(obj.get(keyX)));
        }
    
        for (int i = 1; i < row; i++) {
            getInfoTable().getFlexCellFormatter().setColSpan(i, 1, 2);
        }
    }

    void setBookInfo(JSONObject obj) {
        setCurrentId(Util.getInt(obj.get("id")));
        setBookFields(obj);
    }
}
package no.knubo.bok.client.ui;

import java.util.List;

public interface TableRowSelected {
    public void selected(String id);
    public void selectedWithShift(List<String> list);
}

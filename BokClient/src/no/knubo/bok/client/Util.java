package no.knubo.bok.client;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Various nifty utilities for the project.
 */
public class Util {

    /**
     * Forwards the client side browser to the given location.
     * 
     * @param msg
     *            The url to forward to.
     */
    public static native void forward(String url) /*-{
                                $wnd.location.href = url;
                                }-*/;

    public static boolean getBoolean(JSONValue str) {
        return "1".equals(str(str));
    }

 

    /**
     * Formats a jsonvalue as date 'dd.mm.yyyy' assumed from a date of format
     * 'yyyy-mm-dd'.
     * 
     * @param value
     *            The jason value
     * @return The value, or toString if it isn't a jsonString.
     */
    public static String formatDate(JSONValue value) {
        JSONString string = value.isString();

        if (string == null) {
            return value.toString();
        }
        String[] dateparts = string.stringValue().split("-");

        return dateparts[2] + "." + dateparts[1] + "." + dateparts[0];
    }

    /**
     * Extracts a java string from a jsonvalue.
     * 
     * @param value
     *            The value to extract.
     * @return The string or toString() if not a string for clarity.
     */
    public static String str(JSONValue value) {
        if (value == null) {
            return "ERROR";
        }

        JSONString string = value.isString();
        if (string == null) {
            return value.toString().trim();
        }
        return string.stringValue().trim();
    }

    public static String money(JSONValue value) {
        if (value == null) {
            return "ERROR";
        }

        JSONString string = value.isString();
        if (string == null) {
            return money(value.toString());
        }
        return money(string.stringValue());
    }

    public static String money(String original) {
        if (original.charAt(0) == '-') {
            return "-" + money(original.substring(1));
        }

        int sepPos = original.indexOf('.');

        String x = null;
        if (sepPos == -1) {
            x = original;
        } else {
            x = original.substring(0, sepPos);
        }
        // 100000000.00
        int count = x.length() / 3;

        if (count == 0) {
            return x + fixDecimal(original, sepPos);
        }
        int left = x.length() % 3;

        String res = null;
        if (left > 0) {
            res = x.substring(0, left);
            if (count > 0) {
                res += ",";
            }
        } else {
            res = "";
        }

        for (int i = left; i < x.length(); i += 3) {
            res += x.substring(i, i + 3);

            if (i + 3 < x.length()) {
                res += ",";
            }
        }
        return res + fixDecimal(original, sepPos);
    }

    private static String fixDecimal(String str, int sepPos) {
        if (sepPos == -1) {
            return ".00";
        }

        String sub = str.substring(sepPos);
        switch (sub.length()) {
        case 3:
            return sub;
        case 2:
            return sub + "0";
        default:
            return sub.substring(0, 3);
        }
    }

    /**
     * Adds listeners to the listbox and textbox so that the selected elements
     * id is displayed in the textbox when selected and visa versa for the
     * textbox.
     * 
     * @param listbox
     * @param textbox
     */
    public static void syncListbox(final ListBox listbox, final TextBox textbox) {
        ChangeListener listchange = new ChangeListener() {

            public void onChange(Widget sender) {
                textbox.setText(listbox.getValue(listbox.getSelectedIndex()));
            }

        };
        listbox.addChangeListener(listchange);

        ChangeListener textchange = new ChangeListener() {

            public void onChange(Widget sender) {
                String id = textbox.getText();

                for (int i = 0; i < listbox.getItemCount(); i++) {
                    if (listbox.getValue(i).equals(id)) {
                        listbox.setSelectedIndex(i);
                        return;
                    }
                }
            }

        };
        textbox.addChangeListener(textchange);
    }

    /**
     * Get month part of string on format "dd.mm.yyyy".
     * 
     * @param value
     * @return
     */
    public static int getMonth(JSONValue value) {
        JSONString string = value.isString();

        if (string == null) {
            return 0;
        }

        return Integer.parseInt(string.stringValue().substring(3, 5));
    }

    /**
     * Get year part of string on format dd.mm.yyyy
     * 
     * @param value
     * @return
     */
    public static int getYear(JSONValue value) {
        JSONString string = value.isString();

        if (string == null) {
            return 0;
        }

        return Integer.parseInt(string.stringValue().substring(6));
    }

    /**
     * Get day part of string on format dd.mm.yyyy
     * 
     * @param value
     * @return
     */
    public static String getDay(JSONValue value) {
        JSONString string = value.isString();

        if (string == null) {
            return "ERROR";
        }

        return string.stringValue().substring(0, 2);
    }

    public static HashMap<Label, Timer> timers = new HashMap<Label, Timer>();

    /**
     * Sets given message after some seconds.
     * 
     * @param label
     *            Label to set text in.
     * @param message
     *            Text to set.
     * @param seconds
     *            The amount of seconds before the text is set. If 0 timer is removed.
     */
    public static void timedMessage(final Label label, final String message, int seconds) {

        Timer runningTimer = timers.get(label);

        if (runningTimer != null) {
            runningTimer.cancel();
        }

        if(seconds == 0) {
        	timers.remove(label);
        	return;
        }
        
        Timer timer = new Timer() {

            public void run() {
                label.setText(message);
                timers.remove(label);
            }
        };
        timers.put(label, timer);
        timer.schedule(seconds * 1000);
    }

    /**
     * Adds &param=value encoded to the stringbuffer. Note the &.
     * 
     * @param sb
     *            The string buffer to add
     * @param param
     *            the paramameter name
     * @param value
     *            The value. if it is null or of length 0 it is not added.
     */
    public static void addPostParam(StringBuffer sb, String param, String value) {
        if (value == null || value.length() == 0) {
            return;
        }
        sb.append("&");
        sb.append(URL.encodeComponent(param));
        sb.append("=");
        sb.append(URL.encodeComponent(value));
    }

    public static String getSelected(ListBox box) {
        if (box.getItemCount() == 0) {
            return "";
        }
        return box.getValue(box.getSelectedIndex());
    }

    public static String fixMoney(String original) {

        String text = original.replace(',', '.');

        int posp = text.indexOf('.');

        if (posp == -1) {
            return text + ".00";
        }

        /* Add the last 0 if just one number after . */
        if (text.substring(posp + 1).length() == 1) {
            return text + "0";
        }

        return text;
    }

    public static void setIndexByValue(ListBox listbox, String match) {
        if (listbox.getItemCount() == 0) {
            Window.alert("No items in combobox");
            return;
        }

        if (match == null) {
            Window.alert("Can't match null.");
            return;
        }

        for (int i = listbox.getItemCount(); i-- > 0;) {
            if (match.equals(listbox.getValue(i))) {
                listbox.setSelectedIndex(i);
                return;
            }
        }
    }

    public static int getInt(JSONValue value) {
        if (value == null || isNull(value)) {
            return 0;
        }

        if (value.isNumber() != null) {
            JSONNumber numb = value.isNumber();
            double dub = numb.doubleValue();
            return (int) dub;
        }

        String str = str(value);
        return Integer.parseInt(str.trim());
    }

    /**
     * Links two checkboxes and make sure that only one is checked at a time.
     * 
     * @param checkOne
     * @param checkTwo
     */
    public static void linkJustOne(final CheckBox checkOne, final CheckBox checkTwo) {
        ClickListener listener = new ClickListener() {

            public void onClick(Widget sender) {
                if (sender == checkOne) {
                    if (checkTwo.isEnabled() && checkOne.isChecked()) {
                        checkTwo.setChecked(false);
                    } else {
                        checkOne.setChecked(false);
                    }
                } else if (sender == checkTwo) {
                    if (checkOne.isEnabled() && checkTwo.isChecked()) {
                        checkOne.setChecked(false);
                    } else {
                        checkTwo.setChecked(false);
                    }
                }
            }

        };
        checkOne.addClickListener(listener);
        checkTwo.addClickListener(listener);
    }

    public static boolean isNull(JSONValue value) {
        String string = str(value);
        if (string.equals("null")) {
            return true;
        }
        return false;
    }

    public static String strSkipNull(JSONValue value) {
        String string = str(value);
        if (string.equals("null")) {
            return "";
        }
        return string;
    }

    public static int getInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void setCellId(FlexTable table, int row, int col, String id) {
        DOM.setElementAttribute(table.getCellFormatter().getElement(row, col), "id", id);
    }

    /**
     * Iterates fields and looks them up in translate. If found, it uses that
     * value, if not found it writes the non translated value in [].
     * 
     * @param fields
     * @param translate
     * @return The concatenated field list.
     */
    public static String translate(List<String> fields, HashMap<String,String> translate) {
        StringBuffer sb = new StringBuffer();

        for (String fieldName : fields) {

            String translated = translate.get(fieldName);

            if (sb.length() > 0) {
                sb.append(", ");
            }
            if (translated != null) {
                sb.append(translated);
            } else {
                sb.append("[" + fieldName + "]");
            }
        }
        return sb.toString();
    }

    
    public static native void log(String string) /*-{
        if(window['console'])
        window['console'].log(string);
        }-*/;

}

package no.knubo.bok.client.views.registers;

import java.util.HashMap;

import no.knubo.bok.client.Constants;
import no.knubo.bok.client.Elements;
import no.knubo.bok.client.Messages;
import no.knubo.bok.client.Util;
import no.knubo.bok.client.misc.AuthResponder;
import no.knubo.bok.client.misc.IdHolder;
import no.knubo.bok.client.misc.ImageFactory;
import no.knubo.bok.client.misc.ServerResponse;
import no.knubo.bok.client.ui.ListBoxWithErrorText;
import no.knubo.bok.client.ui.NamedButton;
import no.knubo.bok.client.ui.TextBoxWithErrorText;
import no.knubo.bok.client.validation.MasterValidator;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class UsersEditView extends Composite implements ClickListener {

    private static UsersEditView me;

    private final Constants constants;

    private final Messages messages;

    private FlexTable table;

    private IdHolder<String, Image> idHolderEditImages;
    private IdHolder<String, Image> idHolderDeleteImages;

    private Button newButton;

    private UserEditFields editFields;

    private HashMap<String, JSONObject> objectPerUsername;

    private Elements elements;

    public UsersEditView(Messages messages, Constants constants, Elements elements) {
        this.messages = messages;
        this.constants = constants;
        this.elements = elements;

        DockPanel dp = new DockPanel();

        table = new FlexTable();
        table.setStyleName("tableborder");
        table.setHTML(0, 0, elements.title_user_adm());
        table.getRowFormatter().setStyleName(0, "header");
        table.getFlexCellFormatter().setColSpan(0, 0, 5);

        table.setHTML(1, 0, elements.user());
        table.setHTML(1, 1, elements.name());
        table.setHTML(1, 2, elements.access());
        table.setHTML(1, 3, "");
        table.setHTML(1, 4, "");
        table.getRowFormatter().setStyleName(1, "header");

        newButton = new NamedButton("userEditView.newButton", elements.userEditView_newButton());
        newButton.addClickListener(this);

        dp.add(newButton, DockPanel.NORTH);
        dp.add(table, DockPanel.NORTH);

        idHolderEditImages = new IdHolder<String, Image>();
        idHolderDeleteImages = new IdHolder<String, Image>();
        initWidget(dp);
        setTitle(elements.menuitem_useradm());
    }

    public static UsersEditView show(Messages messages, Constants constants, Elements elements) {
        if (me == null) {
            me = new UsersEditView(messages, constants, elements);
        }
        me.setVisible(true);
        return me;
    }

    public void onClick(Widget sender) {

        if (sender == newButton || idHolderEditImages.findId(sender) != null) {
            if (editFields == null) {
                editFields = new UserEditFields();
            }

            int left = 0;
            if (sender == newButton) {
                left = sender.getAbsoluteLeft() + 10;
            } else {
                left = sender.getAbsoluteLeft() - 250;
            }

            int top = sender.getAbsoluteTop() + 10;
            editFields.setPopupPosition(left, top);

            if (sender == newButton) {
                editFields.init();
            } else {
                editFields.init(idHolderEditImages.findId(sender));
            }
            editFields.show();
        } else {
            doDelete(idHolderDeleteImages.findId(sender));
        }
    }

    private void doDelete(String username) {
        boolean cont = Window.confirm(messages.delete_user_question());
        if (!cont) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("action=delete");

        Util.addPostParam(sb, "username", username);

        ServerResponse callback = new ServerResponse() {

            public void serverResponse(JSONValue value) {
                JSONObject object = value.isObject();

                String result = Util.str(object.get("result"));

                if ("1".equals(result)) {
                    init();
                } else {
                    Window.alert(messages.bad_server_response());
                }
            }
        };

        AuthResponder.post(constants, messages, callback, sb, "registers/users.php");

    }

    public void init() {

        while (table.getRowCount() > 2) {
            table.removeRow(2);
        }

        idHolderEditImages.init();
        idHolderDeleteImages.init();
        objectPerUsername = new HashMap<String, JSONObject>();

        ServerResponse callback = new ServerResponse() {

            public void serverResponse(JSONValue value) {

                JSONArray array = value.isArray();
                int row = 2;
                for (int i = 0; i < array.size(); i++) {

                    JSONObject object = array.get(i).isObject();

                    String username = Util.str(object.get("username"));
                    String name = Util.str(object.get("person"));
                    String readOnlyAccess = Util.str(object.get("readonly"));
                    String reducedWriteAccess = Util.str(object.get("reducedwrite"));
                    objectPerUsername.put(username, object);

                    addRow(row++, username, name, readOnlyAccess, reducedWriteAccess);
                }
            }
        };

        AuthResponder.get(constants, messages, callback, "registers/users.php?action=all");
    }

    private void addRow(int row, String username, String name, String readOnlyAccess, String reducedwrite) {
        table.setHTML(row, 0, username);
        table.setHTML(row, 1, name);

        if ("1".equals(reducedwrite)) {
            table.setHTML(row, 2, elements.reduced_write_access());
        } else if ("1".equals(readOnlyAccess)) {
            table.setHTML(row, 2, elements.read_only_access());
        } else {
            table.setHTML(row, 2, elements.full_access());
        }

        table.getCellFormatter().setStyleName(row, 2, "desc");
        table.getCellFormatter().setStyleName(row, 1, "desc");

        Image editImage = ImageFactory.editImage("userEditView_editImage");
        editImage.addClickListener(me);
        idHolderEditImages.add(username, editImage);

        Image deleteImage = ImageFactory.deleteImage("userEditView.deleteImage");
        deleteImage.addClickListener(this);
        idHolderDeleteImages.add(username, deleteImage);

        table.setWidget(row, 3, editImage);
        table.setWidget(row, 4, deleteImage);

        String style = (((row + 2) % 6) < 3) ? "line2" : "line1";
        table.getRowFormatter().setStyleName(row, style);
    }

    class UserEditFields extends DialogBox implements ClickListener {

        private TextBoxWithErrorText userBox;

        private Button saveButton;

        private Button cancelButton;

        private HTML mainErrorLabel;

        private TextBoxWithErrorText passwordBox;

        private FlexTable edittable;

        private TextBoxWithErrorText personBox;

        private ListBoxWithErrorText accessList;

        UserEditFields() {
            edittable = new FlexTable();
            edittable.setStyleName("edittable");

            setText(elements.title_edit_new_user());
            userBox = new TextBoxWithErrorText("user");
            userBox.setMaxLength(12);
            userBox.setWidth("12em");

            passwordBox = new TextBoxWithErrorText("password");
            passwordBox.setWidth("12em");

            personBox = new TextBoxWithErrorText("name");
            personBox.setVisibleLength(40);

            accessList = new ListBoxWithErrorText("access");
            accessList.getListbox().setVisibleItemCount(1);
            accessList.getListbox().addItem(elements.full_access());
            accessList.getListbox().addItem(elements.reduced_write_access());
            accessList.getListbox().addItem(elements.read_only_access());

            edittable.setText(0, 0, elements.user());
            edittable.setWidget(0, 1, userBox);
            edittable.setText(1, 0, elements.password());
            edittable.setWidget(1, 1, passwordBox);
            edittable.setText(2, 0, elements.name());
            edittable.setWidget(2, 1, personBox);

            edittable.setText(3, 0, elements.read_only_access());
            edittable.setWidget(3, 1, accessList);

            DockPanel dp = new DockPanel();
            dp.add(edittable, DockPanel.NORTH);

            saveButton = new NamedButton("userEditView_saveButton", elements.save());
            saveButton.addClickListener(this);
            cancelButton = new NamedButton("usertEditView_cancelButton", elements.cancel());
            cancelButton.addClickListener(this);

            mainErrorLabel = new HTML();
            mainErrorLabel.setStyleName("error");

            HorizontalPanel buttonPanel = new HorizontalPanel();
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            buttonPanel.add(mainErrorLabel);
            dp.add(buttonPanel, DockPanel.NORTH);
            setWidget(dp);
        }

        public void init(String username) {
            init();
            JSONObject object = objectPerUsername.get(username);

            userBox.setText(username);
            personBox.setText(Util.str(object.get("person")));
            userBox.setEnabled(false);
            boolean isReadOnly = "1".equals(Util.str(object.get("readonly")));
            boolean reducedWrite = "1".equals(Util.str(object.get("reducedwrite")));

            if (reducedWrite) {
                Util.setIndexByValue(accessList.getListbox(), elements.reduced_write_access());
            } else if (isReadOnly) {
                Util.setIndexByValue(accessList.getListbox(), elements.read_only_access());
            } else {
                Util.setIndexByValue(accessList.getListbox(), elements.full_access());
            }
        }

        public void init() {
            passwordBox.setText("");
            userBox.setText("");
            userBox.setEnabled(true);
            personBox.setText("");
            mainErrorLabel.setText("");
        }

        public void onClick(Widget sender) {
            if (sender == cancelButton) {
                hide();
            } else if (sender == saveButton) {
                if (validateFields()) {
                    doSave();
                }
            }
        }

        private void doSave() {
            StringBuffer sb = new StringBuffer();
            sb.append("action=save");

            Util.addPostParam(sb, "username", userBox.getText());
            Util.addPostParam(sb, "password", passwordBox.getText());
            Util.addPostParam(sb, "person", personBox.getText());

            if (accessList.getText().equals(elements.reduced_write_access())) {
                Util.addPostParam(sb, "readonly", "1");
                Util.addPostParam(sb, "reducedwrite", "1");
            } else if (accessList.getText().equals(elements.read_only_access())) {
                Util.addPostParam(sb, "readonly", "1");
                Util.addPostParam(sb, "reducedwrite", "0");
            } else {
                Util.addPostParam(sb, "readonly", "0");
                Util.addPostParam(sb, "reducedwrite", "0");
            }

            ServerResponse callback = new ServerResponse() {

                public void serverResponse(JSONValue parse) {

                    if (parse == null || parse.isObject() == null) {
                        mainErrorLabel.setText(messages.save_failed_badly());
                    } else {
                        JSONObject object = parse.isObject();
                        String result = Util.str(object.get("result"));

                        if ("0".equals(result)) {
                            mainErrorLabel.setText(messages.save_failed());
                        } else {
                            mainErrorLabel.setText(messages.save_ok());
                            hide();
                            me.init();
                        }
                    }

                    Util.timedMessage(mainErrorLabel, "", 5);
                }
            };

            AuthResponder.post(constants, messages, callback, sb, "registers/users.php");

        }

        private boolean validateFields() {
            MasterValidator mv = new MasterValidator();

            Widget[] widgets = new Widget[] { userBox, personBox };
            mv.mandatory(messages.required_field(), widgets);

            if (userBox.isEnabled()) {
                mv.mandatory(messages.required_field(), new Widget[] { passwordBox });
            }

            return mv.validateStatus();
        }

    }
}

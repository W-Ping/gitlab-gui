package forms.table;

import config.CommonConstants;
import forms.panel.BasePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author lwp
 * @date 2022-09-30
 */
public class TableLabel extends JLabel {
    private String id;
    private TableRow tableRow;
    private BasePanel bindPanel;

    public TableLabel(String id, String text, BasePanel bindPanel, Color color) {
        super(text);
        this.id = id;
        this.bindPanel = bindPanel;
        this.setForeground(color);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setFont(CommonConstants.DEFAULT_FONT);
    }

    public String getId() {
        return id;
    }

    public TableRow getTableRow() {
        return tableRow;
    }

    public BasePanel getBindPanel() {
        return bindPanel;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTableRow(TableRow tableRow) {
        this.tableRow = tableRow;
    }

    public void setBindPanel(BasePanel bindPanel) {
        this.bindPanel = bindPanel;
    }
}

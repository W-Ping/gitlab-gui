package forms.table.button;

import forms.panel.BasePanel;
import forms.table.TableRow;

import javax.swing.*;
import java.awt.*;

/**
 * @author liu_wp
 * @date 2020/11/6
 * @see
 */
public class TableCustomButton extends JButton {
    private String id;
    private TableRow tableRow;
    private BasePanel bindPanel;

    public TableCustomButton(String id, String text, BasePanel bindPanel) {
        this(id, text, new Dimension(90, 25), bindPanel);
    }

    public TableCustomButton(String id, String text, Dimension dimension, BasePanel bindPanel) {
        super(text);
        this.id = id;
        this.bindPanel = bindPanel;
        setOpaque(false);
        if (dimension != null) {
            setPreferredSize(dimension);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public TableRow getTableRow() {
        return tableRow;
    }

    public void setTableRow(final TableRow tableRow) {
        this.tableRow = tableRow;
    }


    public BasePanel getBindPanel() {
        return bindPanel;
    }

    public void setBindPanel(final BasePanel bindPanel) {
        this.bindPanel = bindPanel;
    }
}

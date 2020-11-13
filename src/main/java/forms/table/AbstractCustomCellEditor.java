package forms.table;

import forms.panel.BasePanel;
import forms.panel.CommonJPanel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/5
 * @see
 */
public abstract class AbstractCustomCellEditor<T extends JComponent> extends AbstractCellEditor implements
        TableCellEditor {
    protected String cellName;
    protected CommonJPanel commonJPanel;
    protected List<T> components;
    protected BasePanel bindPanel;

    public AbstractCustomCellEditor(String cellName, List<T> components) {
        this.cellName = cellName;
        this.components = components;
        commonJPanel = new CommonJPanel();
        commonJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        for (int i = 0; i < components.size(); i++) {
            commonJPanel.add(components.get(i), componentLayout(), i);
        }
    }


    public String getCellName() {
        return cellName;
    }

    /**
     * @return
     */
    public String componentLayout() {
        return BorderLayout.CENTER;
    }

    public BasePanel getBindPanel() {
        return bindPanel;
    }

    public void setBindPanel(final BasePanel bindPanel) {
        this.bindPanel = bindPanel;
    }
}

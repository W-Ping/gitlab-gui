package forms.table;

import forms.panel.CommonJPanel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/5
 * @see
 */
public abstract class AbstractCustomCellRenderer<T extends JComponent> implements TableCellRenderer {
    protected String cellName;
    protected List<T> components;
    protected CommonJPanel commonJPanel;

    public AbstractCustomCellRenderer(String cellName, List<T> components) {
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
    

}

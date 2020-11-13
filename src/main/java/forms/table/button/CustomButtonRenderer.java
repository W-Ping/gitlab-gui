package forms.table.button;

import forms.table.AbstractCustomCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/4
 * @see
 */
public class CustomButtonRenderer extends AbstractCustomCellRenderer<TableCustomButton> {

    public CustomButtonRenderer(String cellName, List<TableCustomButton> tableCustomButtons) {
        super(cellName, tableCustomButtons);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        for (int i = 0; i < components.size(); i++) {
            TableCustomButton jButton = components.get(i);
            if (isSelected && hasFocus) {
                jButton.setForeground(table.getSelectionForeground());
                jButton.setBackground(table.getSelectionBackground());
            } else {
                jButton.setForeground(table.getForeground());
                jButton.setBackground(UIManager.getColor("UIManager"));
            }
        }
        return commonJPanel;
    }
}

package forms.table.button;

import forms.table.AbstractCustomCellRenderer;
import forms.table.TableLabel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/4
 * @see
 */
public class CustomButtonRenderer extends AbstractCustomCellRenderer<TableLabel> {

    public CustomButtonRenderer(String cellName, List<TableLabel> tableCustomButtons) {
        super(cellName, tableCustomButtons);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
//        for (TableLabel jButton : components) {
//            if (isSelected && hasFocus) {
//                jButton.setForeground(table.getSelectionForeground());
//                jButton.setBackground(table.getSelectionBackground());
//            } else {
//                jButton.setForeground(jButton.getForeground());
//                jButton.setBackground(UIManager.getColor("UIManager"));
//            }
//        }
        return commonJPanel;
    }
}

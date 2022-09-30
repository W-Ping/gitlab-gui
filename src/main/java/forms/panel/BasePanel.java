package forms.panel;

import forms.table.CustomTable;
import forms.table.TableCell;
import forms.table.TableRow;
import forms.table.button.CustomButtonCellCellEditor;
import forms.table.button.CustomButtonRenderer;
import forms.table.model.CustomTableModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.models.Branch;
import utils.GitLabApiUtil;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/3
 * @see
 */
public class BasePanel extends JPanel {
    protected Dimension dimension;

    public CommonJPanel createJTextFieldPanel(String label, String text) {
        CommonJPanel jPanel = new CommonJPanel();
        int i = 0;
        if (StringUtils.isNotBlank(label)) {
            jPanel.add(new JLabel(label), i);
            i++;
        }
        JTextField jTextField = new JTextField();
        jTextField.setToolTipText(text);
        jTextField.setPreferredSize(new Dimension(200, 30));
        jPanel.add(jTextField, i);
        jPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        return jPanel;
    }

    /**
     * @param label
     * @param itemList
     * @param order
     * @return
     */
    public CommonJPanel createJComboBoxPanel(String label, List<Object> itemList, int order) {
        CommonJPanel jPanel = new CommonJPanel();
        jPanel.add(new JLabel(label), 0);
        JComboBox jComboBox = new JComboBox();
        jComboBox.setEditable(true);
        if (CollectionUtils.isNotEmpty(itemList)) {
            for (final Object o : itemList) {
                jComboBox.addItem(o);
            }
        }
        jPanel.add(jComboBox, 1);
        jPanel.setBackground(Color.YELLOW.brighter());
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        return jPanel;
    }

    public CommonJPanel createJButtonPanel(JButton... jButtons) {
        CommonJPanel jPanel = new CommonJPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        for (int i = 0; i < jButtons.length; i++) {
            jPanel.add(jButtons[i], i);
        }
        return jPanel;
    }

    /**
     * @param tableCells
     * @param data
     * @param customButtonRenderers
     * @param customButtonCellEditors
     * @param panel
     * @param dimension
     * @return
     */
    public CommonJPanel createTablePanel(List<TableCell> tableCells, List<List<Object>> data, List<CustomButtonRenderer> customButtonRenderers, List<CustomButtonCellCellEditor> customButtonCellEditors, RightPanel panel, Dimension dimension) {
        CommonJPanel jPanel = new CommonJPanel();
        CustomTable<TableRow> customTable = new CustomTable(tableCells, data, tableSize(dimension, 0.9));
        customTable.setCustomCellRenderers(customButtonRenderers);
        customTable.setCustomCellEditors(customButtonCellEditors);
        customTable.setBindPanel(panel);
        RowSorter<CustomTableModel> sorter = new TableRowSorter<>((CustomTableModel) customTable.getModel());
        customTable.setRowSorter(sorter);
        JScrollPane scroll = new JScrollPane(customTable);
        jPanel.add(scroll, BorderLayout.CENTER, 0);
        return jPanel;
    }

    public Dimension tableSize(Dimension dimension, double scale) {
        Dimension tabSize = new Dimension((int) Math.ceil(dimension.getWidth() * scale), (int) Math.ceil(dimension.getHeight() * 0.9));
        return tabSize;
    }

    /**
     * @param projectId
     */
    public void setBranchComboBoxItemsWithApi(String projectId) {
        List<Branch> branches = GitLabApiUtil.getBranchByProjectId(projectId);
        setBranchComboBoxItems(branches);
    }

    /**
     * @param items
     */
    protected void setBranchComboBoxItems(Object... items) {
    }

    /**
     * @param branches
     */
    public void setBranchComboBoxItems(List<Branch> branches) {
        if (CollectionUtils.isNotEmpty(branches)) {
            Object[] bArr = new Object[branches.size()];
            for (int i = 0; i < branches.size(); i++) {
                Branch branch = branches.get(i);
                bArr[i] = branch.getName();
            }
            setBranchComboBoxItems(bArr);
        }
    }

    /**
     * @param jComponents
     * @return
     */
    public Box createVerticalBox(JComponent... jComponents) {
        Box vBox = Box.createVerticalBox();
        for (int i = 0; i < jComponents.length; i++) {
            vBox.add(jComponents[i], i);
        }
        return vBox;
    }

    public Box createHorizontalBox(JComponent... jComponents) {
        Box vBox = Box.createHorizontalBox();
        for (int i = 0; i < jComponents.length; i++) {
            vBox.add(jComponents[i], i);
        }
        return vBox;
    }

    public CustomTable getCommonTable(final CommonJPanel tablePanel) {
        JScrollPane scrollPane = (JScrollPane) tablePanel.getComponent(0);
        return (CustomTable) scrollPane.getViewport().getView();
    }


    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(final Dimension dimension) {
        this.dimension = dimension;
    }


}

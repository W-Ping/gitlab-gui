package forms.table.button;

import forms.panel.CreateBranchPanel;
import forms.panel.DeleteBranchPanel;
import forms.panel.RightPanel;
import forms.table.AbstractCustomCellEditor;
import forms.table.CustomTable;
import forms.table.TableLabel;
import forms.table.TableRow;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.models.Branch;
import utils.GitLabApiUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

/**
 * @author liu_wp
 * @date 2020/11/4
 * @see
 */
public class CustomButtonCellCellEditor extends AbstractCustomCellEditor<TableLabel> {

    private TableRow tableRow;

    /**
     * @param cellName
     * @param tableCustomButtons
     */
    public CustomButtonCellCellEditor(String cellName, List<TableLabel> tableCustomButtons) {
        super(cellName, tableCustomButtons);
        bindClick();
    }

    protected void bindClick() {
        for (TableLabel tableLabel : components) {
            tableLabel.setTableRow(tableRow);
            RightPanel showPanel = (RightPanel) (bindPanel = tableLabel.getBindPanel());
            if ("deleteHotfix".equals(tableLabel.getId())) {
                tableLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        List<Branch> branches = GitLabApiUtil.getBranchByProjectId(tableRow.getId().toString());
                        if (!extracted(branches)) return;
                        DeleteBranchPanel deleteBranchPanel = showPanel.getDeleteBranchPanel();
                        deleteBranchPanel.setBranchComboBoxItems(branches);
                        deleteBranchPanel.setTableRow(tableRow);
                        deleteBranchPanel.setTitle(tableRow.getName());
                        deleteBranchPanel.setDesc(Optional.ofNullable(tableRow.getDescription()).orElse(""));
                        deleteBranchPanel.setVisible(true);
                        showRightPanel(showPanel);
                        fireEditingStopped();
                    }
                });
            } else if ("createHotfix".equals(tableLabel.getId())) {
                tableLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        List<Branch> branches = GitLabApiUtil.getBranchByProjectId(tableRow.getId().toString());
                        if (!extracted(branches)) return;
                        CreateBranchPanel createBranchPanel = showPanel.getCreateBranchPanel();
                        createBranchPanel.setBranchComboBoxItems(branches);
                        createBranchPanel.setTitle(tableRow.getName());
                        createBranchPanel.setDesc(Optional.ofNullable(tableRow.getDescription()).orElse(""));
                        createBranchPanel.setHotFixText(null);
                        createBranchPanel.setTableRow(tableRow);
                        showRightPanel(showPanel);
                        fireEditingStopped();
                    }
                });
            }

        }
    }

    private boolean extracted(List<Branch> branches) {
        if (StringUtils.isBlank(tableRow.getDefaultBranch())) {
            JOptionPane.showMessageDialog(null, "【" + tableRow.getName() + "】主分支不存在！", "错误 ", 0);
            return false;
        }
        if (CollectionUtils.isEmpty(branches)) {
            JOptionPane.showMessageDialog(null, "【" + tableRow.getName() + "】项目分支不存在！", "错误 ", 0);
            return false;
        }
        return true;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        CustomTable<TableRow> table1 = (CustomTable<TableRow>) table;
        List<TableRow> TableRows = table1.getTableRows();
        tableRow = TableRows.get(row);
        return commonJPanel;
    }

    @Override
    public Object getCellEditorValue() {
        return tableRow;
    }

    /**
     * @param showPanel
     */
    private void showRightPanel(RightPanel showPanel) {
        if (!showPanel.isVisible()) {
            JFrame frame = showPanel.getFrame();
            Dimension dimension = showPanel.getDimension();
            int width = frame.getWidth();
            int sumWidth = (int) dimension.getWidth() + width;
            System.out.println("sumWidth:" + sumWidth + "height：" + frame.getHeight());
            frame.setSize(new Dimension(sumWidth, frame.getHeight()));
            showPanel.setVisible(true);
        }
    }
}

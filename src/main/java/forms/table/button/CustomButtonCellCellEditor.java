package forms.table.button;

import forms.panel.CreateBranchPanel;
import forms.panel.DeleteBranchPanel;
import forms.panel.RightPanel;
import forms.table.AbstractCustomCellEditor;
import forms.table.CustomTable;
import forms.table.TableRow;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.models.Branch;
import utils.GitLabApiUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

/**
 * @author liu_wp
 * @date 2020/11/4
 * @see
 */
public class CustomButtonCellCellEditor extends AbstractCustomCellEditor<TableCustomButton> {

    private TableRow tableRow;

    /**
     * @param cellName
     * @param buttonText
     */
    public CustomButtonCellCellEditor(String cellName, List<TableCustomButton> tableCustomButtons) {
        super(cellName, tableCustomButtons);
        bindClick();
    }

    protected void bindClick() {
        for (int i = 0; i < components.size(); i++) {
            TableCustomButton tableCustomButton = components.get(i);
            tableCustomButton.setTableRow(tableRow);
            RightPanel showPanel = (RightPanel) (bindPanel = tableCustomButton.getBindPanel());
            if ("deleteHotfix".equals(tableCustomButton.getId())) {
                tableCustomButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        if (StringUtils.isBlank(tableRow.getDefaultBranch())) {
                            JOptionPane.showMessageDialog(null, "【" + tableRow.getName() + "】主分支不存在！", "错误 ", 0);
                            return;
                        }
                        DeleteBranchPanel deleteBranchPanel = showPanel.getDeleteBranchPanel();
                        deleteBranchPanel.setBranchComboBoxItemsWithApi(tableRow.getId().toString());
                        deleteBranchPanel.setTableRow(tableRow);
                        deleteBranchPanel.setTitle(tableRow.getName());
                        deleteBranchPanel.setDesc(Optional.ofNullable(tableRow.getDescription()).orElse("").toString());
                        deleteBranchPanel.setVisible(true);
                        showRightPanel(showPanel);
                        fireEditingStopped();
                    }
                });
            } else if ("createHotfix".equals(tableCustomButton.getId())) {
                tableCustomButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        if (StringUtils.isBlank(tableRow.getDefaultBranch())) {
                            JOptionPane.showMessageDialog(null, "【" + tableRow.getName() + "】主分支不存在！", "错误 ", 0);
                            return;
                        }
                        List<Branch> branches = GitLabApiUtil.getBranchByProjectId(tableRow.getId().toString());
                        if (CollectionUtils.isNotEmpty(branches)) {
                            String hotfixBc = null;
                            Object[] bArr = new Object[branches.size()];
                            for (int i = 0; i < branches.size(); i++) {
                                Branch branch = branches.get(i);
                                bArr[i] = branch.getName();
                                if (branch.getName().toLowerCase().startsWith("hotfix")) {
                                    hotfixBc = branch.getName();
                                }
                            }
                            CreateBranchPanel createBranchPanel = showPanel.getCreateBranchPanel();
                            createBranchPanel.setBranchComboBoxItems(bArr);
                            createBranchPanel.setTitle(tableRow.getName());
                            createBranchPanel.setDesc(Optional.ofNullable(tableRow.getDescription()).orElse("").toString());
                            createBranchPanel.setHotFixText(hotfixBc);
                            createBranchPanel.setTableRow(tableRow);
                        }
                        showRightPanel(showPanel);
                        fireEditingStopped();
                    }
                });
            }

        }
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

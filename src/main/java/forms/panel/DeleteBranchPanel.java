package forms.panel;

import forms.table.TableRow;
import org.apache.commons.collections4.CollectionUtils;
import org.gitlab4j.api.models.Branch;
import utils.GitLabApiUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liu_wp
 * @date 2020/11/6
 * @see
 */
public class DeleteBranchPanel extends BasePanel {
    protected JComboBox branchComboBox;
    private JButton confirmBtn;
    private JButton closeBtn;
    private JLabel titleLabel;
    private JLabel descLabel;
    private TableRow tableRow;
    private List<String> unAllowDelete = Stream.of("main", "master", "dev").collect(Collectors.toList());

    public DeleteBranchPanel(BasePanel bindPanel) {
        DeleteBranchPanel panel = this;
        Box box1 = Box.createVerticalBox();
        box1.add(titleLabel = new JLabel("删除分支"));
        box1.add(descLabel = new JLabel());
        CommonJPanel j1 = new CommonJPanel(box1);
        j1.setLayout(new FlowLayout(FlowLayout.LEFT));
        CommonJPanel j2 = new CommonJPanel(new JLabel("选择分支："), branchComboBox = new JComboBox());
        branchComboBox.setPreferredSize(new Dimension(150, 30));
        CommonJPanel j3 = new CommonJPanel(FlowLayout.RIGHT, closeBtn = new JButton("关闭"), confirmBtn = new JButton("确认"));
        confirmBtn.addActionListener(e -> {
            if (branchComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "请选择删除分支！", "错误 ", 0);
                return;
            }
            if (unAllowDelete.contains(branchComboBox.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(null, "【" + branchComboBox.getSelectedItem().toString() + "】不允许删除！", "错误 ", 0);
                return;
            }
            if (tableRow.getId() == null) {
                JOptionPane.showMessageDialog(null, "项目ID为空！", "错误 ", 0);
                return;
            }
            String selectValue = branchComboBox.getSelectedItem().toString();
            int res = JOptionPane.showConfirmDialog(null,
                    "确认删除?【" + selectValue + "】", "确认",
                    JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                if (GitLabApiUtil.deleteBranch(tableRow.getId().toString(), branchComboBox.getSelectedItem().toString())) {
                    setBranchComboBoxItemsWithApi(tableRow.getId().toString());
                    JOptionPane.showMessageDialog(null, "【" + selectValue + "】删除成功！", "成功", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "【" + selectValue + "】删除失败！", "错误 ", 0);
                }
            }
        });
        closeBtn.addActionListener(e -> {
            panel.setVisible(false);
            ((RightPanel) bindPanel).hideRightPanel();
        });
        Box box = Box.createVerticalBox();
        box.add(j1);
        box.add(j2);
        box.add(j3);
        panel.add(box);
        setVisible(false);
        panel.setPreferredSize(new Dimension(280, 200));
        Border lineBorder = BorderFactory.createLineBorder(Color.red, 2);
        panel.setBorder(BorderFactory.createTitledBorder(lineBorder, "删除分支"));
    }

    public void setTitle(String title) {
        titleLabel.setText("项目名称【" + title + "】");
    }

    public void setDesc(String desc) {
        if (desc.length() > 15) {
            desc = desc.substring(0, 15) + "...";
        }
        descLabel.setText(desc);
    }

    @Override
    public void setBranchComboBoxItems(Object... items) {
        branchComboBox.removeAllItems();
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                branchComboBox.addItem(items[i]);
            }
        }
        this.setVisible(true);
    }

    /**
     * @param projectId
     */
    public void setBranchComboBoxItemsWithApi(String projectId) {
        List<Branch> branches = GitLabApiUtil.getBranchByProjectId(projectId);
        if (CollectionUtils.isNotEmpty(branches)) {
            Object[] bArr = new Object[branches.size()];
            for (int i = 0; i < branches.size(); i++) {
                Branch branch = branches.get(i);
                bArr[i] = branch.getName();
            }
            setBranchComboBoxItems(bArr);
        }
    }


    public JComboBox getBranchComboBox() {
        return branchComboBox;
    }

    public void setBranchComboBox(final JComboBox branchComboBox) {
        this.branchComboBox = branchComboBox;
    }

    public JButton getConfirmBtn() {
        return confirmBtn;
    }

    public void setConfirmBtn(final JButton confirmBtn) {
        this.confirmBtn = confirmBtn;
    }

    public JButton getCloseBtn() {
        return closeBtn;
    }

    public void setCloseBtn(final JButton closeBtn) {
        this.closeBtn = closeBtn;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(final JLabel titleLabel) {
        this.titleLabel = titleLabel;
    }

    public JLabel getDescLabel() {
        return descLabel;
    }

    public void setDescLabel(final JLabel descLabel) {
        this.descLabel = descLabel;
    }

    public TableRow getTableRow() {
        return tableRow;
    }

    public void setTableRow(final TableRow tableRow) {
        this.tableRow = tableRow;
    }
}

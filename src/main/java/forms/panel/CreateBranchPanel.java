package forms.panel;

import forms.table.TableRow;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.models.Branch;
import utils.DateUtil;
import utils.GitLabApiUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/6
 * @see
 */
public class CreateBranchPanel extends BasePanel {
    private JComboBox branchComboBox;
    private JTextField hotfixInput;
    private JButton confirmBtn;
    private JButton closeBtn;
    private JLabel titleLabel;
    private JLabel descLabel;
    private TableRow tableRow;
    private BasePanel bindPanel;

    public CreateBranchPanel(BasePanel bindPanel) {
        this.bindPanel = bindPanel;
        CreateBranchPanel panel = this;
        CommonJPanel j1 = new CommonJPanel();
        titleLabel = new JLabel("创建分支");
        descLabel = new JLabel();
        Box box1 = Box.createVerticalBox();
        box1.add(titleLabel);
        box1.add(descLabel);
        j1.add(box1);
        CommonJPanel j2 = new CommonJPanel();
        JLabel l2 = new JLabel("来源分支：");
        branchComboBox = new JComboBox();
        branchComboBox.setPreferredSize(new Dimension(150, 30));
        j2.add(l2);
        j2.add(branchComboBox);
        CommonJPanel j3 = new CommonJPanel();
        JLabel l3 = new JLabel("新建分支：");
        hotfixInput = new JTextField();
        hotfixInput.setPreferredSize(new Dimension(150, 30));
        j3.add(l3);
        j3.add(hotfixInput);
        confirmBtn = new JButton("确定");
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String newBranchName = hotfixInput.getText();
                if (StringUtils.isBlank(newBranchName) || StringUtils.isBlank(newBranchName.trim())) {
                    JOptionPane.showMessageDialog(null, "来源分支不能为空!", "错误 ", 0);
                    return;
                }
                if (branchComboBox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "新建分支不能为空!", "错误 ", 0);
                    return;
                }
                String refBranch = branchComboBox.getSelectedItem().toString();
                String projectId = tableRow.getId().toString();
                int itemCount = branchComboBox.getItemCount();
                for (int i = 0; i < itemCount; i++) {
                    Object itemAt = branchComboBox.getItemAt(i);
                    if (itemAt.toString().equals(newBranchName.trim())) {
                        JOptionPane.showMessageDialog(null, "【" + newBranchName.trim() + "】分支已存在", "错误 ", 0);
                        return;
                    }
                }
                int res = JOptionPane.showConfirmDialog(null,
                        "确认创建Hotfix?【" + newBranchName.trim() + "】", "确认",
                        JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    Branch newBranch = GitLabApiUtil.createNewBranch(projectId, newBranchName.trim(), refBranch);
                    if (newBranch != null) {
                        String name = newBranch.getName();
                        JOptionPane.showMessageDialog(null, "【" + name + "】创建成功！", "成功", JOptionPane.PLAIN_MESSAGE);
                        setBranchComboBoxItemsWithApi(tableRow.getId().toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "【" + newBranchName + "】创建失败！", "错误 ", 0);
                    }
                }
            }
        });
        closeBtn = new JButton("关闭");
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                panel.setVisible(false);
                ((RightPanel)bindPanel).hideRightPanel();
            }
        });
        CommonJPanel j4 = new CommonJPanel();
        j4.add(closeBtn);
        j4.add(confirmBtn);
        j4.setLayout(new FlowLayout(FlowLayout.RIGHT));
        Box box = Box.createVerticalBox();
        box.add(j1);
        box.add(j2);
        box.add(j3);
        box.add(j4);
        panel.add(box);
        panel.setVisible(false);
        panel.setPreferredSize(new Dimension(280, 200));
        Border lineBorder = BorderFactory.createLineBorder(Color.gray, 2);
        panel.setBorder(BorderFactory.createTitledBorder(lineBorder, "创建分支"));
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

    /**
     * @param items
     */
    public void setBranchComboBoxItems(Object... items) {
        if (branchComboBox != null) {
            branchComboBox.removeAllItems();
            if (items != null) {
                for (int i = 0; i < items.length; i++) {
                    branchComboBox.addItem(items[i]);
                }
            }
        }
        this.setVisible(true);
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

    /**
     * @param hotFixText
     */
    public void setHotFixText(String hotFixText) {
        hotFixText = hotFixText != null ? hotFixText.trim() : ("hotfix_" + DateUtil.getNowDate(null));
        hotfixInput.setText(hotFixText);
    }

    /**
     *
     */


    public JComboBox getBranchComboBox() {
        return branchComboBox;
    }

    public void setBranchComboBox(final JComboBox branchComboBox) {
        this.branchComboBox = branchComboBox;
    }

    public JTextField getHotfixInput() {
        return hotfixInput;
    }

    public void setHotfixInput(final JTextField hotfixInput) {
        this.hotfixInput = hotfixInput;
    }

    public TableRow getTableRow() {
        return tableRow;
    }

    public void setTableRow(final TableRow tableRow) {
        this.tableRow = tableRow;
    }

    public JButton getConfirmBtn() {
        return confirmBtn;
    }

    public void setConfirmBtn(final JButton confirmBtn) {
        this.confirmBtn = confirmBtn;
    }

}

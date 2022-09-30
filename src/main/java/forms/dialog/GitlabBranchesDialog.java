package forms.dialog;

import config.CommonConstants;
import forms.panel.CommonJPanel;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.GitlabDetailInfo;
import utils.DateUtil;
import utils.SystemUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/13
 * @see
 */
public class GitlabBranchesDialog extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(GitlabBranchesDialog.class);

    /**
     * @param parentComponent
     * @param gitlabDetailInfo
     */
    public GitlabBranchesDialog(Component parentComponent, GitlabDetailInfo gitlabDetailInfo) {
        super((Frame) SwingUtilities.windowForComponent(parentComponent), gitlabDetailInfo.getProject().getName() + " 分支详情", true);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(CommonConstants.SYS_ICON)));
        Box box = Box.createVerticalBox();
        Project project = gitlabDetailInfo.getProject();
        List<Branch> branches = gitlabDetailInfo.getBranches();
        for (int i = 0; i < branches.size(); i++) {
            Branch branch = branches.get(i);
            Commit commit = branch.getCommit();
            CommonJPanel cj0 = new CommonJPanel();
            JLabel lab0 = new JLabel("项目名称：");
            JLabel lab0V = new JLabel(project.getName());
            cj0.add(lab0);
            cj0.add(lab0V);
            CommonJPanel cj1 = new CommonJPanel();
            JLabel lab1 = new JLabel("分支名称：");
            JLabel lab1V = new JLabel(branch.getName());
            cj1.add(lab1);
            cj1.add(lab1V);
            CommonJPanel cj2 = new CommonJPanel();
            JLabel lab2 = new JLabel("最后Commit ID：");
            JLabel lab2V = new JLabel(commit.getId());
            JLabel lab21V = new JLabel(" 复制");
            lab21V.setForeground(Color.BLUE);
            lab21V.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            lab21V.setFont(new Font("Helvetica", Font.PLAIN, 12));
            lab21V.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //双击选中
                    if (e.getClickCount() == 1) {
                        String clipboardStr = lab2V.getText();
                        SystemUtil.setClipboardString(clipboardStr);
                    }
                }

                @Override
                public void mouseEntered(final MouseEvent e) {
                    lab21V.setOpaque(true);
                    lab21V.setFont(new Font("Helvetica", Font.BOLD, 12));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    lab21V.setOpaque(true);
                    lab21V.setFont(CommonConstants.DEFAULT_FONT);
                }
            });
            cj2.add(lab2);
            cj2.add(lab2V);
            cj2.add(lab21V);
            CommonJPanel cj3 = new CommonJPanel();
            JLabel lab3 = new JLabel("最后提交人：");
            JLabel lab3V = new JLabel(commit.getAuthorName());
            cj3.add(lab3);
            cj3.add(lab3V);
            CommonJPanel cj4 = new CommonJPanel();
            JLabel lab4 = new JLabel("最后提交时间：");
            JLabel lab4V = new JLabel(DateUtil.dateFormatter(commit.getCommittedDate(), null));
            cj4.add(lab4);
            cj4.add(lab4V);
            CommonJPanel cj5 = new CommonJPanel();
            CommonJPanel cj51 = new CommonJPanel();
            JLabel lab5 = new JLabel("提交说明：");
            JTextArea lab5V = new JTextArea(3, 30);
            lab5V.setText(commit.getMessage());
//            lab5V.setLayout(new FlowLayout(FlowLayout.CENTER));
            lab5V.setLineWrap(true);
            lab5V.setEditable(false);
            lab5V.setBackground(lab5.getBackground());
            JScrollPane scroll = new JScrollPane(lab5V);
            cj51.add(scroll);
            cj5.add(lab5);
            cj5.add(cj51);
            Box box1 = Box.createVerticalBox();
            box1.add(cj0);
            box1.add(cj1);
            box1.add(cj2);
            box1.add(cj3);
            box1.add(cj4);
            box1.add(cj5);
            CommonJPanel commonJPanel = new CommonJPanel(new FlowLayout(FlowLayout.LEFT));
            commonJPanel.add(box1);
            box.add(commonJPanel);
        }
        CommonJPanel commonJPanel = new CommonJPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeBtn = new JButton("关闭");
        closeBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                close();
            }
        });
        commonJPanel.add(closeBtn);
        box.add(commonJPanel);
        setContentPane(box);
        setMinimumSize(new Dimension(450, 320));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void close() {
        this.dispose();
    }

}

package forms.dialog;

import forms.panel.CommonJPanel;
import org.gitlab4j.api.models.Project;
import pojo.GitlabDetailInfo;
import utils.DateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author liu_wp
 * @date 2020/11/13
 * @see
 */
public class GitlabDetailDialog extends JDialog {

    public GitlabDetailDialog(Component parentComponent, GitlabDetailInfo gitlabDetailInfo) {
        super(SwingUtilities.windowForComponent(parentComponent), gitlabDetailInfo.getProject().getName() + " 详情");
        Project project = gitlabDetailInfo.getProject();
        this.setResizable(false);
        Font helvetica = new Font("Helvetica", Font.BOLD, 12);
        CommonJPanel cj1 = new CommonJPanel();
        JLabel lab1 = new JLabel("项目名称：");
        JLabel lab1V = new JLabel(project.getName());
        lab1V.setFont(helvetica);
        cj1.add(lab1);
        cj1.add(lab1V);
        CommonJPanel cj2 = new CommonJPanel();
        JLabel lab2 = new JLabel("代码地址（http）：");
        JTextField lab2V = new JTextField(project.getHttpUrlToRepo());
        lab2V.setBackground(lab2.getBackground());
        lab2V.setEditable(false);
        lab2V.setFont(helvetica);
        lab2V.setBorder(null);
        cj2.add(lab2);
        cj2.add(lab2V);
        CommonJPanel cj3 = new CommonJPanel();
        JLabel lab3 = new JLabel("代码地址（ssh）：");
        JTextField lab3V = new JTextField(project.getSshUrlToRepo());
        lab3V.setBackground(lab3.getBackground());
        lab3V.setEditable(false);
        lab3V.setFont(helvetica);
        lab3V.setBorder(null);
        cj3.add(lab3);
        cj3.add(lab3V);
        CommonJPanel cj4 = new CommonJPanel();
        JLabel lab4 = new JLabel("默认分支：");
        JLabel lab4V = new JLabel(project.getDefaultBranch());
        lab4V.setFont(helvetica);
        cj4.add(lab4);
        cj4.add(lab4V);
        CommonJPanel cj5 = new CommonJPanel();
        JLabel lab5 = new JLabel("所属分组：");
        JLabel lab5V = new JLabel(project.getNamespace().getFullPath());
        lab5V.setFont(helvetica);
        cj5.add(lab5);
        cj5.add(lab5V);
        CommonJPanel cj6 = new CommonJPanel();
        JLabel lab6 = new JLabel("创建时间：");
        JLabel lab6V = new JLabel(DateUtil.dateFormatter(project.getCreatedAt(), null));
        lab6V.setFont(helvetica);
        cj6.add(lab6);
        cj6.add(lab6V);
        CommonJPanel cj7 = new CommonJPanel();
        JLabel lab7 = new JLabel("描述：");
        JLabel lab7V = new JLabel(project.getDescription());
        lab7V.setFont(helvetica);
        cj7.add(lab7);
        cj7.add(lab7V);
        Box box1 = Box.createVerticalBox();
        box1.add(cj1);
        box1.add(cj2);
        box1.add(cj3);
        box1.add(cj4);
        box1.add(cj5);
        box1.add(cj6);
        box1.add(cj7);
        CommonJPanel closePanel = new CommonJPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeBtn = new JButton("关闭");
        closeBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                close();
            }
        });
        closePanel.add(closeBtn);
        CommonJPanel commonJPanel = new CommonJPanel(new FlowLayout(FlowLayout.CENTER));
        box1.add(closePanel);
        commonJPanel.add(box1);
        add(commonJPanel);
        setMinimumSize(new Dimension(450, 280));
        pack();
        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void close() {
        this.dispose();
    }
}

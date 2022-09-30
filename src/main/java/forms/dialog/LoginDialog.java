package forms.dialog;

import actions.LoginAction;
import config.CommonConstants;
import exception.GitLabGuiException;
import forms.panel.CommonJPanel;
import org.apache.commons.lang3.StringUtils;
import pojo.LoginInfo;
import utils.GitLabApiUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

/**
 * @author lwp
 * @create 2020/11/08
 */
public class LoginDialog extends JDialog {
    private JTextField loginName;
    private JTextField gitlabHost;
    private JPasswordField loginPwd;
    private JButton confirmBtn;
    private Component parentComponent;
    private JCheckBox jCheckBox;

    public LoginDialog(Component parentComponent) {
        super((Frame) SwingUtilities.windowForComponent(parentComponent), "Gitlab管理登录", true);
        this.parentComponent = parentComponent;
        this.setResizable(false);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(CommonConstants.SYS_ICON)));
        Font font = new Font("宋体", Font.BOLD, 13);
        Dimension labDim = new Dimension(90, 20);
        Dimension inputDim = new Dimension(230, 30);
        LoginInfo  loginInfo = GitLabApiUtil.getDefaultLogin();
        CommonJPanel j1 = new CommonJPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel l1 = new JLabel("用户：", JLabel.RIGHT);
        l1.setFont(font);
        l1.setPreferredSize(labDim);
        loginName = new JTextField(loginInfo.getLoginName());
        loginName.setPreferredSize(inputDim);
        j1.add(l1);
        j1.add(loginName);
        CommonJPanel j2 = new CommonJPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel l2 = new JLabel("密码：", JLabel.RIGHT);
        l2.setFont(font);
        l2.setPreferredSize(labDim);
        loginPwd = new JPasswordField(loginInfo.getLoginPwd());
        loginPwd.setPreferredSize(inputDim);
        j2.add(l2, BorderLayout.CENTER);
        j2.add(loginPwd, BorderLayout.CENTER);
        CommonJPanel j3 = new CommonJPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel l3 = new JLabel("Gitlab地址：", JLabel.RIGHT);
        l3.setPreferredSize(labDim);
        l3.setFont(font);
        gitlabHost = new JTextField(loginInfo.getGitlabHostUrl());
        gitlabHost.setPreferredSize(inputDim);
        j3.add(l3);
        j3.add(gitlabHost);
        j3.setVisible(false);
        CommonJPanel j4 = new CommonJPanel(new FlowLayout(FlowLayout.CENTER));
        jCheckBox = new JCheckBox("默认Gitlab地址", true);
        j4.add(jCheckBox);
        CommonJPanel j5 = new CommonJPanel(new FlowLayout(FlowLayout.CENTER));
        confirmBtn = new JButton("登 录");
        confirmBtn.setPreferredSize(new Dimension(120, 30));
        LoginAction loginAction = new LoginAction(this);
        confirmBtn.addActionListener(loginAction);
        confirmBtn.registerKeyboardAction(loginAction, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        j5.add(confirmBtn);
        Box box1 = Box.createVerticalBox();
        box1.add(j1);
        box1.add(j2);
        box1.add(j3);
        box1.add(j4);
        box1.add(j5);
        setMinimumSize(new Dimension(300, 220));
        CommonJPanel commonJPanel = new CommonJPanel(new BorderLayout(), box1);
        setContentPane(commonJPanel);
        jCheckBox.addActionListener(e -> selectCheckBox(j3));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void selectCheckBox(CommonJPanel j3) {
        if (!jCheckBox.isSelected()) {
            j3.setVisible(true);
        } else {
            j3.setVisible(false);
        }
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (JOptionPane.showConfirmDialog(this, "确认退出程序？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
            }
        } else {
            super.processWindowEvent(e);
        }
    }


    public LoginInfo getLoginInfo(boolean isCheck) {
        LoginInfo login = GitLabApiUtil.getDefaultLogin();
        String name = loginName.getText().trim();
        if (isCheck && StringUtils.isBlank(name)) {
            JOptionPane.showMessageDialog(this, "用户不能为空!", "错误 ", 0);
            throw new GitLabGuiException("用户不能为空");
        }
        char[] password = loginPwd.getPassword();
        if (isCheck && (password == null || password.length <= 0)) {
            JOptionPane.showMessageDialog(this, "用户密码不能为空!", "错误 ", 0);
            throw new GitLabGuiException("用户密码不能为空");
        }

        if (!jCheckBox.isSelected()) {
            if (isCheck && StringUtils.isBlank(gitlabHost.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Gitlab地址不能为空!", "错误 ", 0);
                throw new GitLabGuiException("Gitlab地址不能为空");
            }
            login.setGitlabHostUrl(gitlabHost.getText().trim());
        }
        login.setLoginName(name);
        login.setLoginPwd(new String(password));
        return login;
    }

    public JTextField getLoginName() {
        return loginName;
    }

    public JPasswordField getLoginPwd() {
        return loginPwd;
    }

    public JButton getConfirmBtn() {
        return confirmBtn;
    }

    public Component getParentComponent() {
        return parentComponent;
    }
}

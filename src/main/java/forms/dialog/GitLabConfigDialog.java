package forms.dialog;

import config.AccountActionType;
import config.CommonConstants;
import forms.panel.CommonJPanel;
import org.apache.commons.lang3.StringUtils;
import pojo.LoginInfo;
import pojo.ResponseResult;
import service.LoginService;
import utils.JSONUtil;
import utils.PropertiesUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author liu_wp
 * @date 2020/11/10
 * @see
 */
public class GitLabConfigDialog extends JDialog {
    private JButton confirmBtn;
    private JButton testBtn;
    private JButton closeBtn;
    private JTextField url;
    private JTextField user;
    private JTextField pwd;

    /**
     * @param parentComponent
     */
    public GitLabConfigDialog(Component parentComponent, LoginInfo loginInfo) {
        super((Frame) SwingUtilities.windowForComponent(parentComponent), "GitLab配置", true);
        CommonJPanel j1 = new CommonJPanel(new JLabel("GitLab地址："), url = new JTextField(loginInfo.getGitlabHostUrl()));
        url.setPreferredSize(new Dimension(200, 30));
        CommonJPanel j2 = new CommonJPanel(new JLabel("            用户："), user = new JTextField(loginInfo.getLoginName()));
        user.setPreferredSize(new Dimension(200, 30));
        CommonJPanel j3 = new CommonJPanel(new JLabel("            密码："), pwd = new JPasswordField(loginInfo.getLoginPwd()));
        pwd.setPreferredSize(new Dimension(200, 30));
        CommonJPanel j4 = new CommonJPanel(FlowLayout.RIGHT, closeBtn = new JButton("关闭"), testBtn = new JButton("测试连接"), confirmBtn = new JButton("确认并重启"));
        Box box = Box.createVerticalBox();
        box.add(j1);
        box.add(j2);
        box.add(j3);
        box.add(j4);
        this.setMinimumSize(new Dimension(300, 200));
        this.setContentPane(new CommonJPanel(new FlowLayout(FlowLayout.CENTER), box));
        this.setResizable(false);
        pack();
        GitLabConfigDialog configDialog = this;
        testBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StringUtils.isBlank(url.getText())) {
                    JOptionPane.showMessageDialog(configDialog, "GitLab地址不能为空", "错误", 0);
                    return;
                }
                if (StringUtils.isBlank(user.getText())) {
                    JOptionPane.showMessageDialog(configDialog, "用户不能为空", "错误", 0);
                    return;
                }
                if (StringUtils.isBlank(pwd.getText())) {
                    JOptionPane.showMessageDialog(configDialog, "密码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LoginInfo testLogin = new LoginInfo();
                testLogin.setGitlabHostUrl(url.getText().trim());
                testLogin.setLoginName(user.getText().trim());
                testLogin.setLoginPwd(pwd.getText().trim());
                System.out.println(JSONUtil.Object2JSON(testLogin));
                ResponseResult responseResult = LoginService.login(testLogin, false);
                if (!ResponseResult.isSuccess(responseResult)) {
                    JOptionPane.showMessageDialog(configDialog, "连接服务失败！", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(configDialog, "连接成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StringUtils.isBlank(url.getText())) {
                    JOptionPane.showMessageDialog(configDialog, "GitLab地址不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (StringUtils.isBlank(user.getText())) {
                    JOptionPane.showMessageDialog(configDialog, "用户不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (StringUtils.isBlank(pwd.getText())) {
                    JOptionPane.showMessageDialog(configDialog, "密码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LoginInfo newLogin = new LoginInfo();
                newLogin.setLoginName(user.getText().trim());
                newLogin.setLoginPwd(pwd.getText().trim());
                newLogin.setGitlabHostUrl(url.getText().trim());
                newLogin.setActionType(AccountActionType.CHANGE.getType());
                ResponseResult login = LoginService.login(newLogin, true);
                if (!ResponseResult.isSuccess(login)) {
                    JOptionPane.showMessageDialog(configDialog, "连接服务失败", "错误", 0);
                    return;
                }
                if (PropertiesUtil.setPropertyValue(CommonConstants.LOGIN_FILE, newLogin, true)) {
                    LoginService.restart();
                }
            }
        });
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                configDialog.dispose();
            }
        });
        this.setPreferredSize(new Dimension(400, 300));
        this.setLocationRelativeTo(null);
        setVisible(true);
    }


}

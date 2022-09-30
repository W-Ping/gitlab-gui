package forms.dialog;

import config.CommonConstants;
import forms.panel.CommonJPanel;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.models.User;
import utils.DateUtil;
import utils.SystemUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

/**
 * @author liu_wp
 * @date 2020/11/12
 * @see
 */
public class UserDialog extends JDialog {
    private Component parentComponent;
    private User user;

    public UserDialog(Component parentComponent, User user) {
        super((Frame) SwingUtilities.windowForComponent(parentComponent), "账号用户信息", true);
        this.parentComponent = parentComponent;
        this.user = user;
        this.setResizable(false);
        final String avatarUrl = user.getAvatarUrl();
        if (StringUtils.isNotBlank(avatarUrl)) {
            try {
                this.setIconImage(ImageIO.read(new URL(avatarUrl)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(CommonConstants.SYS_ICON)));
        }
        CommonJPanel cj1 = new CommonJPanel();
        JLabel lb1 = new JLabel("用户名称：");
        JLabel lb1V = new JLabel(user.getUsername());
        cj1.add(lb1);
        cj1.add(lb1V);
        CommonJPanel cj2 = new CommonJPanel();
        JLabel lb2 = new JLabel("用户邮箱：");
        JLabel lb2V = new JLabel(user.getEmail());
        cj2.add(lb2);
        cj2.add(lb2V);
        CommonJPanel cj3 = new CommonJPanel();
        JLabel lb3 = new JLabel(" web地址：");
        JLabel lb3V = new JLabel();
        lb3V.setText("<html><a href='" + user.getWebUrl() + "'>" + user.getWebUrl() + "</a></html>");
        lb3V.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lb3V.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                SystemUtil.browseWebUrl(user.getWebUrl());
            }
        });
        cj3.add(lb3);
        cj3.add(lb3V);
        CommonJPanel cj31 = new CommonJPanel();
        JLabel lb31 = new JLabel("创建分组权限：");
        JLabel lb31V = new JLabel(user.getCanCreateGroup() ? "允许" : "不允许");
        cj31.add(lb31);
        cj31.add(lb31V);
        CommonJPanel cj32 = new CommonJPanel();
        JLabel lb32 = new JLabel("创建项目权限：");
        JLabel lb32V = new JLabel(user.getCanCreateProject() ? "允许" : "不允许");
        cj32.add(lb32);
        cj32.add(lb32V);
        CommonJPanel cj4 = new CommonJPanel();
        JLabel lb4 = new JLabel("账号创建时间：");
        JLabel lb4V = new JLabel(DateUtil.dateFormatter(user.getCreatedAt(), "yyyy-MM-dd HH:mm:ss"));
        cj4.add(lb4);
        cj4.add(lb4V);
        CommonJPanel cj5 = new CommonJPanel();
        JLabel lb5 = new JLabel("上次登录时间：");
        JLabel lb5V = new JLabel(DateUtil.dateFormatter(user.getLastSignInAt(), "yyyy-MM-dd HH:mm:ss"));
        cj5.add(lb5);
        cj5.add(lb5V);
        CommonJPanel cj6 = new CommonJPanel();
        JLabel lb6 = new JLabel("当前登录时间：");
        JLabel lb6V = new JLabel(DateUtil.dateFormatter(user.getCurrentSignInAt(), "yyyy-MM-dd HH:mm:ss"));
        cj6.add(lb6);
        cj6.add(lb6V);
        CommonJPanel cj7 = new CommonJPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeBtn = new JButton("关闭");
        closeBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                close();
            }
        });
        cj7.add(closeBtn);
        Box box1 = Box.createVerticalBox();
        box1.add(cj1);
        box1.add(cj2);
        box1.add(cj3);
        box1.add(cj31);
        box1.add(cj32);
        box1.add(cj4);
        box1.add(cj5);
        box1.add(cj6);
        box1.add(cj7);
        CommonJPanel cj8 = new CommonJPanel(new FlowLayout(FlowLayout.CENTER));
        cj8.add(box1);
        add(cj8);
        setMinimumSize(new Dimension(300, 200));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void close() {
        this.dispose();
    }
}

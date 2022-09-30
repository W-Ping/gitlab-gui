package forms;

import config.CommonConstants;
import forms.dialog.GitlabBranchesDialog;
import forms.dialog.LoginDialog;
import forms.dialog.event.EventEnum;
import forms.panel.CommonJPanel;
import forms.panel.LeftPanel;
import forms.panel.RightPanel;
import org.gitlab4j.api.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.LoginInfo;
import pojo.ResponseResult;
import service.LoginService;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

/**
 * @author liu_wp
 * @date 2020/11/3
 * @see
 */
public class GitLabFrame extends JFrame {
    private static final Logger log = LoggerFactory.getLogger(GitlabBranchesDialog.class);

    public GitLabFrame(String title,String icon) {
        super(title);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(600, 500));
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(icon)));
    }

    public void init() {
        SQLiteUtil.init(false);
        LoginInfo loginInfo = GitLabApiUtil.getDefaultLogin();
        if (loginInfo.loginIsEffective()) {
            LoginService loginService = new LoginService(EventEnum.windowOpened, loginInfo);
            MessageDialogUtil.showModalDialog("【" + loginInfo.getLoginName() + "】登录中...", this, loginService);
            ResponseResult handleResult = loginService.getHandleResult();
            if (!ResponseResult.isSuccess(handleResult)) {
                LoginDialog loginDialog = MessageDialogUtil.createLoginDialog(this);
//                loginInfo = loginDialog.getLoginInfo(false);
            }
        } else {
            LoginDialog loginDialog = MessageDialogUtil.createLoginDialog(this);
//            loginInfo = loginDialog.getLoginInfo(false);
        }
        JMenuBar menuBar = new JMenuBar();
        JMenu jMenu1 = new JMenu("设置(S)");
        jMenu1.setMnemonic('S');
        JMenuItem configItem = new JMenuItem("配置GitLab");
        configItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                config(e);
            }
        });
        jMenu1.add(configItem);
        JMenu jMenu2 = new JMenu("账号管理(U)");
        jMenu2.setMnemonic('U');
        JMenuItem jm1 = new JMenuItem("用户信息");
        jm1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                user(e);
            }
        });
        JMenuItem jm2 = new JMenuItem("注销账号");
        jm2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                logout(e);
            }
        });
        jMenu2.add(jm1);
        jMenu2.addSeparator();
        jMenu2.add(jm2);
        menuBar.add(jMenu1);
        menuBar.add(jMenu2);
        setJMenuBar(menuBar);
        setResizable(false);
        RightPanel rightPanel = new RightPanel(this);
        LeftPanel leftPanel = new LeftPanel(this, rightPanel);
        Box vBox = Box.createHorizontalBox();
        vBox.add(leftPanel);
        vBox.add(rightPanel);
        Box vBox2 = Box.createVerticalBox();
        vBox2.add(vBox);
        CommonJPanel bottom = showBottomPanel();
        vBox2.add(bottom);
        this.setContentPane(vBox2);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (JOptionPane.showConfirmDialog(this, "确认退出程序？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                CommonConstants.GLOBE_LOGIN_INFO = null;
                System.exit(0);
//                setExtendedState(JFrame.ICONIFIED);
            }
        } else {
            super.processWindowEvent(e);
        }
    }

    public void logout(ActionEvent event) {
        ResponseResult responseResult = LoginService.logout(GitLabApiUtil.getDefaultLogin());
        if (ResponseResult.isSuccess(responseResult)) {
            LoginService.restart();
        }

    }

    public void config(ActionEvent event) {
        MessageDialogUtil.showGitLabConfigDialog(this, GitLabApiUtil.getDefaultLogin());
    }

    public void user(ActionEvent event) {
        User currentUser = GitLabApiUtil.getCurrentUser();
        log.info("currentUser:{}", JSONUtil.Object2JSON(currentUser));
        MessageDialogUtil.createUserDialog(this, currentUser);
    }

    /**
     * @return
     */
    private CommonJPanel showBottomPanel() {
        final LoginInfo loginInfo = GitLabApiUtil.getDefaultLogin();
//        Font helvetica = new Font("Helvetica", Font.PLAIN, 12);
        CommonJPanel bottom = new CommonJPanel();
        JLabel j1 = new JLabel("GitLab 地址：");
        j1.setFont(CommonConstants.DEFAULT_FONT);
        JLabel j2 = new JLabel();
        j2.setFont(CommonConstants.DEFAULT_FONT);
        String gitlabHostUrl = loginInfo.getGitlabHostUrl();
        j2.setText("<html><a href='" + gitlabHostUrl + "'>" + gitlabHostUrl + "</a></html>");
        j2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        j2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                SystemUtil.browseWebUrl(gitlabHostUrl);
            }
        });
        JLabel j3 = new JLabel("  用户：");
        j3.setFont(CommonConstants.DEFAULT_FONT);
        JLabel j4 = new JLabel(loginInfo.getLoginName());
        j4.setFont(CommonConstants.DEFAULT_FONT);
        bottom.add(j1);
        bottom.add(j2);
        bottom.add(j3);
        bottom.add(j4);
        return bottom;
    }
}

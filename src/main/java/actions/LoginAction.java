package actions;

import forms.dialog.LoginDialog;
import forms.dialog.event.EventEnum;
import pojo.LoginInfo;
import pojo.ResponseResult;
import service.LoginService;
import utils.MessageDialogUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author liu_wp
 * @date 2020/11/9
 * @see
 */
public class LoginAction extends AbstractAction {

    private LoginDialog loginDialog;

    public LoginAction(LoginDialog loginDialog) {
        this.loginDialog = loginDialog;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        LoginInfo loginInfo = loginDialog.getLoginInfo(true);
        if (loginInfo != null) {
            LoginService loginService = new LoginService(EventEnum.windowOpened, loginInfo);
            MessageDialogUtil.showModalDialog("【" + loginInfo.getLoginName() + "】登录中...", loginDialog, loginService);
            ResponseResult handleResult = loginService.getHandleResult();
            if (!ResponseResult.isSuccess(handleResult)) {
                final Object object = handleResult.getObject();
                if (object instanceof LoginInfo) {
                    loginInfo.setId(((LoginInfo) object).getId());
                }
                JOptionPane.showMessageDialog(loginDialog, handleResult.getMessage(), "错误 ", 0);
            } else {
                loginDialog.dispose();
            }
        }
    }
}

package service;

import config.AccountActionType;
import config.CommonConstants;
import forms.dialog.event.AbstractCustomMsgDialogEvent;
import forms.dialog.event.EventEnum;
import org.gitlab4j.api.GitLabApiException;
import pojo.LoginInfo;
import pojo.ResponseResult;
import utils.GitLabApiUtil;
import utils.PropertiesUtil;

import java.io.IOException;
import java.util.Optional;

/**
 * @author liu_wp
 * @date 2020/11/10
 * @see
 */
public class LoginService extends AbstractCustomMsgDialogEvent {
    public LoginInfo loginInfo;

    public LoginService(EventEnum eventEnum, final LoginInfo loginInfo) {
        super(eventEnum);
        this.loginInfo = loginInfo;
    }

    @Override
    public ResponseResult handle() {
        System.out.println("CustomMsgDialogOpenedEvent");
        ResponseResult responseResult = login(loginInfo, true);
        return responseResult;
    }

    /**
     * @return
     */
    public static ResponseResult logout(LoginInfo loginInfo) {
        try {
            loginInfo = Optional.ofNullable(loginInfo).orElse(new LoginInfo());
            loginInfo.setLoginPwd("");
            loginInfo.setActionType(AccountActionType.LOGOUT.getType());
            if (PropertiesUtil.setPropertyValue(CommonConstants.LOGIN_FILE, loginInfo, true)) {
                return ResponseResult.success();
            }
        } catch (Exception e) {
        }
        return ResponseResult.fail("注销失败");
    }

    /**
     *
     */
    public static void restart() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", CommonConstants.APPLICATION_JAR);
                try {
                    processBuilder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.exit(0);
    }

    /**
     * @param loginInfo
     * @return
     */
    public static ResponseResult login(LoginInfo loginInfo, boolean updateFile) {
        if (loginInfo != null) {
            try {
                boolean result = GitLabApiUtil.autoGitLabLogin(loginInfo);
                if (updateFile) {
                    loginInfo.setActionType(AccountActionType.DEFAULT.getType());
                    result = PropertiesUtil.setPropertyValue(CommonConstants.LOGIN_FILE, loginInfo, true);
                }
                if (result) {
                    return ResponseResult.success();
                }
            } catch (GitLabApiException e) {
                int httpStatus = e.getHttpStatus();
                if (httpStatus == 401) {
                    return ResponseResult.fail("【" + loginInfo.getGitlabHostUrl() + "】用户名或密码错误");
                }
            }
        }
        return ResponseResult.fail("登录失败");
    }
}

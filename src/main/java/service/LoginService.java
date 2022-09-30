package service;

import config.AccountActionType;
import config.CommonConstants;
import forms.dialog.GitLabConfigDialog;
import forms.dialog.event.AbstractCustomMsgDialogEvent;
import forms.dialog.event.EventEnum;
import org.gitlab4j.api.GitLabApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.LoginInfo;
import pojo.ResponseResult;
import utils.GitLabApiUtil;
import utils.SQLiteUtil;

import java.io.IOException;

/**
 * @author liu_wp
 * @date 2020/11/10
 * @see
 */
public class LoginService extends AbstractCustomMsgDialogEvent {
    public LoginInfo loginInfo;
    private static final Logger log = LoggerFactory.getLogger(GitLabConfigDialog.class);

    public LoginService(EventEnum eventEnum, final LoginInfo loginInfo) {
        super(eventEnum);
        this.loginInfo = loginInfo;
    }

    @Override
    public ResponseResult handle() {
        return login(loginInfo, true);
    }

    /**
     * @return
     */
    public static ResponseResult logout(LoginInfo loginInfo) {
        try {
            if (loginInfo != null) {
                log.info("{}退出登录", loginInfo.getLoginName());
            }
            CommonConstants.GLOBE_LOGIN_INFO = null;
            return ResponseResult.success();
        } catch (Exception e) {
            log.error("注销失败{}", e.getMessage());
        }
        return ResponseResult.fail("注销失败");
    }

    /**
     *
     */
    public static void restart() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", CommonConstants.APPLICATION_JAR);
            try {
                processBuilder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
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
                    result = SQLiteUtil.insertOrUpdate(loginInfo);
//                    result = PropertiesUtil.setPropertyValue(CommonConstants.LOGIN_FILE, loginInfo, true);
                }
                if (result) {
                    return ResponseResult.success(loginInfo);
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

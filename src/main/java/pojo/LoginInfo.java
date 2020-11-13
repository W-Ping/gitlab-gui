package pojo;

import config.CommonConstants;

/**
 * @author lwp
 * @create 2020/11/08
 */
public class LoginInfo {
    private String loginName;
    private String loginPwd;
    private String gitlabHostUrl = CommonConstants.GITLAB_HOST;
    private String actionType;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(final String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(final String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getGitlabHostUrl() {
        return gitlabHostUrl;
    }

    public void setGitlabHostUrl(final String gitlabHostUrl) {
        this.gitlabHostUrl = gitlabHostUrl;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(final String actionType) {
        this.actionType = actionType;
    }
}

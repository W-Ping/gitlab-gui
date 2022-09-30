package pojo;

import annotation.Column;
import annotation.IgnoreReflection;
import annotation.Table;
import config.CommonConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lwp
 * @create 2020/11/08
 */
@Table(value = "login_info", uniqueKey = {"login_name", "gitlab_host_url"})
public class LoginInfo extends BaseInfo {
    @Column("login_name")
    private String loginName;
    @Column("login_pwd")
    private String loginPwd;
    @Column("gitlab_host_url")
    private String gitlabHostUrl = CommonConstants.GITLAB_HOST;
    @IgnoreReflection
    @Deprecated
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

    /**
     * 有效
     * @return
     */
    public boolean loginIsEffective() {
        return StringUtils.isNotBlank(loginName);
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "loginName='" + loginName + '\'' +
                ", loginPwd='" + loginPwd + '\'' +
                ", gitlabHostUrl='" + gitlabHostUrl + '\'' +
                ", actionType='" + actionType + '\'' +
                '}';
    }
}

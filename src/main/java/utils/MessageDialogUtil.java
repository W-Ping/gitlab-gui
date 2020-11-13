package utils;

import forms.dialog.*;
import forms.dialog.event.AbstractCustomMsgDialogEvent;
import org.gitlab4j.api.models.User;
import pojo.GitlabDetailInfo;
import pojo.LoginInfo;

import java.awt.*;

/**
 * @author liu_wp
 * @date 2020/11/10
 * @see
 */
public class MessageDialogUtil {
    /**
     * @param message
     * @param parentComponent
     * @param eventList
     * @return
     */
    public static CustomMsgDialog showModalDialog(String message, Component parentComponent, AbstractCustomMsgDialogEvent... eventList) {
        final CustomMsgDialog dialog = new CustomMsgDialog(parentComponent, message, null, true, true, eventList);
        return dialog;
    }

    /**
     * @param message
     * @param parentComponent
     * @param isAutoClose
     * @param eventList
     * @return
     */
    public static CustomMsgDialog showModalDialog(String message, Component parentComponent, boolean isAutoClose, AbstractCustomMsgDialogEvent... eventList) {
        final CustomMsgDialog dialog = new CustomMsgDialog(parentComponent, message, null, true, isAutoClose, eventList);
        return dialog;
    }

    /**
     * @param parentComponent
     * @return
     */
    public static GitLabConfigDialog showGitLabConfigDialog(Component parentComponent, LoginInfo loginInfo) {
        GitLabConfigDialog gitLabConfigDialog = new GitLabConfigDialog(parentComponent, loginInfo);
        return gitLabConfigDialog;
    }

    /**
     * @param parentComponent
     * @param user
     * @return
     */
    public static UserDialog createUserDialog(Component parentComponent, User user) {
        UserDialog userDialog = new UserDialog(parentComponent, user);
        return userDialog;
    }

    /**
     * @param parentComponent
     * @return
     */
    public static LoginDialog createLoginDialog(Component parentComponent) {
        return new LoginDialog(parentComponent);
    }

    /**
     * @param parentComponent
     * @param gitlabDetailInfo
     * @return
     */
    public static GitlabDetailDialog createGitlabDetailDialog(Component parentComponent, GitlabDetailInfo gitlabDetailInfo) {
        GitlabDetailDialog gitlabDetailDialog = new GitlabDetailDialog(parentComponent, gitlabDetailInfo);
        return gitlabDetailDialog;
    }

    /**
     * @param parentComponent
     * @param gitlabDetailInfo
     * @return
     */
    public static GitlabBranchesDialog createGitlabBranchesDialog(Component parentComponent, GitlabDetailInfo gitlabDetailInfo) {
        return new GitlabBranchesDialog(parentComponent, gitlabDetailInfo);
    }
}

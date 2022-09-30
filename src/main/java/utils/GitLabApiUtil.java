package utils;

import config.CommonConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.GroupApi;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.ProtectedBranch;
import org.gitlab4j.api.models.User;
import pojo.LoginInfo;

import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/4
 * @see
 */
public class GitLabApiUtil {
    private static GitLabApi gitLabApiReq;

    public static void setGitLabApi(final GitLabApi gitLabApi) {
        gitLabApiReq = gitLabApi;
    }

    public static synchronized LoginInfo getDefaultLogin() {
        final LoginInfo loginInfo = CommonConstants.GLOBE_LOGIN_INFO;
        if (loginInfo != null) {
            return loginInfo;
        }
        final List<LoginInfo> loginInfos = SQLiteUtil.select(new LoginInfo());
        if (CollectionUtils.isEmpty(loginInfos)) {
            return CommonConstants.GLOBE_LOGIN_INFO = new LoginInfo();
        }
        return CommonConstants.GLOBE_LOGIN_INFO = loginInfos.get(0);
    }

    /**
     * @param loginInfo
     * @return
     * @throws GitLabApiException
     */
    public static boolean autoGitLabLogin(LoginInfo loginInfo) throws GitLabApiException {
        try {
            GitLabApi gitLabApi = GitLabApi.oauth2Login(loginInfo.getGitlabHostUrl(), loginInfo.getLoginName(), loginInfo.getLoginPwd());
            if (gitLabApi != null) {
                GitLabApiUtil.setGitLabApi(gitLabApi);
                return true;
            }
        } catch (GitLabApiException e) {
            throw e;
        }
        return false;
    }

    /**
     * @return
     */
    public static GroupApi getGroupApi() {
        GroupApi groupApi = gitLabApiReq.getGroupApi();
        return groupApi;
    }

    /**
     * @param search
     * @return
     */
    public static List<Project> getProjects(String search) {
        try {

            List<Project> projects;
            if (StringUtils.isNotBlank(search)) {
                projects = gitLabApiReq.getProjectApi().getProjects(search);
            } else {
                projects = gitLabApiReq.getProjectApi().getProjects();
            }

            return projects;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @return
     */
    public static User getCurrentUser() {
        try {
            return gitLabApiReq.getUserApi().getCurrentUser();
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param projectIdOrPath
     * @param newBranchName
     * @param refBranch
     * @return
     */
    public static Branch createNewBranch(String projectIdOrPath, String newBranchName, String refBranch) {
        try {
            Branch branch = gitLabApiReq.getRepositoryApi().createBranch(projectIdOrPath, newBranchName, refBranch);
            return branch;
        } catch (GitLabApiException e) {
            return null;
        }
    }

    /**
     * @param projectIdOrPath
     * @param branchName
     * @return
     */
    public static ProtectedBranch getProtectedBranch(String projectIdOrPath, String branchName) {
        try {
            ProtectedBranch protectedBranch = gitLabApiReq.getProtectedBranchesApi().getProtectedBranch(projectIdOrPath, branchName);
            return protectedBranch;
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param projectIdOrPath
     * @param branchName
     * @return
     */
    public static boolean deleteBranch(String projectIdOrPath, String branchName) {
        try {
            gitLabApiReq.getRepositoryApi().deleteBranch(projectIdOrPath, branchName);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * @param projectId
     * @return
     */
    public static List<Branch> getBranchByProjectId(String projectId) {
        try {
            List<Branch> branches = gitLabApiReq.getRepositoryApi().getBranches(projectId);

            return branches;
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param projectId
     * @param branchName
     * @return
     */
    public static Branch getOneBranch(String projectId, String branchName) {
        try {
            Branch branch = gitLabApiReq.getRepositoryApi().getBranch(projectId, branchName);
            return branch;
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}

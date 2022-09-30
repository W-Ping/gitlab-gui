package config;

import pojo.LoginInfo;

import java.awt.*;

/**
 * @author liu_wp
 * @date 2020/11/3
 * @see
 */
public class CommonConstants {
    public final static String PROJECT_ROOT_PATH = System.getProperty("user.dir");
    public final static String SYS_TEMP_PATH = System.getProperty("java.io.tmpdir");
    public final static String RESOURCES_PATH = "\\src\\main\\resources\\";
    public final static String LOGIN_FILE = "login.properties";
    public final static String APPLICATION_JAR = "gitLab-gui.jar";

    public final static String HEADER_PRIVATE_TOKEN = "PRIVATE-TOKEN";
    public final static String HEADER_PRIVATE_TOKEN_VALUE = "-Senny8xHrkhwmA-_3qU";
    public final static String GITLAB_HOST = "https://git.snd00.com/";
    public static final String SQLITE_DEFAULT_DB = "gitlab";

    public static final String SQLITE_DEFAULT_NAME = "gitlab_sb";
    public static final String SQLITE_DEFAULT_PWD = "gitlab_sb@";
    /**
     * 全局登录用户
     */
    public static LoginInfo GLOBE_LOGIN_INFO = null;

    public static final Font DEFAULT_FONT = new Font("Helvetica", Font.PLAIN, 12);
    public final static String SYS_ICON = "images/Gitlab.png";
}

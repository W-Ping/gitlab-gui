package utils;


import connect.JDBCService;
import connect.JdbcServiceFactory;
import enums.DataSourceTypeEnum;
import pojo.BaseInfo;
import pojo.DbTableInfo;
import pojo.JdbcSourceInfo;
import pojo.LoginInfo;

import java.util.Arrays;
import java.util.List;

/**
 * @author lwp
 * @date 2022-05-23
 */
public class SQLiteUtil {
    public static JDBCService SERVER;

    public static JDBCService getSingleton() {
        if (SERVER == null) {
            synchronized (SQLiteUtil.class) {
                if (SERVER == null) {
                    JdbcSourceInfo jdbcSourceInfo = new JdbcSourceInfo();
                    jdbcSourceInfo.setUserName("gitlab");
                    jdbcSourceInfo.setPassword("gitlabPing123");
                    jdbcSourceInfo.setDataSourceTypeEnum(DataSourceTypeEnum.SQLITE);
                    return SERVER = JdbcServiceFactory.getJdbcService(jdbcSourceInfo);
                }
            }
        }
        return SERVER;
    }

    private SQLiteUtil() {
    }

    public static boolean init(boolean forceCreate) {
        return createJDBCSourceTable(forceCreate) && createLoginTable(forceCreate);
    }

    /**
     * @param forceCreate
     * @return
     */
    public static boolean createJDBCSourceTable(boolean forceCreate) {
        final JDBCService jdbcService = SQLiteUtil.getSingleton();
        DbTableInfo c0 = new DbTableInfo("id", "INTEGER", "索引", true, true);
        DbTableInfo c1 = new DbTableInfo("connect_name", "VARCHAR(100)", "连接名称", false, false);
        DbTableInfo c2 = new DbTableInfo("connect_host", "VARCHAR(100)", "连接地址", false, false);
        DbTableInfo c3 = new DbTableInfo("connect_port", "INT2", "端口", false, false);
        DbTableInfo c4 = new DbTableInfo("user_name", "VARCHAR(100)", "用户名称", false, false);
        DbTableInfo c5 = new DbTableInfo("password", "VARCHAR(100)", "密码", false, false);
        DbTableInfo c6 = new DbTableInfo("source_type", "VARCHAR(50)", "类型", false, false);
        DbTableInfo c7 = new DbTableInfo("init_db", "VARCHAR(50)", "初始数据库", false, false);
        List<DbTableInfo> tableInfoList = Arrays.asList(c0, c1, c2, c3, c4, c5, c6, c7);
        return jdbcService.createTableIfAbsent(JdbcSourceInfo.class, tableInfoList, forceCreate);
    }

    /**
     * @return
     */
    public static boolean createLoginTable(boolean forceCreate) {
        final JDBCService jdbcService = SQLiteUtil.getSingleton();
        DbTableInfo c0 = new DbTableInfo("id", "INTEGER", "索引", true, true);
        DbTableInfo c1 = new DbTableInfo("gitlab_host_url", "VARCHAR(100)", "地址", false, false);
        DbTableInfo c2 = new DbTableInfo("login_name", "VARCHAR(100)", "用户名称", false, false);
        DbTableInfo c3 = new DbTableInfo("login_pwd", "VARCHAR", "用户密码", false, false);
        List<DbTableInfo> tableInfoList = Arrays.asList(c0, c1, c2, c3);
        return jdbcService.createTableIfAbsent(LoginInfo.class, tableInfoList, forceCreate);
    }

    /**
     * @param object
     * @param <T>
     * @return
     */
    public static <T extends BaseInfo> List<T> select(T object) {
        return SQLiteUtil.getSingleton().select(object);
    }

    /**
     * @param obj
     * @param <T>
     * @return
     */
    public static <T extends BaseInfo> boolean insertOrUpdate(T obj) {
        if (obj.getId() != null) {
            return SQLiteUtil.getSingleton().updateByPk(obj);
        }
        return SQLiteUtil.getSingleton().insert(obj);
    }


}

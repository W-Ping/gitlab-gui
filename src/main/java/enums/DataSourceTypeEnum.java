package enums;

import config.CommonConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import pojo.JdbcSourceInfo;
import utils.JSONUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;

/**
 * @author liu_wp
 * @date Created in 2020/11/18 17:00
 * @see
 */
public enum DataSourceTypeEnum {
    SQLITE {
        @Override
        public String getDriver() {
            return "org.sqlite.JDBC";
        }

        @Override
        public int defaultPort() {
            return -1;
        }

        @Override
        public String defaultInitDb() {
            return null;
        }

        @Override
        public String typeName() {
            return "SQLite";
        }

        @Override
        public String getUrl(final JdbcSourceInfo JDBCSourceInfo) {
            String dbName = StringUtils.isNotBlank(JDBCSourceInfo.getInitDb()) ? JDBCSourceInfo.getInitDb() : CommonConstants.SQLITE_DEFAULT_DB;
            String url = "jdbc:sqlite:" + dbName + ".db";
            return url;
        }

        @Override
        public Connection getConnection(final JdbcSourceInfo jdbcSourceInfo) {
            try {
                Class.forName(this.getDriver());
                String userName = Optional.ofNullable(jdbcSourceInfo.getUserName()).orElse(CommonConstants.SQLITE_DEFAULT_NAME);
                String pwd = Optional.ofNullable(jdbcSourceInfo.getPassword()).orElse(CommonConstants.SQLITE_DEFAULT_PWD);
                return DriverManager.getConnection(this.getUrl(jdbcSourceInfo), userName, pwd);
            } catch (Exception e) {
                LoggerFactory.getLogger(DataSourceTypeEnum.class).error("SQLite获取连接失败参数：【{}】错误：【{}】", JSONUtil.Object2JSON(jdbcSourceInfo), e.getMessage());
            }
            return null;
        }
    };

    /**
     * @return
     */
    public abstract String getDriver();

    /**
     * @return
     */
    public abstract int defaultPort();

    public abstract String defaultInitDb();

    /**
     * @return
     */
    public abstract String typeName();

    public abstract String getUrl(JdbcSourceInfo JDBCSourceInfo);

    public abstract Connection getConnection(JdbcSourceInfo jdbcSourceInfo);

    public static DataSourceTypeEnum getDataSourceTypeEnum(String typeName) {
        for (final DataSourceTypeEnum value : values()) {
            if (value.typeName().equals(typeName)) {
                return value;
            }
        }
        return null;
    }

}

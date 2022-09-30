package pojo;

import annotation.Column;
import annotation.IgnoreReflection;
import annotation.Table;
import enums.DataSourceTypeEnum;

/**
 * 数据库连接信息
 *
 * @author liu_wp
 * @date 2020/11/17
 * @see
 */
@Table(value = "jdbc_source_info", uniqueKey = {"connect_name", "connect_host", "connect_port", "source_type", "init_db"})
public class JdbcSourceInfo extends BaseInfo {

    /**
     * 连接名称
     */
    @Column("connect_name")
    private String connectName;
    /**
     * 连接地址（域名或IP）
     */
    @Column("connect_host")
    private String connectHost;
    /**
     * 连接端口
     */
    @Column("connect_port")
    private Integer connectPort;
    /**
     * 用户名
     */
    @Column("user_name")
    private String userName;
    /**
     * 初始数据库
     */
    @Column("init_db")
    private String initDb;
    /**
     * 用户密码
     */
    @Column("password")
    private String password;
    @Column("source_type")
    private String sourceType;


    @IgnoreReflection
    private DataSourceTypeEnum dataSourceTypeEnum;


    @Override
    public JdbcSourceInfo clone() {
        try {
            JdbcSourceInfo jdbcSourceInfo = new JdbcSourceInfo();
            jdbcSourceInfo.setConnectName(this.getConnectName());
            jdbcSourceInfo.setConnectHost(this.getConnectHost());
            jdbcSourceInfo.setConnectPort(this.getConnectPort());
            jdbcSourceInfo.setUserName(this.getUserName());
            jdbcSourceInfo.setPassword(this.getPassword());
            jdbcSourceInfo.setInitDb(this.getInitDb());
            jdbcSourceInfo.setSourceType(this.getSourceType());
            jdbcSourceInfo.setDataSourceTypeEnum(this.getDataSourceTypeEnum());
            return jdbcSourceInfo;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return this.connectName;
    }


    public String getConnectName() {
        return connectName;
    }

    public void setConnectName(final String connectName) {
        this.connectName = connectName;
    }

    public String getConnectHost() {
        return connectHost;
    }

    public void setConnectHost(final String connectHost) {
        this.connectHost = connectHost;
    }

    public Integer getConnectPort() {
        return connectPort;
    }

    public void setConnectPort(final Integer connectPort) {
        this.connectPort = connectPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public DataSourceTypeEnum getDataSourceTypeEnum() {
        if (this.sourceType != null) {
            return this.dataSourceTypeEnum = DataSourceTypeEnum.getDataSourceTypeEnum(this.sourceType);
        }
        return dataSourceTypeEnum;
    }

    public void setDataSourceTypeEnum(final DataSourceTypeEnum dataSourceTypeEnum) {
        if (null != dataSourceTypeEnum) {
            this.sourceType = dataSourceTypeEnum.typeName();
        }
        this.dataSourceTypeEnum = dataSourceTypeEnum;
    }


    public String getSourceType() {
        if (null != dataSourceTypeEnum) {
            return this.sourceType = dataSourceTypeEnum.typeName();
        }
        return sourceType;
    }

    public void setSourceType(final String sourceType) {
        if (this.sourceType != null) {
            this.dataSourceTypeEnum = DataSourceTypeEnum.getDataSourceTypeEnum(this.sourceType);
        }
        this.sourceType = sourceType;
    }

    public String getInitDb() {
        return initDb;
    }

    public void setInitDb(String initDb) {
        this.initDb = initDb;
    }
}

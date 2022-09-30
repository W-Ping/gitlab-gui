package connect;

import pojo.JdbcSourceInfo;

/**
 * @author liu_wp
 * @date 2020/11/18
 * @see
 */
public class JdbcServiceFactory {
    /**
     * @param jdbcSourceInfo
     * @return
     */
    public static JDBCService getJdbcService(JdbcSourceInfo jdbcSourceInfo) {
        switch (jdbcSourceInfo.getDataSourceTypeEnum()) {
            case SQLITE:
                return new SQLiteJDBCServiceImpl(jdbcSourceInfo);
            default:
                return null;
        }
    }


}

package connect;

import annotation.Column;
import annotation.Table;
import enums.DataSourceTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.BaseInfo;
import pojo.DbTableInfo;
import pojo.JdbcSourceInfo;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lwp
 * @date 2022-09-29
 */

public abstract class AbstractJDBCService<T extends BaseInfo> implements JDBCService<T> {
    protected static final Logger log = LoggerFactory.getLogger(AbstractJDBCService.class);
    protected JdbcSourceInfo jdbcSourceInfo;

    public AbstractJDBCService(JdbcSourceInfo jdbcSourceInfo) {
        this.jdbcSourceInfo = jdbcSourceInfo;
    }
    protected abstract DataSourceTypeEnum dataSourceTypeEnum();
    /**
     * @return
     */
    protected Connection getConnection() {
        return dataSourceTypeEnum().getConnection(jdbcSourceInfo);
    }
    /**
     * 新建表
     *
     * @param cls          表对应的实体类
     * @param dbTableInfos 表字段信息
     * @param forceCreate  是否强制创建
     * @return
     */
    @Override
    public boolean createTableIfAbsent(Class<T> cls, List<DbTableInfo> dbTableInfos, boolean forceCreate) {
        final Table annotation = cls.getAnnotation(Table.class);
        final String tableName = annotation.value();
        if (!forceCreate && checkTableIsExist(tableName)) {
            return true;
        }
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            log.info(">>>>>> drop table if exists {}", tableName);
            statement.execute("DROP TABLE IF EXISTS " + tableName);
            statement.execute(this.createTableSql(tableName, dbTableInfos));
            log.info(">>>>>> create table {}", tableName);
            final String[] uniqueKey = annotation.uniqueKey();
            if (uniqueKey != null && uniqueKey.length > 0) {
                StringBuilder uk = new StringBuilder();
                String ukName = tableName + "_uk";
                uk.append("CREATE UNIQUE INDEX ");
                uk.append(ukName);
                uk.append(" ON ");
                uk.append(tableName);
                uk.append("(");
                uk.append(Arrays.stream(uniqueKey).collect(Collectors.joining(",")));
                uk.append(")");
                statement.execute(uk.toString());
            }
            return true;
        } catch (Exception e) {
            log.error("错误！创建表失败【{}】", e.getMessage());
        } finally {
            close(connection, statement, null);
        }
        return false;
    }
    @Override
    public boolean checkTableIsExist(String tableName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT count(*) from sqlite_master where type='table' and name=?");
            statement.setString(1, tableName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (Exception e) {
            log.error("错误！检查表是否存在失败【{}】", e.getMessage());
        } finally {
            close(connection, statement, resultSet);
        }
        return false;
    }
    protected String createTableSql(String tableName, List<DbTableInfo> dbTableInfos) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append(tableName);
        sb.append("(");
        List<String> primaryKeys = new ArrayList<>();
        for (int i = 0; i < dbTableInfos.size(); i++) {
            DbTableInfo dbTableInfo = dbTableInfos.get(i);
            sb.append(dbTableInfo.getColumn());
            sb.append(" ");
            sb.append(dbTableInfo.getJdbcType());
            if (dbTableInfo.getCanNull() != null && !dbTableInfo.getCanNull()) {
                sb.append(" NOT NULL ");
            }
            if (dbTableInfo.getPrimaryKey() != null && dbTableInfo.getPrimaryKey()) {
                primaryKeys.add(dbTableInfo.getColumn());
            }
            if (dbTableInfo.getAutoIncrement() != null && dbTableInfo.getAutoIncrement()) {
                sb.append(" AUTO_INCREMENT ");
            }
            if (StringUtils.isNotBlank(dbTableInfo.getDefaultValue())) {
                sb.append(" DEFAULT ");
                sb.append(dbTableInfo.getDefaultValue());
            }
            if (StringUtils.isNotBlank(dbTableInfo.getComment())) {
                sb.append(" COMMENT ");
                sb.append("'");
                sb.append(dbTableInfo.getComment());
                sb.append("'");
            }
            if (i != dbTableInfos.size() - 1) {
                sb.append(",");
            }
        }
        if (!CollectionUtils.isEmpty(primaryKeys)) {
            final String pkStr = primaryKeys.stream().collect(Collectors.joining(","));
            sb.append(",PRIMARY KEY ");
            sb.append("(");
            sb.append(pkStr);
            sb.append(")");
        }
        sb.append(")");
        log.debug("创建表SQL：{}", sb);
        return sb.toString();
    }

    @Override
    public boolean insert(T obj) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            Class<? extends BaseInfo> aClass = obj.getClass();
            Field[] declaredFields = FieldUtils.getAllFields(aClass);
            Table tbAnnotation = aClass.getAnnotation(Table.class);
            StringBuilder columns = new StringBuilder();
            StringBuilder columnValues = new StringBuilder();
            columns.append(" (");
            columnValues.append(" (");
            final List<Field> fields = Arrays.stream(declaredFields).filter(f -> f.getAnnotation(Column.class) != null).collect(Collectors.toList());
            boolean valueAllIsNull = true;
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                field.setAccessible(true);
                Object objValue = field.get(obj);
                final Column annotation = field.getAnnotation(Column.class);
                String value = annotation.value();
                columns.append(value);
                if (field.getType().equals(String.class) && objValue != null) {
                    columnValues.append("'");
                    columnValues.append(objValue);
                    columnValues.append("'");
                    valueAllIsNull = false;
                } else {
                    if (objValue != null) {
                        valueAllIsNull = false;
                    }
                    columnValues.append(objValue);
                }
                if (i != fields.size() - 1) {
                    columns.append(",");
                    columnValues.append(",");
                }
            }
            columns.append(") values");
            columnValues.append(") ");
            StringBuilder sql = new StringBuilder("INSERT INTO ");
            sql.append(tbAnnotation.value());
            sql.append(" ");
            sql.append(columns);
            sql.append(columnValues);
            log.debug("数据新增SQL:{}", sql);
            if (valueAllIsNull) {
                log.error("新增数据失败！{} 字段值都是null", tbAnnotation.value());
                return false;
            }
            statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            final Field field = getPrimaryKeyField(declaredFields);
            while (resultSet != null && resultSet.next()) {
                final long pk = resultSet.getLong(1);
                log.debug("数据新增返回主键：{}", pk);
                field.set(obj, pk);
            }
            return true;
        } catch (Exception e) {
            log.error("错误！数据新增失败【{}】", e.getMessage());
        } finally {
            close(connection, statement, resultSet);
        }
        return false;
    }
    @Override
    public boolean updateByPk(T obj) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            final Class<? extends BaseInfo> cls = obj.getClass();
            final Table annotation = cls.getAnnotation(Table.class);
            final String tableName = annotation.value();
            final Field[] declaredFields = FieldUtils.getAllFields(cls);
            final Field field = getPrimaryKeyField(declaredFields);
            if (field == null && field.get(obj) == null) {
                return false;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ");
            sb.append(tableName);
            sb.append(" ");
            sb.append("SET ");
            final List<Field> fieldList = Arrays.stream(declaredFields).filter(v -> v.getAnnotation(Column.class) != null).collect(Collectors.toList());
            for (int i = 0; i < fieldList.size(); i++) {
                final Field f = fieldList.get(i);
                f.setAccessible(true);
                final Object value = f.get(obj);
                if (value != null) {
                    sb.append(f.getAnnotation(Column.class).value());
                    sb.append("=");
                    sb.append("'");
                    sb.append(value);
                    sb.append("'");
                    if (i != fieldList.size() - 1) {
                        sb.append(",");
                    }
                }
            }
            sb.append(" WHERE ");
            sb.append(field.getName());
            sb.append("=");
            sb.append(field.get(obj));
            statement = connection.createStatement();
            statement.executeUpdate(sb.toString());
            return true;
        } catch (Exception e) {
            log.error("数据库连接失败！{}", e.getMessage());
        } finally {
            close(connection, statement, null);
        }
        return false;
    }
    @Override
    public List<T> select(T obj) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            final Class<? extends BaseInfo> cls = obj.getClass();
            Field[] declaredFields = FieldUtils.getAllFields(cls);
            List<Field> fields = new ArrayList<>();
            final Table tableAnnotation = cls.getAnnotation(Table.class);
            final String table = tableAnnotation.value();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM ");
            sql.append(table);
            StringBuilder where = new StringBuilder(" where 1=1");
            for (Field declaredField : declaredFields) {
                final Column annotation = declaredField.getAnnotation(Column.class);
                if (annotation == null) {
                    continue;
                }
                declaredField.setAccessible(true);
                final String value = annotation.value();
                final Object val = declaredField.get(obj);
                final Class<?> type = declaredField.getType();
                if (val != null) {
                    where.append(" AND ");
                    where.append(value);
                    where.append("=");
                    if (type.equals(String.class) || type.equals(Integer.class) || type.equals(Long.class)) {
                        where.append("'");
                        where.append(val);
                        where.append("'");
                    } else {
                        where.append(val);
                    }
                }
                fields.add(declaredField);
            }
            if (StringUtils.isNotBlank(where)) {
                sql.append(" ");
                sql.append(where);
            }
            log.debug("数据查询SQL:{}", sql);
            resultSet = statement.executeQuery(sql.toString());
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                T t = (T) cls.newInstance();
                for (Field field : fields) {
                    Column annotation = field.getAnnotation(Column.class);
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    if (Integer.class.equals(type)) {
                        int anInt = resultSet.getInt(annotation.value());
                        field.set(t, anInt);
                    } else if (Long.class.equals(type)) {
                        long aLong = resultSet.getLong(annotation.value());
                        field.set(t, aLong);
                    } else if (String.class.equals(type)) {
                        String value = resultSet.getString(annotation.value());
                        if (StringUtils.isNotBlank(value)) {
                            field.set(t, value);
                        }
                    } else if (Double.class.equals(type)) {
                        double aDouble = resultSet.getDouble(annotation.value());
                        field.setDouble(t, aDouble);
                    } else if (BigDecimal.class.equals(annotation.value())) {
                        BigDecimal bigDecimal = resultSet.getBigDecimal(annotation.value());
                        if (bigDecimal != null) {
                            field.setDouble(t, bigDecimal.doubleValue());
                        }
                    }
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            log.error("错误！查询数据失败【{}】", e.getMessage());
        } finally {
            close(connection, statement, resultSet);
        }
        return null;
    }
    protected void close(Connection conn, Statement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                rs = null;
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                ps = null;
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
            }
        }
    }
    private Field getPrimaryKeyField(Field[] fields) {
        final List<Field> pkList = Arrays.asList(fields).stream().filter(v -> {
            final Column annotation = v.getAnnotation(Column.class);
            if (annotation != null && annotation.pk()) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(pkList)) {
            return null;
        }
        final Field field = pkList.get(0);
        field.setAccessible(true);
        return field;
    }
}

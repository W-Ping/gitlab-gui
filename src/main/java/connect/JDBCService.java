package connect;

import pojo.DbTableInfo;

import java.util.List;

/**
 * @author lwp
 * @date 2022-09-29
 */
public interface JDBCService<T> {
    /**
     * 新建表
     *
     * @param cls          表对应的实体类
     * @param dbTableInfos 表字段信息
     * @param forceCreate  是否强制创建
     * @return
     */
    boolean createTableIfAbsent(Class<T> cls, List<DbTableInfo> dbTableInfos, boolean forceCreate);
    /**
     * 检查表是否存在
     *
     * @param tableName
     * @return
     */
    boolean checkTableIsExist(String tableName);
    /**
     * 新增
     *
     * @param obj
     * @return
     */
    boolean insert(T obj);
    /**
     * 根据主键修改数据
     *
     * @param obj
     * @return
     */
    boolean updateByPk(T obj);

    /**
     * 查询所有数据
     *
     * @return
     */
    List<T> select(T obj);
}

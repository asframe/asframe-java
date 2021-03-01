package com.asframe.game.db;

import com.asframe.game.enums.DaoTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * Dao操控接口,定义了基础的dao操控接口，跟具体的数据库实现无关
 * @author sodaChen
 */
public interface IDao<T extends IEntity,M>
{
    /**
     * 获取到Dao操作类型
     * @return
     */
    DaoTypeEnum getType();

    /**
     * 设置表名
     */
    void setTableName(String tableName);

    /**
     * 返回操作模版
     * @return
     */
    void setTemplate(M template);

    /**
     * 根据uid删除数据
     * @param id
     * @return
     */
    boolean delete(long id);
    /**
     * 根据uid删除数据
     * @param playerId
     * @return
     */
    boolean deleteByPlayerId(long playerId);
    /**
     * 插入一条新的记录
     * @param entity
     * @return
     */
    boolean insert(T entity);
    /**
     * 根据id查找数据
     * @param id
     * @return
     */
    T findByID(long id);

    /**
     * 根据id查找数据
     * @param playerId
     * @return
     */
    T findByPlayerId(long playerId);
    /**
     * 根据指定的id来查找对象
     * @param playerId
     * @return
     */
    List<T> findsByPlayerId(long playerId);
    /**
     * 根据指定的id来查找对象
     * @param id
     * @return
     */
    List<T> finds(long id);
    /**
     * 根据指定的key和value来查找对象
     * @param key
     * @param value
     * @return
     */
    List<T> finds(String key,Object value);

    /**
     * 根据指定的key和value来查找对象
     * @param key
     * @param value
     * @return
     */
    T find(String key, Object value);
    /**
     * 根据指定的key和value来查找对象 todo Map
     * @return
     */
    T findByMap(Map<String,Object> paramsMap);
    /**
     * 根据指定的key和value来查找对象 todo Map
     * @return
     */
    List<T> findsByMap(Map<String,Object> paramsMap);


}

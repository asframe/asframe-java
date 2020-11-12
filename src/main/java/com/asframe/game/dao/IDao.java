package com.asframe.game.dao;


import com.asframe.game.dao.entity.MongonEntity;
import com.asframe.game.enums.DaoTypeEnum;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Map;

/**
 * Dao接口，操作数据库库的对象
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/10
 */
public interface IDao<T extends MongonEntity> {

    /**
     * 获取到Dao操作类型
     * @return
     */
    public DaoTypeEnum getType();

    /**
     * 设置表名
     */
    void setTableName(String tableName);

    /**
     * 返回操作模版
     * @return
     */
    void setTemplate(MongoTemplate template);

    /**
     * 根据uid删除数据
     * @param uid
     * @return
     */
    boolean delete(long uid);

    /**
     * 根据根据query 删除字段
     * @param query
     * @return
     */
    boolean deleteByQuery(Query query);


    /**
     * 插入一条新的记录
     * @param entity
     * @return
     */
    boolean insert(T entity);

    /**
     * 保存更新
     */
    boolean saveDb(Query query, Update update);
    /**
     * 根据id查找数据
     * @param uid
     * @return
     */
    T findByUID(long uid);

    /**
     * 根据指定的key和value来查找对象
     * @param uid
     * @return
     */
    List<T> finds(long uid);
    /**
     * 根据指定的key和value来查找对象
     * @param uid
     * @return
     */
    List<T> findsPlayerId(long uid);
    /**
     * 根据指定的key和value来查找对象
     * @param key
     * @param value
     * @return
     */
    List<T> finds(String key, long value);

    /**
     * 根据指定的key和value来查找对象
     * @param key
     * @param value
     * @return
     */
    T find(String key, int value);
    /**
     * 根据指定的key和value来查找对象
     * @param key
     * @param value
     * @return
     */
    T find(String key, long value);
    /**
     * 根据指定的query 查询
     * @return
     */
    List<T> finds(Query query);
    /**
     * 根据指定的key和value来查找对象
     * @param key
     * @param value
     * @return
     */
    T find(String key, String value);


    /**
     * 根据where（老数据）和新的entity进行更新（内部会对两个对象的属性进行比较更新）
     * @param where
     * @param entity
     * @return
     */
    boolean update(Query query, Update update);
    /**
     * 根据where（老数据）和新的entity进行更新（内部会对两个对象的属性进行比较更新）
     * @param where
     * @param entity
     * @return
     */
    boolean updateAll(Query query, Update update);




    /**
     * 根据指定的key和value来查找对象 todo Map
     * @return
     */
    T findByMap(Map<String, Object> paramsMap);

    /**
     * 根据指定的key和value来查找对象 todo Map
     * @return
     */
    List<T> findsByMap(Map<String, Object> paramsMap);
}

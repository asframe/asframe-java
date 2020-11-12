package com.asframe.game.component;

import com.asframe.game.dao.entity.IEntity;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * 玩家组件类 可将玩家按系统功能分为多个组件模块。
 * @author sodaChen
 * @Date 2020/10/10
 */
public interface IComponent<T extends IEntity> {

    String getName();

    /**
     * 组件是否已经初始化过了
     * @return
     */
    boolean isDBInit();

    /**
     * 保存当前组件的所有数据到数据库
     */
    boolean saveToDB(Query query, Update update);


    /**
     * 从数据库表中读取数据到组件中
     * @return
     */
    boolean readFromDB();

    boolean isNeedInsert();

    /**
     * 从数据库中获取数据,如果数据中没有，仍然初始化
     */
    void init(long uid, long playerId);

    /**
     * 创建用户表结构（这个方法慎重）
     */
    void build();
    /**
     * 清除数据
     * @param time
     * @return
     */
    boolean reset(int time);
}

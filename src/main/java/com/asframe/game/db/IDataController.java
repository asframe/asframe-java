package com.asframe.game.db;
/**
 * 数据的数据控制器
 * @author sodaChen
 * @date 2018-10-1
 */
public interface IDataController<D extends IDao,E extends IEntity>
{
    /**
     * 组件是否已经初始化过了
     * @return
     */
    boolean isDBInit();
    /**
     * 从数据库表中读取数据到组件中
     * @return
     */
    boolean readFromDB();

    boolean isNeedInsert();
    /**
     * 创建用户表结构（这个方法慎重）
     * 创建一些默认数据
     */
    void build();
    /**
     * 清除数据
     * @param time
     * @return
     */
    boolean reset(int time);

    /**
     * 当前缓存数据保存到数据库中去
     * @return
     */
    boolean saveToDB();
}

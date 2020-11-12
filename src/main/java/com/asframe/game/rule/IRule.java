package com.asframe.game.rule;

import java.util.Map;

/**
 * 数据的保存接口
 *
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/2
 */
public interface IRule<T>
{
    /**
     * 规则名字
     * @param name
     */
    void setName(String name);

    /**
     * 根据id获取到指定的规则配置数据
     * @param id
     * @return
     */
    T getBean(int id);

    /**
     * 获取到整个表的配置数据
     * @return
     */
    Map<Integer,T> getBeanMap();

    /**
     * 初始化单张配置表的规则
     * @param beanMap
     */
    void init(Map<Integer, T> beanMap);
}

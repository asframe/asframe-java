package com.asframe.game.rule;

import com.asframe.game.exception.RuleConfigException;

import java.util.List;
import java.util.Map;

/**
 * 规则数据的控制器，包含了该规则数据的全部配置数据
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/2
 */
public interface IRule<T extends IRuleBean>
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
     * 根据自定义的key来获取对应的数据结构
     * @param key
     * @return
     */
    T getBeanByKey(int key);

    /**
     * 根据索引小标获取到
     * @param index 下标索引  0，1，2，3
     * @return
     */
    T getBeanByIndex(int index);

    /**
     * 获取到整个表的配置数据
     * @return
     */
    Map<Integer,T> getBeanMap();

    /**
     * 获取规则数据列表
     * @return
     */
    List<T> getBeanList();
    /**
     * 初始化单张配置表的所有规则数据
     * @param beanMap
     */
    void init(Map<Integer, T> beanMap);

    /**
     * 在{@link BasicRuleManager}加载完所有的策划配置数据之后调用，主要用于检查各个策划配置
     * 表配置是否正确，如果不正确，应抛出{@link RuleConfigException}类型的异常，并在异常
     * 消息中记录详细的出错信息
     * @throws RuleConfigException
     */
    void check() throws RuleConfigException;

    /**
     * 做数据，所有的策划配置表数据加载完成之后，会针对每张表做自己特有的数据，避免后面每次查找，提升性能
     */
    void makeDataUp();
}

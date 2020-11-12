package com.asframe.game.rule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * 基础策划配置数据对象
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/2
 */
public class BasicRule<T> implements IRule<T>
{

    private static Log logger	= LogFactory.getLog(BasicRule.class);

    protected Map<Integer,T> beanMap;
    protected String name;

    @Override
    public void setName(String name)
    {
        this.name = name;
    }
    @Override
    public T getBean(int id)
    {
        T bean = this.beanMap.get(id);
        if(bean == null)
        {
            logger.info(id + "没有对应的策划数据:" + this.name);
        }
        return this.beanMap.get(id);
    }

    public Map<Integer,T> getBeanMap()
    {
        return this.beanMap;
    }

    @Override
    public synchronized void init(Map<Integer,T> beanMap) {
        this.beanMap = beanMap;
        //子类实现初始化相关数据
        this.onInit();
        //统一做个循环回掉
        this.beanMap.forEach((id,bean) ->
        {
            this.forEachHandle(id,bean);
        });
        this.onInitEnd();
    }

    protected void onInitEnd()
    {

    }

    protected void forEachHandle(int key,T bean)
    {

    }

    protected void onInit()
    {

    }
}

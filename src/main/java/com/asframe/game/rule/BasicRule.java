package com.asframe.game.rule;

import com.asframe.game.exception.RuleConfigException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础策划配置数据对象
 * 这里还有优化空间，一个是读取配置的话。可以用一个线程来读取。
 * 解析配置，可以采用多线程来解析。 最后所有的数据都解析完成之后
 * 再回到主线程来对数据进行处理
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/2
 */
//public class BasicRule<T extends IRuleBean> implements IRule<T>
public class BasicRule<T> implements IRule<T>
{

    private static Logger logger	= LogManager.getLogger(BasicRule.class);

    /**
     * 基于默认id存放的数据结构
     */
    protected Map<Integer,T> beanMap;
    /**
     * 基于自定义key存放的数据结构
     */
    protected Map<Integer,T> keyBeanMap;
    /**
     * 规则表明
     */
    protected String name;
    /**
     * 是否需要列表数组
     */
    protected boolean isNeedList;

    protected List<T> beanList;

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

    @Override
    public T getBeanByKey(int key) {
        return this.keyBeanMap.get(key);
    }

    @Override
    public T getBeanByIndex(int index) {
        return this.beanList.get(index);
    }

    public Map<Integer,T> getBeanMap()
    {
        return this.beanMap;
    }

    @Override
    public List<T> getBeanList() {
        return this.beanList;
    }

    @Override
    public void init(Map<Integer,T> beanMap) {
        this.beanMap = beanMap;
        this.keyBeanMap = new HashMap<>();
        if(this.isNeedList)
        {
            this.beanList = new ArrayList<>();
        }
        //子类实现初始化相关数据
        this.onInit();
        int length = this.beanMap.size();
        //统一做个循环回掉
        this.beanMap.forEach((id,bean) ->
        {
            this.beanMap.put(id,bean);
            if(this.isNeedList)
            {
                this.beanList.add(bean);
            }
            this.forEachHandle(id,bean,length);
        });
        this.onInitEnd();
    }

    @Override
    public void check() throws RuleConfigException {

    }

    @Override
    public void makeDataUp() {

    }

    /**
     * 初始化表中所有记录之后调用
     */
    protected void onInitEnd()
    {

    }

    /**
     * 每条记录便利，可以做额外的处理
     * @param key
     * @param bean
     */
    protected void forEachHandle(int key,T bean,int length)
    {

    }

    /**
     * 子类扩展，初始化之后调用
     */
    protected void onInit()
    {

    }
}

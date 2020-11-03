/**
 * @Subjects.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.mode.observer;

import com.asframe.utils.ErrorLogUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现了支持派发不同事件的观察者主题对象
 * 这个对象是非线程同步的，在多线程环境下使用需要注意线程安全
 * 如果是多线程环境，最好是在起服的时候注册监听事件
 * 后面的运行状态永远只有发送事件给监听者
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/5
 */
public class Subjects implements ISubjects
{
    private static Logger logger = LogManager.getLogger(Subjects.class);
    private Map<String, List<IObserver>> noticeMaps;
    /**
     * 是否停止On事件了
     */
    private boolean isCloseOn;
    /**
     * 添加锁，防止多线程环境下多个地方同时监听事件
     */
    private Object onLock;

    public Subjects()
    {
        this.noticeMaps = new HashMap<>();
        this.onLock = new Object();
    }

    /**
     * 关闭注册事件的功能
     */
    public void closOn()
    {
        this.isCloseOn = true;
    }
    /**
     * 发起一个通知
     * @param notice 通知
     * @param param 参数是数字
     */
    @Override
    public void send(String notice,Object param)
    {
        List<IObserver> observerList = this.noticeMaps.get(notice);
        if(observerList == null)
        {
            logger.error(notice + "这个事件没有注册对应的观察者");
            return;
        }
        int length = observerList.size();
        if(length == 0)
        {
            return ;
        }
        IObserver observer;
        for (int i = 0; i < length; i++)
        {
            observer = observerList.get(i);
            try
            {
                observer.update(param);
            }
            catch (Exception e)
            {
                logger.error(observer + "执行更新报错:" + param);
                logger.error(ErrorLogUtil.errorTrackSpaceString(e));
            }
        }
    }

    /**
     * 添加一个观察者通知
     * @param notice 通知名称
     * @param observer 通知监听函数
     *
     */
    @Override
    public void on(String notice,IObserver observer)
    {
        if(this.isCloseOn)
        {
            logger.error(notice + "Subjects已经关闭on功能，禁止添加监听事件");
            throw new Error(notice + "Subjects已经关闭on功能，禁止添加监听事件");
        }
        synchronized (this.onLock)
        {
            List<IObserver> observerList = this.noticeMaps.get(notice);
            if(observerList == null)
            {
                observerList = new ArrayList<>();
                this.noticeMaps.put(notice,observerList);
            }
            observerList.add(observer);
        }
    }

    /**
     * 删除一个观察者通知
     * @param notice 通知名称
     * @param observer 删除指定监听函数
     *
     */
    @Override
    public void off(String notice,IObserver observer)
    {
        if(this.isCloseOn)
        {
            logger.error(notice + "Subjects已经关闭on功能，禁止删除监听事件");
            throw new Error(notice + "Subjects已经关闭on功能，禁止删除监听事件");
        }
        synchronized (this.onLock)
        {
            List<IObserver> observerList = this.noticeMaps.get(notice);
            if(observerList == null)
            {
                return ;
            }
            observerList.remove(observer);
        }
    }
}

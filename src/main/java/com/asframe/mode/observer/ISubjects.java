/**
 * @ISubjects.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.mode.observer;

/**
 * 可以有不同的主题的主题对象，支持发布不同的事件让观察者进行接口
 * 每个观察者只会接收自己关注的主题
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/5
 */
public interface ISubjects
{
    /**
     * 发起一个通知
     * @param notice 通知名称
     * @param param 参数是数字
     */
    void send(String notice,Object param);

    /**
     * 添加一个观察者通知
     * @param notice 通知名称
     * @param observer 通知监听函数
     *
     */
    void on(String notice,IObserver observer);

    /**
     * 删除一个观察者通知
     * @param notice 通知名称
     * @param observer 删除指定监听函数
     *
     */
    void off(String notice,IObserver observer);
}

/**
 * @IObserver.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.mode.observer;

/**
 * 观察者接口
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/5
 */
public interface IObserver<T>
{
    /**
     * 观察者收到对应主题发出的通知
     * @param param 变化的参数
     */
    void update(T param);
}

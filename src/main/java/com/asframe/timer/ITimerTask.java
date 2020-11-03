/**
 * @ITimerTask.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.timer;

/**
 * 定时任务顶级接口，所以的定时任务都是采用
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/15
 */
public interface ITimerTask {

    /**
     * 获取到当前的任务类型，唯一的
     */
    int getTaskType();
    /**
     * 任务添加到管理器时都会获得一个唯一id
     * @param timerId
     */
    void setTimerId(long timerId);

    /**
     * 获得唯一id
     * @return
     */
    long getTimerId();
    /**
     * 时间执行接口，具体根据传递的参数来执行这个方法的次数
     */
    void timer();
}

/**
 * @BaseTimerTask.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.timer;

/**
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/17
 */
public class BaseTimerTask implements ITimerTask
{
    /**
     * 任务管理器中的唯一id
     */
    protected long timerId;
    /**
     * 任务管理器中的唯一id
     */
    protected int taskType;

    /**
     * 任务添加到管理器时都会获得一个唯一id
     * @param timerId
     */
    @Override
    public void setTimerId(long timerId)
    {
        this.timerId = timerId;
    }

    /**
     * 获得唯一id
     * @return
     */
    @Override
    public long getTimerId()
    {
        return this.timerId;
    }

    @Override
    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    /**
     * 时间执行接口，具体根据传递的参数来执行这个方法的次数
     */
    @Override
    public void timer()
    {

    }
}

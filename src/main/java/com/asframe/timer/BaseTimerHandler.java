/**
 * @BaseTimerHandler.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础适配器
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/12
 */
public class BaseTimerHandler extends BaseTimerTask implements ITimerHandler {
    public static Logger logger = LoggerFactory.getLogger(BaseTimerHandler.class);
    /**
     * 是否已经取消了
     */
    protected boolean canceled;
    /**
     * 是否已经取消了
     * @return
     */
    @Override
    public boolean isCanceled()
    {
        return this.canceled;
    }

    /**
     * 强行停止这个时间管理器
     */
    @Override
    public void stop()
    {
        this.canceled = true;
    }

    /**
     * 重置时间操作对象，重复使用对象
     */
    public void rest()
    {
        this.canceled = false;
        this.timerId = 0;
        this.taskType = 0;
    }
}

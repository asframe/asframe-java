package com.asframe.timer;

/**
 * 时间处理接口
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/7
 */
public interface ITimerHandler extends ITimerTask
{
    /**
     * 是否已经取消了
     * @return
     */
    boolean isCanceled();

    /**
     * 强行停止这个时间管理器
     */
    void stop();
}

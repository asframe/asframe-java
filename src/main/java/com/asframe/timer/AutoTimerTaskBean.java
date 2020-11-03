package com.asframe.timer;

import java.util.Date;

/**
 * 自动添加计时器的数据结构
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/6
 */
public class AutoTimerTaskBean
{
    /**
     * 添加这个计时器时生成的唯一id
     */
    public long id;
    /**
     * 是否取消了
     */
    public volatile boolean isCancel;
    public ITimerHandler timerHandler;
    public long time;
    /**
     * 下一次的时间
     */
    public long lastTime;
    public Date date;
    /**
     * 当前任务执行器
     */
    public AutoTimerTask autoTimerTask;
    /**
     * 执行次数 0是无限次
     */
    public int count;
    /**
     * 是否为重复
     */
    public boolean isRepeat;
    /**
     * 是否正在跑动中
     */
    public volatile  boolean isRunning;
}

package com.asframe.timer;

import java.util.TimerTask;

/**
 * 计时器管理使用到的自动时间任务
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/6
 */
public class AutoTimerTask extends TimerTask
{
    /**
     * 计时器操作接口，执行用户的回调
     */
    private ITimerHandler timerHandler;
    private AutoTimerTaskBean bean;
    public void init(AutoTimerTaskBean bean)
    {
        this.bean = bean;
        this.timerHandler = bean.timerHandler;
    }
    @Override
    public void run()
    {
        this.timerHandler.timer();
        if(bean.count > 0)
        {
            bean.count--;
            if(bean.count == 0)
            {
                //取消掉当前事件
            }
        }
    }
}

package com.asframe.timer;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 时间管理器，默认前端游戏机制编写的定时器。
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/15
 */
public class TimerManager
{
    private static TimerManager instance = new TimerManager();
    public static TimerManager getInstance()
    {
        return instance;
    }

    private static Logger logger = LogManager.getLogger(TimerManager.class);

    /** 线程池 */
    private ExecutorService executorService;
    /**
     * 计时器对象
     */
    private Timer timer;
    /**
     * 每次定时器的唯一id生成器
     */
    private AtomicLong id;
    /**
     * 存放相关的定时任务
     */
    private Map<Long,AutoTimerTaskBean> beanMap;

    private TimerManager()
    {
        this.timer = new Timer();
        this.beanMap = new ConcurrentHashMap<>();
        this.id = new AtomicLong();
    }

    public void init()
    {
        logger.info("=======TimerManager=======进行初始化");
        this.init(1000,Executors.newFixedThreadPool(5));
    }

    /**
     * 线程池的数量
     * @param threadCount
     */
    public void init(int interval,int threadCount)
    {
        this.init(interval,Executors.newFixedThreadPool(threadCount));
    }

    /**
     * 外面传线程池进来
     * @param executorService
     */
    public void init(int interval,ExecutorService executorService)
    {
//        this.interval = interval;
        this.executorService = executorService;
        //初始化timer的时间间隔
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    timeLoop();
                } catch (Exception e)
                {
                    logger.error("执行timeLoop发生异常:",e);
                }
            }
        };

        this.timer.schedule(task, 0, interval);
    }

    public void cancel(long id)
    {
        AutoTimerTaskBean bean = this.beanMap.remove(id);
        if(null != bean)
        {
            bean.isCancel = true;
            bean.timerHandler = null;
            bean.autoTimerTask = null;
        }
    }

    /**
     * 只跑一次
     * @param time 执行时间毫秒
     * @param timerHandler 执行回调接口
     */
    public long doOnce(long time,ITimerHandler timerHandler)
    {
        return this.doTime(1,time,timerHandler);
    }

    /**
     * 无限循环跑
     * @param time 执行时间毫秒
     * @param timerHandler
     * @return
     */
    public long doLoop(long time,ITimerHandler timerHandler)
    {
        return this.doTime(0,time,timerHandler);
    }

    /**
     * 指定次数跑
     * @param count 执行次数
     * @param time 执行时间毫秒
     * @param timerHandler
     * @return
     */
    public long doTime(int count,long time,ITimerHandler timerHandler)
    {
        AutoTimerTaskBean bean = new AutoTimerTaskBean();
        bean.id = this.id.getAndIncrement();
        timerHandler.setTimerId(bean.id);
        bean.time = time;
        //注意，这里如果time是负数的，则下一轮就会立刻执行这个定时任务了
        bean.lastTime = System.currentTimeMillis() + time;
        bean.timerHandler = timerHandler;
        bean.count = count;
        this.beanMap.put(bean.id,bean);
        return bean.id;
    }

    private void timeLoop()
    {
        synchronized (this.beanMap)
        {
            Iterator<Map.Entry<Long,AutoTimerTaskBean>> entries = this.beanMap.entrySet().iterator();
            while (entries.hasNext())
            {
                Map.Entry<Long,AutoTimerTaskBean> entry = entries.next();
                AutoTimerTaskBean bean = entry.getValue();
                //已经取消了
                if(bean.isCancel)
                {
                    entries.remove();
                    continue;
                }
                //防止重复执行
                if(bean.isRunning) {
                    continue;
                }

                //判断时间是否到了
                if(bean.lastTime > System.currentTimeMillis())
                {
                    continue;
                }
                bean.isRunning = true;
                //时间到了，使用线程池执行任务
                this.executorService.execute(new Runnable() {
                    @Override
                    public void run()
                    {
                        if(bean.isCancel)
                        {
                            return ;
                        }
                        try
                        {
                            bean.timerHandler.timer();
                        }
                        catch (Exception e)
                        {
                            logger.error(bean.timerHandler + "执行timer报错",e);
                        }
                        bean.isRunning = false;
                        //无限重复
                        if(bean.count != 0)
                        {
                            bean.count--;
                            //次数用完，自动删除
                            if(bean.count == 0)
                            {
                                bean.isCancel = true;
                                return ;
                            }
                        }
                        //只有没有取消的定时任务才有增加新的时间的意义
                        bean.lastTime = System.currentTimeMillis() + bean.time;
                    }
                });
            }
        }
    }
}

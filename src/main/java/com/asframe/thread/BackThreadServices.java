/**
 * @BackThread.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 后台线程集合，主要是存放一些用来处理后台业务的线程池集合
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/9
 */
public class BackThreadServices
{
    private static Logger logger = LogManager.getLogger(BackThreadServices.class);
    /**
     * 单线程以队列的形式保存数据
     */
    public static final BackExecutorService singleSaveData = new BackExecutorService("单线保存");
    /**
     * 广播线程，一般用来做大量广播，跟业务线程分开，因为广播有可能量大.可根据实际CPU数量进行配置 cpu+1
     */
    public static final BackExecutorService broadcastThread = new BackExecutorService("广播",3);
    /**
     * 业务线程池，所有需要放到后台线程异步处理的业务，都可以使用这个线程池
     */
    public static final BackExecutorService businessThread = new BackExecutorService("业务",3);

    public static void init()
    {
        logger.info("启动BackThreadServices，进行初始化相关线程池");
        singleSaveData.init();
        broadcastThread.init();
        businessThread.init();
    }
}

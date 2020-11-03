/**
 * @NameThreadFactory.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 具有自己名字的线程池工厂
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/9
 */
public class NameThreadFactory implements ThreadFactory
{
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    NameThreadFactory(String name)
    {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" + poolNumber.getAndIncrement() + "-" +name + " thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        //这里就是给线程命名的地方   namePrefix+当前线程池的第几个线程
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}

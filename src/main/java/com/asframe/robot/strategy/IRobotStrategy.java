/**
 * @IRobotStrategy.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.robot.strategy;

/**
 * 机器人策略接口
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/19
 */
public interface IRobotStrategy<T>
{
    /**
     * 执行的比率，这里是按照数量，数量越多越有机会被执行
     * 框架会统计所有的策略数量，然后进行分配
     * @return
     */
    int getRate();
    /**
     * 执行策略
     * @param robot
     */
    void execute(T robot);
}

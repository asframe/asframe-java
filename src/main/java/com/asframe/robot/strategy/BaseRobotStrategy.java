/**
 * @BaseRobotStrategy.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.robot.strategy;

/**
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/20
 */
public class BaseRobotStrategy
{
    /**
     * 策略执行比例
     */
    protected int rate;


    public BaseRobotStrategy()
    {
        this.rate = 1;
    }
    public BaseRobotStrategy(int rate)
    {
        this.rate = rate;
    }
}

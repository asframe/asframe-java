/**
 * @RobotStrategyManager.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.robot.strategy;

import com.asframe.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 机器人策略管理对象，静态类
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/20
 */
public class RobotStrategyManager
{
    private static Logger logger = LogManager.getLogger(RobotStrategyManager.class);
    private static List<IRobotStrategy> robotStrategyList = new ArrayList<>();
    private static List<Object> robotList = new ArrayList<>();
    private static volatile boolean isRunning = false;
    /**
     * 添加一个机器人
     * @param robot
     */
    public static void addRobot(Object robot)
    {
        if(isRunning)
        {
            List<Object> temp = new ArrayList<>();
            Collections.copy(robotList,temp);
            temp.add(robot);
            robotList = temp;
            return ;
        }
        robotList.add(robot);
    }

    /**
     * 删除一个机器人
     * @param robot
     */
    public static void removeRobot(Object robot)
    {
        if(isRunning)
        {
            List<Object> temp = new ArrayList<>();
            Collections.copy(robotList,temp);
            temp.remove(robot);
            robotList = temp;
            return ;
        }
        robotList.remove(robot);
    }
    /**
     * 添加一个机器人策略机制
     * @param robotStrategy
     */
    public static void addRobotStrategy(IRobotStrategy robotStrategy)
    {
        int rate = robotStrategy.getRate();
        if(rate == 1)
        {
            robotStrategyList.add(robotStrategy);
            return ;
        }
        //循环添加多次
        for (int i = 0; i < rate; i++)
        {
            robotStrategyList.add(robotStrategy);
        }
    }

    /**
     * 调度一个策略来执行
     */
    public static void dispatchStrategy()
    {
        int length = robotStrategyList.size();
        int random = RandomUtils.randomInt(0,length - 1);
        IRobotStrategy robotStrategy = robotStrategyList.get(random);
        if(robotStrategy != null)
        {
            //这里主要是为了防止多线程的时候，操作这个列表。注意这个机制是建立在添加和删除比较少的情况
            //如果频繁添加和删除，则可以考虑遍历的时候进行复制
            List<Object> tempRobotList = robotList;
            isRunning = true;
            int robotCount = tempRobotList.size();
            for (int i = 0; i < robotCount; i++)
            {
                try
                {
                    robotStrategy.execute(tempRobotList.get(i));
                }
                catch (Exception e)
                {
                    logger.error(tempRobotList.get(i) + "执行策略出错" + robotStrategy,e);
                }
            }
            isRunning = false;
        }
    }
}

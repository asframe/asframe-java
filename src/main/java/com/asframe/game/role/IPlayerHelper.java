/**
 * @IPlayerHelper.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.game.role;

/**
 * 玩家扩展接口,同步了玩家的基础操作接口，被动调用，和Player对象保持同步
 * 主要是用于需要使用到玩家的相关基础动作的接口。
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/6
 */
public interface IPlayerHelper<T extends IPlayer>
{
    /**
     * helper绑定对应的player对象
     * @param player
     */
    void init(T player);
    /**
     * 玩家登陆时处理的接口
     */
    void login();

    /**
     * 玩家退出时处理的接口
     */
    void logout();

    /**
     * 进行数据清楚，可以考虑重复使用
     */
    void clear();

}

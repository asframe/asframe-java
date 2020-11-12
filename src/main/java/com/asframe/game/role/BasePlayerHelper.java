/**
 * @BasePlayerHelper.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2020/10/6
 */
package com.asframe.game.role;

/**
 * 基础的玩家辅助对象，实现了基础功能，子类再去扩展
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/6
 */
public abstract class BasePlayerHelper<T extends IPlayer> implements IPlayerHelper<T>
{
    protected T player;
    /**
     * helper绑定对应的player对象
     * @param player
     */
    @Override
    public void init(T player)
    {
        this.player = player;
        this.onInit();
    }

    /**
     * 子类实现自己的初始化方法,如果需要有自己的实现的话
     */
    public void onInit()
    {

    }

    /**
     * 玩家登陆时处理的接口
     */
    @Override
    public void login()
    {

    }

    /**
     * 玩家退出时处理的接口
     */
    @Override
    public void logout()
    {

    }

    /**
     * 进行数据清楚，可以考虑重复使用
     */
    @Override
    public void clear()
    {

    }
}

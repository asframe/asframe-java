/**
 * @ISession.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:TribeGameServer
 * <br>Date:2018-10-1
 */
package com.asframe.game.role;


/**
 * 所有游戏角色类接口，包括玩家 NPC、机器人
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public interface ICharacter
{
    /**
     * 唯一id
     * @param id
     */
    void setId(long id);

    /**
     * 获取唯一id
     * @return
     */
    long getId();
    /**
     * 角色的名字
     * @param name
     */
    void setName(String name);

    /**
     * 获取角色的名称
     * @return
     */
    String getName();
}

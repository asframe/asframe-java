/**
 * @IPlayerSession.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFrame
 * <br>Date:2018-10-1
 */
package com.asframe.game.session;

import com.asframe.game.protocol.ITcpProtocol;
import com.asframe.game.role.IPlayer;

/**
 * 游戏中玩家上线时存在的对象
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public interface IPlayerSession<T extends IPlayer> extends ISession {
    /**
     * 获取到玩家对象
     * @return
     */
    T getPlayer();

    void setPlayer(T player);

    /**
     * 发送Object对象
     * @param object
     */
    void sendMessage(Object object);

    /**
     * 直接发送对象
     * @param protocol
     */
    void sendMessage(ITcpProtocol protocol);

    /**
     * 发送tcp的返回，基于二进制的消息结构体
     * @param resCmd
     * @param protocol
     */
    void sendMessage(short resCmd,ITcpProtocol protocol);

    /**
     * 发送具备错误码的消息结构体
     * @param errorCode
     * @param protocol
     */
    void sendErrorCode(short errorCode, ITcpProtocol protocol);

    /**
     * 直接发送错误码，内置默认错误对象类型
     * @param errorCode
     */
    void sendErrorCode(short resCmd, short errorCode);

}

/**
 * @IPlayer.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:TribeGameServer
 * <br>Date:2018-10-1
 */
package com.asframe.game.role;

import com.asframe.game.protocol.write.ITcpResponse;
import com.asframe.game.session.IPlayerSession;

/**
 * 游戏中的玩家对象接口
 * @author sodaChen
 * @date 2018-10-1
 */
public interface IPlayer extends ICharacter
{
    /**
     * 初始化玩家对象
     */
    void init();
    /**
     * 获取userId
     * @return
     */
    long getUserId();

    /**
     * 是否为机器人
     * @return
     */
    boolean isRobot();

    /**
     * 设置userId
     * @param userId
     */
    void setUserId(long userId);

    /**
     * 添加一个session给玩家。这个session和player绑定，主要用来和前端通讯，和底层tcp机制绑定
     * @param session 玩家session接口
     */
    void setSession(IPlayerSession session);

    /**
     * 获取到玩家的session对象
     * @return
     */
    IPlayerSession getSession();

    /**
     * 当玩家登录时，可以调用此方法，用于处理玩家登陆的相关操作。
     * 一般这个方法里面还会触发相关的其他辅助对象的登陆机制
     * 比如登陆、离线判断，任务等等相关判断，谨慎调用
     */
    void login();

    /**
     * 判断玩家是否掉线了
     * @return
     */
    boolean isLogout();

    /**
     * 当玩家注销时，可以调用此方法。注意这个方法需要谨慎调用，在玩家与服务端断开连接的时候，底层框架会自动调用
     */
    void logout();

    /**
     * 保存玩家相关的所有数据，对里面的所有功能模块执行保存操作
     */
    void saveAll();

    /**
     * 重置玩家对象，用于对象池循环使用
     */
    void reset();

    /**
     * 发送Object对象
     * @param object
     */
    void sendMessage(Object object);

    /**
     * 直接发送对象
     * @param protocol
     */
    void sendMessage(ITcpResponse protocol);

    /**
     * 发送tcp的返回，基于二进制的消息结构体
     * @param resCmd
     * @param protocol
     */
    void sendMessage(short resCmd,ITcpResponse protocol);

    /**
     * 发送具备错误码的消息结构体
     * @param errorCode
     * @param protocol
     */
    void sendErrorCode(short errorCode, ITcpResponse protocol);

    /**
     * 直接发送错误码，内置默认错误对象类型
     * @param errorCode
     */
    void sendErrorCode(short resCmd, short errorCode);
}

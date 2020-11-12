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
package com.asframe.game.session;

import io.netty.channel.Channel;

/**
 * Session对象中
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public interface ISession {
    /**
     * Session的状态类型
     */
    enum Status {
        Not_Connected, Connecting, Connected, Re_Connecting, Closed
    }


    void setId(long id);
    long getId();

    /**
     * 关闭点session
     */
    void close();

    void setStatus(Status status);

    Status getStatus();

    boolean isWriteable();

    void setWriteable(boolean writeable);

    boolean isLogin();

    void setLogin(boolean login);

    public String getKey();

    public void setKey(String key);

    void setChannel(Channel channel);

    Channel getChannel();
}

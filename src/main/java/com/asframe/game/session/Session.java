/**
 * @Session.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFrame
 * <br>Date:2018-10-1
 */
package com.asframe.game.session;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 游戏服务器启动对象
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public class Session implements ISession
{
    static Logger logger = LoggerFactory.getLogger(Session.class);
    protected ISession.Status status;

    protected boolean isWriteable;

    protected boolean isLogin;

    protected long id;
    /**
     * 用于加密用
     */
    protected String key;

    /**
     * 通讯通道
     */
    protected Channel channel;


    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public boolean isWriteable() {
        return isWriteable;
    }

    @Override
    public void setWriteable(boolean writeable) {
        isWriteable = writeable;
    }

    @Override
    public boolean isLogin() {
        return isLogin;
    }

    @Override
    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setChannel(Channel channel)
    {
        this.channel = channel;
    }

    public Channel getChannel()
    {
        return this.channel;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public void close()
    {
        try
        {
            if(this.channel.isOpen() || this.channel.isActive())
                this.channel.close();
        }
        catch (Exception e)
        {
            logger.error("关闭session出现异常:",e);
        }

    }
}

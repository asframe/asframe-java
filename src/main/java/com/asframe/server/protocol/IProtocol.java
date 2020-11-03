/**
 * @IProtocol.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFrame
 * <br>Date:2018-10-1
 */
package com.asframe.server.protocol;


import java.io.Serializable;

/**
 * 协议的基础接口，定义了请求和发送的基础结构
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public interface IProtocol extends Serializable
{
    /**
     * 此次通讯对象的编码，告诉前端通讯时成功还是失败
     *
     * @param code
     */
    void setCode(short code);

    /**
     * 获取编码结果
     *
     * @return
     */
    short getCode();
}

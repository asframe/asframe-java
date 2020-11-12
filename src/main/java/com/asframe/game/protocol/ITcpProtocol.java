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
package com.asframe.game.protocol;

import com.asframe.game.protocol.read.ITcpRead;
import com.asframe.game.protocol.write.ITcpWrite;
import com.asframe.server.protocol.IProtocol;

/**
 * 协议的基础接口，定义了请求和发送的基础结构。这里主要是netty实现的
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public interface ITcpProtocol extends IProtocol, ITcpWrite, ITcpRead
{
    /**
     * 获取到协议的编码类型
     * @return
     */
    byte getCodeType();

    /**
     * 恢复，重用协议对象
     */
    void reset();
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
    /**
     * 设置协议对象唯一的通讯指令
     * @param cmd
     */
    void setCmd(short cmd);
    /**
     * 此次发送指令的编码，根据这个编码来决定如何处理发送数据或者发送去向
     * @return
     */
    short getCmd();
}

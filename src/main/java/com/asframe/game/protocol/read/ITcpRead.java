/**
 * @IRead.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFrame
 * <br>Date:2018-10-1
 */
package com.asframe.game.protocol.read;

import io.netty.buffer.ByteBuf;

/**
 * 从二进制流中读取数据
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public interface ITcpRead{
    /**
     * 根据二进制数据返回结构体
     * @param input
     * @return
     */
    void read(ByteBuf input);
}

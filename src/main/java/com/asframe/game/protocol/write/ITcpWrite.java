/**
 * @IWrite.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFrame
 * <br>Date:2018-10-1
 */
package com.asframe.game.protocol.write;

import io.netty.buffer.ByteBuf;

/**
 * 把数据写进二进制流中
 * @author sodaChen
 * @date 2018-10-1
 *
 */

public interface ITcpWrite {
    /**
     * 把发送结构体写成二进制数据流
     * @param output
     */
    void write(ByteBuf output);
}

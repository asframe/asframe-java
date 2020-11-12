package com.asframe.game.protocol.write;

import com.asframe.game.protocol.BasicTcpProtocol;
import io.netty.buffer.ByteBuf;

/**
 * tcp的返回数据结构实现
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public abstract class BasicTcpResponse extends BasicTcpProtocol implements ITcpResponse
{
    /**
     * 把发送结构体写成二进制数据流
     * @return
     */
    @Override
    public abstract void write(ByteBuf output);
}

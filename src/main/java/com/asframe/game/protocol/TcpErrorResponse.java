/**
 * @TcpErrorResponse.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.game.protocol;

import com.asframe.game.utils.ProtocolUtil;
import com.asframe.server.protocol.ErrorResponse;
import io.netty.buffer.ByteBuf;

/**
 * 基于tcp的错误协议
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/30
 */
public class TcpErrorResponse extends ErrorResponse implements ITcpProtocol
{
    /**
     * 获取到协议的编码类型
     * @return
     */
    @Override
    public byte getCodeType()
    {
        return 0;
    }

    /**
     * 恢复，重用协议对象
     */
    @Override
    public void reset()
    {

    }

     /**
     * 把发送结构体写成二进制数据流
     * @param output
     */
     @Override
     public void write(ByteBuf output)
     {
         ProtocolUtil.writeUTFBinary(output, this.getError());
     }
    /**
     * 根据二进制数据返回结构体
     * @param input
     * @return
     */
    @Override
    public void read(ByteBuf input)
    {
        this.setError(ProtocolUtil.readUTFStr(input));
    }
}

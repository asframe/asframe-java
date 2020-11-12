package com.asframe.game.utils;

import com.asframe.game.module.ModuleMgr;
import com.asframe.game.protocol.read.ITcpRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 编码解码工具类
 * @author sodaChen
 * @version 1.0
 * @date 2018/11/27
 */
public class CodecUtils
{
    /**
     * 解析二进制数据流
     * @param ctx
     * @param in
     * @return
     * @throws Exception
     */
    public static ITcpRequest decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        in.markReaderIndex();
        //读取直接看看
        short requestId = in.readShort();
        byte codeType = in.readByte();//标识
        //解析消息体  ByteArray测试
        short bodySize = in.readShort();
        if (in.readableBytes() >= bodySize) {
            ITcpRequest request = ModuleMgr.getRequest(requestId);
            if (null == request) {
                in.skipBytes(bodySize);
                return null;
            }
            request.reset();
            request.setCmd(requestId);
            request.setCode(codeType);
            //解析数据
            request.read(in);
            return request;
        } else {
            in.resetReaderIndex();
        }
        return null;
    }
}

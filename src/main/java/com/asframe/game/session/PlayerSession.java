/**
 * @PlayerSession.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFrame
 * <br>Date:2018-10-1
 */
package com.asframe.game.session;

import com.asframe.game.protocol.ITcpProtocol;
import com.asframe.game.role.IPlayer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 * 玩家session的实现
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public class PlayerSession extends Session implements IPlayerSession {

    protected IPlayer player = null;


    @Override
    public IPlayer getPlayer()
    {
        return this.player;
    }

    @Override
    public void setPlayer(IPlayer player)
    {
        //互相嵌套
        this.player = player;
    }

    @Override
    public void sendErrorCode(short resCmd,short errorCode) {

    }
    @Override
    public void sendErrorCode(short errorCode, ITcpProtocol protocol) {
        protocol.setCode(errorCode);
        sendMessage(protocol);
    }

    @Override
    public void sendMessage(Object object) {
        this.sendMessage((ITcpProtocol)object);
    }

    @Override
    public void sendMessage(ITcpProtocol protocol)
    {
        ByteBuf out = Unpooled.buffer();
        out.writeShort(protocol.getCmd());
        out.writeByte(0);
        out.writeShort(protocol.getCode());
        //只有正常返回结果才需要进行处理
        if (protocol.getCode() == 0) {
            ByteBuf byteBuf =  Unpooled.buffer();
            protocol.write(byteBuf);
            //写消息体
            out.writeShort(byteBuf.readableBytes());
            out.writeBytes(byteBuf);
        }
        else
        {
            //写个错误消息出去
            protocol.write(out);
        }
        //这里可以做发送数据的大小统计
        this.channel.writeAndFlush(new BinaryWebSocketFrame(out));
      }

    @Override
    public void sendMessage(short resCmd, ITcpProtocol protocol) {
        sendMessage(protocol);
    }
}

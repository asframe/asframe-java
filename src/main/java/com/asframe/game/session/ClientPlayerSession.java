package com.asframe.game.session;

import com.asframe.game.protocol.ITcpProtocol;
import com.asframe.game.role.IPlayer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 * 客户端专用的session对象
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/3
 */
public class ClientPlayerSession extends Session implements IPlayerSession
{
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


    public void sendMessage(ITcpProtocol protocol)
    {
        ByteBuf out = Unpooled.buffer();
        out.writeShort(protocol.getCmd());
        out.writeByte(0);
        //只有正常返回结果才需要进行处理
        if (protocol == null) {
            out.writeByte(0);
        }
        else
        {
            ByteBuf byteBuf =  Unpooled.buffer();
            protocol.write(byteBuf);
            //写消息体
            out.writeShort(byteBuf.readableBytes());
            out.writeBytes(byteBuf);
        }
        this.channel.writeAndFlush(new BinaryWebSocketFrame(out));
    }

    @Override
    public void sendMessage(short resCmd, ITcpProtocol protocol) {

    }
}

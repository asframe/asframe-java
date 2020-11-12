package com.asframe.game.server.handler;

import com.asframe.game.module.ModuleMgr;
import com.asframe.game.protocol.read.ITcpRequest;
import com.asframe.game.utils.CodecUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * webSocket的接入处理
 * @author: sodaChen
 * @date: 2020/10/5 15:10
 */
public class WSServerHandler extends BasicNettyHandler
{
    private static Logger logger = LoggerFactory.getLogger(WSServerHandler.class);

    public WSServerHandler()
    {
        super();
        logger.info("WSServerHandler初始化:" + id);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof WebSocketFrame)
            handleWebSocketFrame(ctx, (WebSocketFrame)msg);
    }

    private ITcpRequest decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.equals(Unpooled.EMPTY_BUFFER)) {
            closeConnection(ctx);
            return null;
        }
        if (in.readableBytes() < HEAD_LEN) return null;

        return CodecUtils.decode(ctx,in);
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if (frame instanceof BinaryWebSocketFrame)
        {
            ITcpRequest request = decode(ctx, frame.content());
            if (null == request)
            {
                logger.info("该协议没有注册");
                return;
            }
            ModuleMgr.dispatch(playerSession,request.getCmd(),request);

        } else if (frame instanceof PingWebSocketFrame) {
            //判断是否Ping消息 -- ping/pong心跳包
            System.out.println("ping/pong心跳包");
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
        }
        else
        {
            logger.info("其他情况发送的数据包:" + frame);
        }
    }
}
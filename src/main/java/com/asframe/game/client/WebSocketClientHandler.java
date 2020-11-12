package com.asframe.game.client;

import com.asframe.game.protocol.write.ITcpResponse;
import com.asframe.game.server.handler.BasicNettyHandler;
import com.asframe.game.session.ClientPlayerSession;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * websocket客户端Handler，目前机器人程序在。完全模拟web客户端的socket通讯
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/10
 */
public class WebSocketClientHandler extends BasicNettyHandler
{
    private static Logger logger = LoggerFactory.getLogger(WebSocketClientHandler.class);



    public WebSocketClientHandler()
    {
        this.sessionClass = ClientPlayerSession.class;
    }

    public WebSocketClientHandler(Class clazz)
    {
        this.sessionClass = clazz;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        if (!this.handshaker.isHandshakeComplete())
        {
            try {
                //握手协议返回，设置结束握手
                this.handshaker.finishHandshake(ch, (FullHttpResponse) msg);
                //设置成功
                this.handshakeFuture.setSuccess();
            } catch (WebSocketHandshakeException var7)
            {
                FullHttpResponse res = (FullHttpResponse) msg;
                String errorMsg = String.format("WebSocket Client failed to connect,status:%s,reason:%s", res.status(), res.content().toString(CharsetUtil.UTF_8));
                this.handshakeFuture.setFailure(new Exception(errorMsg));
                logger.error("WebSocket Client failed to connect,status:%s,reason:" + res.status());
            }
        } else if (msg instanceof FullHttpResponse)
        {
            logger.error("Unexpected FullHttpResponse (getStatus=" + ((FullHttpResponse)msg).status());
        } else {
            if (msg instanceof TextWebSocketFrame)
            {
                TextWebSocketFrame textFrame = (TextWebSocketFrame) msg;
                logger.info("TextWebSocketFrame：" + textFrame.text());
            } else if (msg instanceof BinaryWebSocketFrame)
            {
                this.parseBinaryFrame((BinaryWebSocketFrame) msg);

            } else if (msg instanceof PongWebSocketFrame)
            {
                System.out.println("WebSocket Client received pong");
            } else if (msg instanceof CloseWebSocketFrame)
            {
                System.out.println("receive close frame");
                ch.close();
            }
        }
    }

    protected void parseBinaryFrame(BinaryWebSocketFrame binaryFrame)
    {
        ByteBuf byteBuf = binaryFrame.content();
        short cmd = byteBuf.readShort();
        //编码类型，目前没用
        byteBuf.readByte();
        int code = byteBuf.readShort();
        logger.debug(this.player.getName() + "收到cmd:" + cmd + " code:" + code);
        if(code == 0)
        {
            IResponseCmd responseCmd = ResponseCmdMgr.getResponseCmd(cmd);
            if(responseCmd == null)
            {
                logger.error(cmd + "没有注册对应的IResponseCmd");
                return ;
            }
            ITcpResponse response = responseCmd.createResponse();
            short bodyLen = byteBuf.readShort();
            //填充response里面的数据
            response.read(byteBuf);
            //执行
            responseCmd.handleResponse(this.player,response);
        }
        else
        {
            //错误码，进行统一的错误码抛出处理
        }
    }
}

/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.asframe.game.client;

import com.asframe.game.role.IPlayer;
import com.asframe.game.server.handler.BasicNettyHandler;
import com.asframe.utils.ClassUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 基础的websocket客户端
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/10
 */
public class BasicWebSocketClient {

    private static final Logger logger = LoggerFactory.getLogger(BasicWebSocketClient.class);

    /**
     * 线程池
     */
    protected ScheduledExecutorService executor;
    /**
     * netty客户端业务处理线程池
     */
    protected EventLoopGroup group;
    /**
     * 服务器地址
     */
    protected String host;
    /**
     * 服务器端口号
     */
    protected int port;
    protected IPlayer player;
    protected Class<? extends BasicNettyHandler> wsHandlerClass;
    /**
     * 初始化
     * @param player 玩家对象
     * @param group netty的线程管理组
     * @param executor 业务处理线程池
     */
    public void init(IPlayer player,EventLoopGroup group,ScheduledExecutorService executor)
    {
        this.player = player;
        this.group = group;
        this.executor = executor;
    }

    public void connect(Class<? extends BasicNettyHandler> wsHandlerClass,int port, String host) throws Exception {
        this.port = port;
        this.host = host;
        this.wsHandlerClass = wsHandlerClass;
        this.connectting(wsHandlerClass);
        this.connectEnd();
    }
    protected void connectEnd()
    {

    }
    protected void connectting(Class<? extends BasicNettyHandler> wsHandlerClass) throws Exception
    {
        logger.info(this.player.getName() + "进行socket登陆了");
        // 配置客户端NIO线程组
        try {
            Bootstrap boot = new Bootstrap();
            boot.option(ChannelOption.SO_KEEPALIVE,true)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.SO_BACKLOG,1024*1024*10)
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();
                            p.addLast(new ChannelHandler[]{new HttpClientCodec(),
                                    new HttpObjectAggregator(1024*1024*10)});
                            p.addLast("hookedHandler", ClassUtils.createObject(wsHandlerClass));
                        }
                    });
            StringBuilder sb = new StringBuilder();
            sb.append("ws://").append(host).append(":").append(port).append("/ws");
            URI websocketURI = new URI(sb.toString());
            HttpHeaders httpHeaders = new DefaultHttpHeaders();
            //进行握手
            WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(websocketURI, WebSocketVersion.V13, (String)null, true,httpHeaders);
            System.out.println("connect");
            final Channel channel= boot.connect(websocketURI.getHost(),websocketURI.getPort()).sync().channel();
            BasicNettyHandler handler = (BasicNettyHandler)channel.pipeline().get("hookedHandler");
            handler.setHandshaker(handshaker);
            handshaker.handshake(channel);
            this.player.getSession().setChannel(channel);
            handler.setPlayer(this.player);
            //阻塞等待是否握手成功
            handler.handshakeFuture().sync();
        } finally {
            // 所有资源释放完成之后，清空资源，再次发起重连操作
//            log.error(this.player.getName() + "进行断线重连了");
//            this.connect(wsHandlerClass,port,host);
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        new NettyClient().connect(NettyConstant.PORT, NettyConstant.REMOTEIP);
        BasicWebSocketClient client = new BasicWebSocketClient();
        client.init(null,new NioEventLoopGroup(),Executors.newScheduledThreadPool(5));
        client.connect(WebSocketClientHandler.class,19050, "127.0.0.1");
    }

}

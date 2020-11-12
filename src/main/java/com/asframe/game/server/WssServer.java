/**
 * @GameServer.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFrame
 * <br>Date:2018-10-1
 */
package com.asframe.game.server;

import com.asframe.game.server.handler.BasicNettyHandler;
import com.asframe.utils.ClassUtils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * WebSocket游戏服务器启动对象
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public class WssServer
{
    private static Logger logger	= LogManager.getLogger(WssServer.class);
    protected static final String WEBSOCKET_PATH = "/ws";

    protected static final int READ_TIMEOUT_SECONDS = 1200;

    protected static final int MAX_BUFFER_SIZE = 65536;

    /**
     * ssl证书的对象
     */
    protected SSLContext sslContext;

    /**
     * 初始化SSL容器。如果要使用ssl,必须在init之前调用这个方法
     * @param keystorePass
     * @param jksPath
     */
    public void initSSLContext(String keystorePass,String jksPath)
    {
        logger.info("准备初始化initSSLContext");
        logger.info("keystorePass:" + keystorePass);
        logger.info("jksPath:" + jksPath);
        try {
            InputStream in = new FileInputStream(jksPath);
//            ClassPathResource classPathResource = new ClassPathResource(jksPath);
            KeyStore ks = KeyStore.getInstance("JKS");
//            InputStream in = classPathResource.getInputStream();
            ks.load(in, keystorePass.toCharArray());
            // Set up key manager factory to use our key store
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, keystorePass.toCharArray());

            KeyManager[] km = kmf.getKeyManagers();

            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(km, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭服务
     */
    public void close()
    {

    }
    /**
     * 初始化。如果要使用ssl,必须先调用initSSLContext
     * @param serverPort
     * @param wsHandlerClass
     * @throws Exception
     */
    public void init(int serverPort,Class<? extends BasicNettyHandler> wsHandlerClass) throws Exception
    {
        logger.info("开始启动WeboSocket服务器:");
        logger.info("serverPort:" + serverPort);
        // 服务端启动辅助类，用于设置TCP相关参数
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 获取Reactor线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        SSLContext ssl = this.sslContext;
        // 设置为主从线程模型
        bootstrap.group(bossGroup, workGroup)
                // 设置服务端NIO通信类型
                .channel(NioServerSocketChannel.class)
                // 设置ChannelPipeline，也就是业务职责链，由处理的Handler串联而成，由从线程池处理
                .childHandler(new ChannelInitializer<Channel>() {
                    // 添加处理的Handler，通常包括消息编解码、业务处理，也可以是日志、权限、过滤等
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        // 获取职责链
                        ChannelPipeline pipeline = ch.pipeline();
                        if(ssl != null)
                        {
                            SSLEngine sslEngine = ssl.createSSLEngine();
                            //ssl
                            sslEngine.setUseClientMode(false);
                            sslEngine.setNeedClientAuth(false);
                            pipeline.addFirst("ssl", new SslHandler(sslEngine)); //搞定
                        }
                        //HttpServerCodec将请求和应答消息编码或解码为HTTP消息
                        pipeline.addLast(new HttpServerCodec());
                        //通常接收到的http是一个片段，如果想要完整接受一次请求所有数据，我们需要绑定HttpObjectAggregator, 65536定义缓冲大小
                        pipeline.addLast(new HttpObjectAggregator(MAX_BUFFER_SIZE));
                        pipeline.addLast(new WebSocketServerCompressionHandler());
                        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
                        //在READ_TIMEOUT_SECONDS秒内没有收到数据就断掉。
                        pipeline.addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS));
                        //设置handler解析对象
                        pipeline.addLast(ClassUtils.createObject(wsHandlerClass));
                    }
                })
                // bootstrap 还可以设置TCP参数，根据需要可以分别设置主线程池和从线程池参数，来优化服务端性能。
                // 其中主线程池使用option方法来设置，从线程池使用childOption方法设置。
                // backlog表示主线程池中在套接口排队的最大数量，队列由未连接队列（三次握手未完成的）和已连接队列
                .option(ChannelOption.SO_BACKLOG, 5)
                // 表示连接保活，相当于心跳机制，默认为7200s
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            logger.info("服务初始化完毕");
            // 绑定端口，启动select线程，轮询监听channel事件，监听到事件之后就会交给从线程池处理
            Channel channel = bootstrap.bind(serverPort).sync().channel();
            // 等待服务端口关闭
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            logger.info("ws服务退出");
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}

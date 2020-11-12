package com.asframe.game.client;

import com.asframe.game.session.IPlayerSession;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 基于Netty实现的客户端
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/10
 */
public class NettyClient
{
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    /**
     * 线程池
     */
    private ScheduledExecutorService executor;
    /**
     * netty客户端业务处理线程池
     */
    private EventLoopGroup group;
    /**
     * 服务器地址
     */
    private String host;
    /**
     * 服务器端口号
     */
    private int port;
    private IPlayerSession playerSession;
    /**
     * 初始化
     * @param playerSession 和用户绑定的session对象
     * @param group netty的线程管理组
     * @param executor 业务处理线程池
     */
    public void init(IPlayerSession playerSession,EventLoopGroup group,ScheduledExecutorService executor)
    {
        this.playerSession = playerSession;
        this.group = group;
        this.executor = executor;
    }

    /**
     * 使用之前保存的host和port进行连接
     * @throws Exception
     */
    public void connect()throws Exception
    {
        this.connect(this.port,this.host);
    }

    public void connect(int port, String host) throws Exception {

        this.host = host;
        this.port = port;
        // 配置客户端NIO线程组
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast("readTimeoutHandler",
                                    new ReadTimeoutHandler(50));
                        }
                    });
            // 发起异步连接操作
            ChannelFuture future = b.connect(
                    new InetSocketAddress(host, port)).sync();
            // 当对应的channel关闭的时候，就会返回对应的channel。
            // Returns the ChannelFuture which will be notified when this channel is closed. This method always returns the same future instance.
            future.channel().closeFuture().sync();
        } finally {
            // 所有资源释放完成之后，清空资源，再次发起重连操作
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        try {
                            logger.info("发起断线重连了");
                            connect();// 发起重连操作
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}

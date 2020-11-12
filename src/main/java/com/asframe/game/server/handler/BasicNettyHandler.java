package com.asframe.game.server.handler;

import com.asframe.game.role.IPlayer;
import com.asframe.game.session.IPlayerSession;
import com.asframe.game.session.PlayerSession;
import com.asframe.utils.ClassUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * netty基础的数据解析分析，主要是处理玩家的基础操作。session的进入和退出
 * 注意，这个对象，是一个客户端连接就回初始化一个这样的实例
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/28
 */
public class BasicNettyHandler extends SimpleChannelInboundHandler<Object>
{
    private static Logger logger = LoggerFactory.getLogger(BasicNettyHandler.class);

    protected static final int HEAD_LEN = 5;
    private static AtomicInteger idCount = new AtomicInteger();
    protected int id = 0;
    /**
     * 玩家session对象接口，用来封装单个玩家的通讯
     */
    protected IPlayerSession playerSession;
    protected Class<?> sessionClass;

    /**
     * player对象
     */
    protected IPlayer player;
    /**
     * session的id。网关分配的
     */
    private static long sessionId = 0;
    protected WebSocketClientHandshaker handshaker;
    protected ChannelPromise handshakeFuture;

    public BasicNettyHandler()
    {
        id = idCount.incrementAndGet();
        logger.info("WSServerHandler初始化:" + id);
    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshakeFuture = ctx.newPromise();
    }

    public WebSocketClientHandshaker getHandshaker() {
        return handshaker;
    }

    public void setHandshaker(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelPromise getHandshakeFuture() {
        return handshakeFuture;
    }

    public void setHandshakeFuture(ChannelPromise handshakeFuture) {
        this.handshakeFuture = handshakeFuture;
    }

    public ChannelFuture handshakeFuture() {
        return this.handshakeFuture;
    }

    /**
     * 设置绑定的玩家对象
     * @param player
     */
    public void setPlayer(IPlayer player)
    {
        this.player = player;
        if(this.player.getSession() != null)
        {
            this.playerSession = this.player.getSession();
        }
    }

    /**
     * 设置玩家的session对象
     * @param playerSession
     */
    public void setPlayerSession(IPlayerSession playerSession)
    {
        this.playerSession = playerSession;
    }

    /**
     * 子类扩展实际的业务处理逻辑
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception
    {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("channelActive id:" + id + " tx:" + ctx);
        //如果还没初始化session，则进行初始化session
        if (null == playerSession)
        {
            if(this.sessionClass == null)
            {
                playerSession = new PlayerSession();
            }
            else
            {
                playerSession = (IPlayerSession)ClassUtils.createObject(this.sessionClass);
            }
            synchronized (BasicNettyHandler.class)
            {
                long id = BasicNettyHandler.sessionId++;
            }
            playerSession.setId(id);
        }
        else
        {
            logger.info(id + "已经有session了");
        }
        //绑定通道对象
        playerSession.setChannel(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelInactive ctx：" + ctx);
        playerLogout(ctx);
        super.channelInactive(ctx);
    }

    protected void closeConnection(ChannelHandlerContext ctx) throws Exception {
        logger.info("closeConnection ctx：" + ctx);
        playerLogout(ctx);
    }

    //玩家下线
    protected void playerLogout(ChannelHandlerContext ctx)
    {
        //todo 玩家下线
        try {
            IPlayer player = playerSession.getPlayer();
            //判断一下session是否一样的(有可能是新进player了，所以sesion不一样，不需要处理)
            if(player != null && player.getSession() == playerSession)
            {
                logger.info(player.getName() + "与服务器断开链接了");
                player.logout();
            }
            else
            {
                logger.info("playerLogout事件响应，但是session不一样，这个是老session断开，不做处理");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ctx.channel().isActive()) {
                ctx.channel().close();
            }
            ctx.close();
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception
    {
        logger.info("channelUnregistered ctx：" + ctx);
        //顶号处理
        super.channelUnregistered(ctx);
    }
}

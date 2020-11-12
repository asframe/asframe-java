package com.asframe.game;

import com.asframe.game.server.WssServer;
import com.asframe.game.server.handler.BasicNettyHandler;
import com.asframe.game.thread.GameExecutorService;
import com.asframe.thread.EventScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 游戏的基类，所有的游戏Main入口都继承这个类
 * @author sodaChen
 * @date 2018-10-1
 */
public abstract class BasicGameMain implements IServerShutdownHook
{
    private Logger logger = LoggerFactory.getLogger(BasicGameMain.class);
    /**
     * 唯一实例
     */
    protected static BasicGameMain instance;
//    /**
//     * 配置的根路径
//     */
//    protected String rootPath;

    protected WssServer wsServer;

    /**
     * 启动服务器
     */
    public void startTcpServer(int serverPort,Class<? extends BasicNettyHandler> wsHandlerClass)
    {
        //后面参数要改成外面传递进来
        //初始化线程池
        GameExecutorService.getInstance().setExecutorLimit(1);
        GameExecutorService.getInstance().setReportExecutorLimit(1);
        GameExecutorService.getInstance().init();
        //初始化事件调度器
        EventScheduler.getInstance().setExecutorLimit(1);
        EventScheduler.getInstance().init();
        EventScheduler.getInstance().start();


        //临时变量
        BasicGameMain gameMain = this;
        //钩子 在jvm关闭之前做的工作
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                logger.info("ShutdownHook run()");
                try {
                    gameMain.shutdownHook();
                }
                catch (Exception e)
                {
                    logger.error("执行回调出现异常",e);
                }
                logger.info("ShutdownHook over");
            }
        });

        try {
            this.onTcpServerBegin();
        }
        catch (Exception e)
        {
            logger.error("onTcpServerBegin启动发现异常",e);
        }

        //初始化ws的socket服务器
        logger.info("启动ws服务");
        wsServer = new WssServer();
        try {
//            if(AppConfig.isWss)
//            {
//                wsServer.initSSLContext(AppConfig.Jks_Password,AppConfig.Jks_Path);
//            }
            wsServer.init(serverPort, wsHandlerClass);
        }
        catch (Exception e)
        {
            logger.error("wsServer启动发现异常",e);
        }
        try {
            this.onTcpServerEnd();
        }
        catch (Exception e)
        {
            logger.error("onTcpServerEnd启动发现异常",e);
        }

        logger.info("服务器的相关组件全部启动完毕");
    }

    /**
     * 启动即时通讯服务器之前
     */
    public void onTcpServerBegin()
    {

    }
    /**
     * 启动即时通讯服务器之后
     */
    public void onTcpServerEnd()
    {

    }

     /**
      * 关闭回调
      */
     @Override
     public void shutdownHook()
     {
         //关闭一些能够关闭的东西
         GameExecutorService.getInstance().shutdown();
         EventScheduler.getInstance().shutdown();
         try {
             this.onShutdownHook();
         }
         catch(Exception e)
         {
             logger.error("onShutdownHook发现异常",e);
         }
         this.wsServer.close();
     }

    /**
     * 子类必须扩展
     */
    protected abstract void onShutdownHook();
}

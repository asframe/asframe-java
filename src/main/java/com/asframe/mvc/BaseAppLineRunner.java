package com.asframe.mvc;

import com.asframe.server.ServerSession;
import com.asframe.thread.BackThreadServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
/**
 * 基础的程序启动对象，实现了redis的名字的通用配置
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/10/7
 */
public class BaseAppLineRunner implements CommandLineRunner
{
    private static Logger logger = LogManager.getLogger(BaseAppLineRunner.class);



    @Override
    public void run(String... args) throws Exception
    {
        logger.info("================Spring Boot 基础全部启动完成，初始化系统默认机制=====================");
        //初始化线程池
        BackThreadServices.init();
        this.onRun(args);
        //关闭事件机制的注册
        ServerSession.events.closOn();
    }

    protected void onRun(String[] args) throws Exception
    {
    }
}

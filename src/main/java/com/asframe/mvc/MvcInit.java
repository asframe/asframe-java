/**
 * @ServerInit.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/22
 */
package com.asframe.mvc;

import com.asframe.server.IServerContext;
import com.asframe.mvc.cmd.HttpJsonCmdProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * mvc服务初始化机制
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/10/7
 */
public class MvcInit
{
    private static Logger logger = LogManager.getLogger(MvcInit.class);
    /**
     * 基于返回json结果的cmd指令处理器
     */
    private HttpJsonCmdProcessor httpJsonCmdProcessor;

    /**
     * 获取到http处理的解析器
     * @return
     */
    public HttpJsonCmdProcessor getHttpJsonCmdProcessor()
    {
        return this.httpJsonCmdProcessor;
    }

    /**
     * mvc框架的基础初始化
     * @param serverContext
     */
    public void init(IServerContext serverContext)
    {
        httpJsonCmdProcessor = new HttpJsonCmdProcessor();
        httpJsonCmdProcessor.setServerContext(serverContext);
        logger.info("MvcInit初始化完毕");
    }
}

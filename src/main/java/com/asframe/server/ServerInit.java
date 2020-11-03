/**
 * @ServerInit.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/22
 */
package com.asframe.server;

import com.asframe.server.cmd.ICmd;
import com.asframe.server.module.IModule;
import com.asframe.server.module.db.IModuleDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务器出来了机制
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/2/12
 */
public class ServerInit
{
    private static Logger logger = LoggerFactory.getLogger(ServerInit.class);
    private IServerContext serverContext;

    private static List<ICmd> cmdList = new ArrayList<>();
    private static List<IModule> moduleList = new ArrayList<>();
    private static List<IModuleDB> moduleDbList = new ArrayList<>();

    /**
     * 对模块、模块db以及对应的cmd进行自动绑定
     * @param serverContext
     */
    public void init(IServerContext serverContext)
    {
        logger.info("开始初始化Module、ModuleDB以及Cmds");
        this.serverContext = serverContext;
        //自动注入相关属性
        moduleList.forEach( module -> serverContext.registerModule(module));
        moduleDbList.forEach( moduleDB -> serverContext.registerModuleDB(moduleDB));
        cmdList.forEach( cmd -> serverContext.registerCmd(cmd));
        logger.info("Module、ModuleDB以及Cmds初始化完毕");
    }

    /**
     * 添加cmd实例
     * @param cmd
     */
    public static void addCmd(ICmd cmd)
    {
        cmdList.add(cmd);
    }

    /**
     * 添加模块对象
     * @param module
     */
    public static void addModule(IModule module)
    {
        moduleList.add(module);
    }

    /**
     * 添加模块的db对象
     * @param moduleDB
     */
    public static void addModule(IModuleDB moduleDB)
    {
        moduleDbList.add(moduleDB);
    }
}

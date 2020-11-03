/**
 * @IServerContext.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/24
 */
package com.asframe.server;

import com.asframe.server.cmd.ICmd;
import com.asframe.server.module.IModule;
import com.asframe.server.module.db.IModuleDB;
import com.asframe.server.module.helper.IModuleHelper;

/**
 * 服务端框架的容器对象，所有基于这个服务端框架的机制，内部行走的都是基于这个接口的服务容器对象
 * （类似于SpringContext）
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/2/13
 */
public interface IServerContext {
    /**
     * 注册模块对象
     * @param module
     */
    void registerModule(IModule module);

    /**
     * 注册模块db对象
     * @param moduleDB
     */
    void registerModuleDB(IModuleDB moduleDB);

    /**
     * 注册指令处理对象
     * @param cmd
     */
    void registerCmd(ICmd cmd);
    /**
     * 注册指令处理对象
     * @param helpers
     */
    void registerHelpers(short moduleId, IModuleHelper[] helpers);

    /**
     * 根据请求id获取到对应的cmd对象
     * @param reqCmd
     * @return
     */
    ICmd getCmd(short reqCmd);

    /**
     * 获取到模块对象
     * @param moduleId
     * @return
     */
    IModule getModule(short moduleId);

    /**
     * 开始初始化框架
     */
    void start();
}

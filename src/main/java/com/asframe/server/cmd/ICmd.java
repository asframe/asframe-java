/**
 * @IModule.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:TribeGameServer
 * <br>Date:2018-10-1
 */
package com.asframe.server.cmd;

import com.asframe.server.IEntrance;
import com.asframe.server.module.IModule;
import com.asframe.server.verify.IServerVerify;

/**
 * 指令处理对象，专门处理客户端提交过来的指令信息
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public interface ICmd<M extends IModule,S,REQ,RES>  extends IEntrance, IServerVerify<REQ>
{
    /**
     * cmd初始化方法
     * 服务器启动好之后，框架会自动调用这个方法
     */
    void init();
    /**
     * 设置命令绑定的模块对象
     * @param module
     */
    void setModule(M module);
    /**
     * 获取到这个指令的模块对象
     * @return
     */
    M getModule();

    /**
     * 获取这个cmd的错误码编号
     * @return
     */
    short getErrorCode();

    /**
     * 处理命令
     * @param request
     * @param response 服务器返回数据结构对象
     * @param session
     * @return 返回操作指令的状态
     * @throws Throwable 底层抛出的异常
     */
    short handleCmd(S session, REQ request, RES response) throws Throwable;

    /**
     * 返回所属的模块ID
     * @return
     */
    short getModuleId();

    /**
     * 客户端请求指令
     * @return
     */
    short getReqCmd();

    /**
     * 服务端返回指令
     * @return
     */
    short getResCmd();
    /**
     * 主要给子类重写
     * @param request
     * @return 返回编码
     * @throws Throwable 底层抛出的异常
     */
    short onVerify(REQ request);
}

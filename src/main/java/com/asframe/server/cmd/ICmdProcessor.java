/**
 * @ICmdProcessor.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/11
 */
package com.asframe.server.cmd;

import com.asframe.server.IServerContext;
import com.asframe.server.filter.ICmdRequestFilter;
import com.asframe.server.filter.ICmdResponseFilter;
import com.asframe.server.protocol.IErrorResponse;
import com.asframe.server.protocol.IProtocol;

import java.util.Map;

/**
 * Cmd指令处理器，集中处理所有的前端过来的cmd指令
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/2/11
 */
public interface ICmdProcessor<R,S> {
//    /**
//     * 注册功能模块信息（手动注册）
//     * @param module
//     */
//    void regModule(IModule module);
//
//    /**
//     * 注册cmd，工具生成注册信息
//     * @param cmd
//     */
//    void regCmd(ICmd cmd);

    /**
     * 设置Cmd过滤器
     * @param cmdFilter
     */
    void setCmdFilter(ICmdRequestFilter cmdFilter);
    /**
     * 设置Cmd过滤器
     * @param cmdResponseFilter
     */
    void setCmdResponseFilter(ICmdResponseFilter cmdResponseFilter);
    /**
     * 设置错误消息配置集合对象
     * @param errorMap
     */
    void setErrorMap(Map<Short,String> errorMap);
    /**
     * 设置一个服务端的内容服务中心
     * @param serverContext
     */
    void setServerContext(IServerContext serverContext);
    /**
     * 注册客户端请求的 Request Class，工具生成注册信息
     * @param reqCmd
     * @param reqClass
     */
    void regRequest(short reqCmd, Class reqClass);

    /**
     * 注册服务端发送的 Response Class，工具生成注册信息
     * @param cmd
     * @param resClass
     */
    void regResponse(short cmd, Class resClass);

    /**
     * 创建一个服务器返回对象
     * @param resCmd
     * @return
     */
    IProtocol createResponse(short resCmd);
    /**
     * 创建一个服务器返回对象
     * @param reqCmd
     * @return
     */
    IProtocol createRequest(short reqCmd);
    /**
     * 发送统一的错误消息
     * @param session
     * @param response 封装了错误相关信息对象
     * @return
     */
    R sendErrorResult(S session, IErrorResponse response);

    /**
     * 发送正确的处理结果
     * @param session
     * @param resCmd
     * @param response
     * @return
     */
    R sendSuccessResult(S session, short resCmd,IProtocol response);

    /**
     * 发送不具备任何结果的操作
     * @param session
     * @return
     */
    R sendNoHandler(S session);
    /**
     * 发送不具备任何结果的操作
     * @param session
     * @param reqCmd
     * @return
     */
    R sendNoOpen(S session, short reqCmd);

    /**
     * 向服务端内部派发一个IRequest数据，会转派到对应的cmd进行处理
     * @param reqCmd 前端请求指令
     * @param session 扩展对象
     * @param request 请求对象
     * @return 返回处理结果
     */
    R dispatch(short reqCmd, S session, IProtocol request);

}

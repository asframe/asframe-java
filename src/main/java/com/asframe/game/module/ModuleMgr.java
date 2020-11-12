/**
 * @IModule.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFrame
 * <br>Date:2018-10-1
 */
package com.asframe.game.module;

import com.asframe.game.protocol.read.ITcpRequest;
import com.asframe.game.session.IPlayerSession;
import com.asframe.server.IServerContext;
import com.asframe.server.cmd.ICmdProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * 游戏中的功能模块接口，把游戏分成一个个模块，每个模块都有不同的指令
 * 这个控制器目前有点鸡肋，后面会考虑优化掉。派发cmd通过cmdProcessor来实现。多一层代理转发，意义不是很大
 * 后面优化可以把cmdProcessor传入到底层，直接由底层来进行转发，提升性能
 * @author sodaChen
 * @date 2020-10-1
 *
 */
public class ModuleMgr {
    private static Log logger = LogFactory.getLog(ModuleMgr.class);

    public static ICmdProcessor<Short,IPlayerSession> cmdProcessor = new TcpCmdProcessor();
//    /**
//     * 注册功能模块信息（手动注册）
//     * @param module
//     */
//    public static void regModule(IModule module)
//    {
//        cmdProcessor.regModule(module);
//    }
//
//    /**
//     * 注册cmd，工具生成注册信息
//     * @param cmd
//     */
//    public static void regCmd(ITcpCmd cmd)
//    {
//        cmdProcessor.regCmd(cmd);
//    }

//    /**
//     * 注册一个cmd
//     * @param code
//     * @param cmdClass
//     */
//    public static void regCmd(short code,Class<?> cmdClass)
//    {
//        regCmd(code, (ITcpCmd) ClassUtils.createObject(cmdClass));
//    }

//    /**
//     * 注册cmd，工具生成注册信息
//     * @param cmd
//     */
//    public static void regCmd(short code, ITcpCmd cmd)
//    {
//        cmd.initCmd(code);
//        cmdProcessor.regCmd(cmd);
//    }


    /**
     * 注册客户端请求的 Request Class，工具生成注册信息
     * @param reqClas
     */
    public static void regRequest(short reqCmd,Class reqClas)
    {
        cmdProcessor.regRequest(reqCmd,reqClas);
    }

    /**
     * 注册服务端发送的 Response Class，工具生成注册信息
     * @param resCmd
     * @param resClass
     */
    public static void regResponse(short resCmd,Class resClass)
    {
        cmdProcessor.regResponse(resCmd,resClass);
    }

    /**
     * 初始化模块管理
     * @param serverContext
     */
    public static void init(IServerContext serverContext, Map<Short,String> errorMsgMap)
    {
        cmdProcessor.setServerContext(serverContext);
        cmdProcessor.setErrorMap(errorMsgMap);
    }
    /**
     * 向服务端内部派发一个IRequest数据，会转派到对应的cmd进行处理
     * @param request
     */
    public static void dispatch(IPlayerSession session, short reqCmd,ITcpRequest request)
    {
        cmdProcessor.dispatch(reqCmd,session,request);
    }

//    public static ITcpResponse getResponse(short resCmd)
//    {
//        return cmdProcessor.createResponse(resCmd);
//    }

    /**
     * 返回前端请求的数据结构对象
     * @param reqCmd
     * @return
     */
    public static ITcpRequest getRequest(short reqCmd)
    {
        return (ITcpRequest)cmdProcessor.createRequest(reqCmd);
    }
}

/**
 * @CmdProcessor.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/11
 */
package com.asframe.server.cmd;

import com.asframe.server.IServerContext;
import com.asframe.server.ServerConfig;
import com.asframe.server.ServerConst;
import com.asframe.server.exception.ErrorMessageException;
import com.asframe.server.filter.ICmdRequestFilter;
import com.asframe.server.filter.ICmdResponseFilter;
import com.asframe.server.module.IModule;
import com.asframe.server.protocol.ErrorResponse;
import com.asframe.server.protocol.IProtocol;
import com.asframe.utils.ErrorLogUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * cmd处理器，负责处理每个cmd的请求
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/2/06
 */
public abstract class BasicCmdProcessor<R,S> implements ICmdProcessor<R,S>
{
    private static Logger logger	            = LogManager.getLogger(BasicCmdProcessor.class);

    /**
     * 前端对象集合
     */
    private Map<Short,Class>                    requestMap;
    /**
     * 服务器返回对象集合
     */
    protected Map<Short,Class>                  responseMap;
    /**
     * 错误码集合对象
     */
    protected Map<Short,String>                 errorMap;
    /**
     * 服务容器上下文
     */
    protected IServerContext                    serverContext;
    /**
     * cmd过滤器
     */
    protected ICmdRequestFilter<IProtocol,S> cmdFilter;
    /**
     * 服务器返回数据处理过滤器
     */
    protected ICmdResponseFilter<IProtocol,S>   cmdResponseFilter;

    public BasicCmdProcessor()
    {
        //注意，非线程安全，所有的请求和返回对象必须在服务器启动的时候进行注册
        this.responseMap = new HashMap<>();
        this.requestMap = new HashMap<>();
    }

    /**
     * 设置Cmd过滤器
     * @param cmdFilter
     */
    @Override
    public void setCmdFilter(ICmdRequestFilter cmdFilter)
    {
        this.cmdFilter = cmdFilter;
    }
    /**
     * 设置Cmd过滤器
     * @param cmdResponseFilter
     */
    @Override
    public void setCmdResponseFilter(ICmdResponseFilter cmdResponseFilter)
    {
        this.cmdResponseFilter = cmdResponseFilter;
    }


    /**
     * 设置错误消息配置集合对象
     * @param errorMap
     */
    @Override
    public void setErrorMap(Map<Short,String> errorMap)
    {
        this.errorMap = errorMap;
    }

    @Override
    public void setServerContext(IServerContext serverContext)
    {
        this.serverContext = serverContext;
    }

    /**
     * 注册客户端请求的 Request Class，工具生成注册信息
     * @param reqCmd
     * @param reqClass
     */
    @Override
    public void regRequest(short reqCmd,Class reqClass)
    {
        this.requestMap.put(reqCmd,reqClass);
    }

    /**
     * 注册服务端发送的 Response Class，工具生成注册信息
     * @param cmd
     * @param resClass
     */
    @Override
    public void regResponse(short cmd,Class resClass)
    {
        this.responseMap.put(cmd,resClass);
    }

    @Override
    public IProtocol createResponse(short resCmd)
    {
        Class<?> clazz = this.responseMap.get(resCmd);
        return createProtocol(clazz);
    }
    @Override
    public IProtocol createRequest(short reqCmd)
    {
        Class<?> clazz = this.requestMap.get(reqCmd);
        return createProtocol(clazz);
    }

    private IProtocol createProtocol(Class clazz) {
        try {
            return (IProtocol)clazz.newInstance();
        }
        catch (Exception e)
        {
            logger.error(clazz + "返回指令没有注册对应的IProtocol");
        }
        return null;
    }

    /**
     * 向服务端内部派发一个IRequest数据，会转派到对应的cmd进行处理
     * @param request 前端请求参数对象
     */
    @Override
    public R dispatch(short reqCmd,S session, IProtocol request)
    {
        ICmd cmd = this.serverContext.getCmd(reqCmd);
        if(cmd == null)
        {
            throw new Error(reqCmd+ "没有配置对应的cmd处理器");
        }
        //模块用得少，考虑性能的话，可以去掉这个判断，改为cmd的基类自己判断
        IModule module = cmd.getModule();
        //判断没有模块或者模块没有开启的情况
        if(module == null || module.isOpenFlag())
        {
            //一般不用判断指令是否开启，之后再优化
            long time = System.currentTimeMillis();
            //结果编码
            short resultCode ;
            //过滤器执行
            if(this.cmdFilter != null)
            {
                try {
                    resultCode = this.cmdFilter.requestFilter(session,reqCmd,request);
                } catch (Throwable e)
                {
                    logger.error(ErrorLogUtil.errorTrackSpaceString(e));
                    resultCode = ServerConst.Cmd_Filter_Error;
                }
                if(resultCode != ServerConst.Success)
                {
                    return flushError(session,cmd.getResCmd(),resultCode);
                }
            }

            //服务器返回指令
            short resCmd = cmd.getResCmd();
            //校验cmd的请求参数
            try {
                resultCode = cmd.isVerify(request);
            } catch (Throwable e)
            {
                logger.error(ErrorLogUtil.errorTrackSpaceString(e));
                resultCode = ServerConst.Verify_Error;
            }
            if(resultCode != ServerConst.Success)
            {
                return flushError(session,resCmd,resultCode);
            }
            //创建一个服务器返回对象
            IProtocol response = this.createResponse(resCmd);
            try
            {
                resultCode = cmd.handleCmd(session,request,response);
            }
            catch (Throwable e)
            {
                logger.error(ErrorLogUtil.errorTrackSpaceString(e));
                if(e instanceof ErrorMessageException)
                {
                    ErrorMessageException messageException = (ErrorMessageException)e;
                    return flushError(session,resCmd,messageException.getCode(),messageException.getErrorMsg());
                }
                resultCode = cmd.getErrorCode();
            }
            time = System.currentTimeMillis() - time;
            if(time > ServerConfig.CmdProcessorMaxTime)
            {
                if(time > ServerConfig.CmdProcessorWarnTime)
                {
                    logger.warn(reqCmd + "处理超过" + ServerConfig.CmdProcessorWarnTime + "毫秒:" + time);
                }
                else
                {
                    logger.info(reqCmd + "处理超过" + ServerConfig.CmdProcessorMaxTime + "毫秒:" + time);
                }
            }
            if(resultCode == ServerConst.Success)
            {
                response.setCode(resultCode);
                //判断一下有没有结果过滤器，有的话，进行结果过滤器过滤
                if(this.cmdResponseFilter != null)
                {
                    try
                    {
                        this.cmdResponseFilter.responseFilter(session,resCmd,response);
                    }
                    catch (Throwable e)
                    {
                        logger.error(ErrorLogUtil.errorTrackSpaceString(e));
                    }
                }
                return this.sendSuccessResult(session,resCmd,response);
            }
            //发送无操作结果
            if(resultCode == ServerConst.No_Handle)
            {
               return this.sendNoHandler(session);
            }
            //发送错误处理机制
            return flushError(session,resCmd,resultCode);
        }
        return this.sendNoOpen(session,reqCmd);
    }

    protected R flushError(S session, short resCmd,short errorCode)
    {
        return this.flushError(session,resCmd,errorCode,null);
    }
    /**
     * 刷新自定义的错误消息返回给前端
     * @param session
     * @param resCmd 服务器返回指令
     * @param errorCode 错误码
     * @return
     */
    protected R flushError(S session, short resCmd,short errorCode,String errorMsg)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(errorCode);
        errorResponse.setCmd(resCmd);
        //如果有错误文本，则也发送给前端
        if(errorMsg == null)
        {
            errorMsg = this.errorMap.get(errorCode);
        }
        if(errorMsg != null)
        {
            errorResponse.setError(errorMsg);
        }
        //打印一下这个错误日志，方便开发者看到
        logger.info("服务器处理消息错误码 resCmd：" + resCmd + " errorCode:" + errorCode + " errorMsg:" + errorMsg);
        return this.sendErrorResult(session,errorResponse);
    }
}

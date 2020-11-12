/**
 * @TCPCmdProcessor.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/11
 */
package com.asframe.game.module;

import com.asframe.game.protocol.TcpErrorResponse;
import com.asframe.game.session.IPlayerSession;
import com.asframe.server.cmd.BasicCmdProcessor;
import com.asframe.server.protocol.IErrorResponse;
import com.asframe.server.protocol.IProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 基于TCP即时通信的cmd处理机制
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/10/10
 */
public class TcpCmdProcessor extends BasicCmdProcessor<Short,IPlayerSession> {

    private static Logger                               logger	= LoggerFactory.getLogger(TcpCmdProcessor.class);


    public TcpCmdProcessor()
    {
        this.responseMap = new HashMap<>();
    }

    /**
     * 刷新自定义的错误消息返回给前端
     * @param session
     * @param resCmd 服务器返回指令
     * @param errorCode 错误码
     * @return
     */
    @Override
    protected Short flushError(IPlayerSession session, short resCmd,short errorCode,String errorMsg)
    {
        TcpErrorResponse errorResponse = new TcpErrorResponse();
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
        else
        {
            errorResponse.setError("未知错误");
        }
        //打印一下这个错误日志，方便开发者看到
        logger.info("服务器处理消息错误码 resCmd：" + resCmd + " errorCode:" + errorCode + " errorMsg:" + errorMsg);
        return this.sendErrorResult(session,errorResponse);
    }

    /**
     * 发送统一的错误消息
     * @param session
     * @param response
     * @return
     */
    @Override
    public Short sendErrorResult(IPlayerSession session, IErrorResponse response)
    {
        session.sendMessage(response);
        return 0;
    }

    /**
     * 发送正确的处理结果
     * @param session
     * @param response
     * @return
     */
    @Override
    public Short sendSuccessResult(IPlayerSession session, short resCmd, IProtocol response)
    {
        session.sendMessage(resCmd,response);
        return 0;
    }

    /**
     * 发送不具备任何结果的操作
     * @param session
     * @return
     */
    @Override
    public Short sendNoHandler(IPlayerSession session)
    {
        return 0;
    }
    /**
     * 发送不具备任何结果的操作
     * @param session
     * @param reqCmd
     * @return
     */
    @Override
    public Short sendNoOpen(IPlayerSession session,short reqCmd)
    {
        return 0;
    }
}

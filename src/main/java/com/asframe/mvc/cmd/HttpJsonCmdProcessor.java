/**
 * @TCPCmdProcessor.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/11
 */
package com.asframe.mvc.cmd;

import com.asframe.mvc.MVCConst;
import com.asframe.mvc.protocol.HttpResponseResult;
import com.asframe.mvc.session.IMvcSession;
import com.asframe.server.cmd.BasicCmdProcessor;
import com.asframe.server.protocol.IErrorResponse;
import com.asframe.server.protocol.IProtocol;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * 基于TCP即时通信的cmd处理机制
 *
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/2/11
 */
public class HttpJsonCmdProcessor extends BasicCmdProcessor<String, IMvcSession> {

    private static Logger logger = LogManager.getLogger(HttpJsonCmdProcessor.class);
    /**
     * json转换对象
     */
    private Gson gson;

    public HttpJsonCmdProcessor() {
        this.responseMap = new HashMap<>();
        this.gson = new Gson();
        logger.info("HttpJsonCmdProcessor创建成功");
    }


    /**
     * 发送统一的错误消息
     *
     * @param session
     * @param response
     * @return
     */
    @Override
    public String sendErrorResult(IMvcSession session, IErrorResponse response) {
        return this.gson.toJson(response);
    }

    /**
     * 发送正确的处理结果
     *
     * @param session
     * @param response
     * @return
     */
    @Override
    public String sendSuccessResult(IMvcSession session, short resCmd,IProtocol response) {
        HttpResponseResult responseResult = new HttpResponseResult();
        responseResult.setCmd(resCmd);
        responseResult.setCode((short) 0);
        responseResult.setData(response);
        return this.gson.toJson(responseResult);
    }

    /**
     * 发送不具备任何结果的操作
     *
     * @param session
     * @return
     */
    @Override
    public String sendNoHandler(IMvcSession session) {
        //http不会有没有操作的实现
        throw new Error("http不允许有没有操作的实现");
    }

    /**
     * 发送不具备任何结果的操作
     *
     * @param session
     * @param reqCmd
     * @return
     */
    @Override
    public String sendNoOpen(IMvcSession session, short reqCmd) {
        return this.flushError(session,reqCmd, MVCConst.No_Handle);
    }
}

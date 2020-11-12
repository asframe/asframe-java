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
package com.asframe.game.module;

import com.asframe.game.protocol.read.ITcpRequest;
import com.asframe.game.protocol.write.ITcpResponse;
import com.asframe.game.session.IPlayerSession;
import com.asframe.server.cmd.BasicCmd;
import com.asframe.server.module.IModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 游戏中指令处理对象，专门处理客户端提交过来的指令信息
 * 目前这个主要是拿来给子类继承的，确定一些泛型的实际对象和限制
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public abstract class BasicTcpCmd<M extends IModule,REQ extends ITcpRequest,RES extends ITcpResponse>  extends BasicCmd<M,IPlayerSession,REQ,RES> implements ITcpCmd<M,IPlayerSession,REQ,RES>
{
    public static Logger logger = LoggerFactory.getLogger(BasicTcpCmd.class);

    /**
     * 处理命令
     * @param session
     * @param request
     * @param response
     * @return
     */
    @Override
    public abstract short handleCmd(IPlayerSession session, REQ request, RES response) throws Throwable;
}

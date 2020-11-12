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

import com.asframe.game.protocol.write.ITcpResponse;
import com.asframe.game.protocol.read.ITcpRequest;
import com.asframe.game.session.IPlayerSession;
import com.asframe.server.cmd.ICmd;
import com.asframe.server.module.IModule;

/**
 * 游戏中指令处理对象，专门处理客户端提交过来的指令信息
 * @author sodaChen
 * @date 2020-10-1
 *
 */
public interface ITcpCmd<M extends IModule,S extends IPlayerSession,REQ extends ITcpRequest,RES extends ITcpResponse>  extends ICmd<M,S,REQ,RES>
{
}

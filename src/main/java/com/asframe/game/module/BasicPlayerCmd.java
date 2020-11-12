/**
 * @BasicPlayerCmd.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/11
 */
package com.asframe.game.module;

import com.asframe.game.protocol.read.ITcpRequest;
import com.asframe.game.protocol.write.ITcpResponse;
import com.asframe.game.role.IPlayer;
import com.asframe.game.session.IPlayerSession;
import com.asframe.server.ServerConst;
import com.asframe.server.module.IModule;

/**
 * 专门处理玩家指令的基础cmd对象
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/10/10
 */
public abstract class BasicPlayerCmd<P extends IPlayer,M extends IModule,REQ extends ITcpRequest,RES extends ITcpResponse>  extends BasicTcpCmd<M,REQ,RES> {
    /**
     * 处理命令
     * @param request
     */
    @Override
    public short handleCmd(IPlayerSession session, REQ request, RES response) throws Throwable
    {
        if(session.getPlayer() == null)
        {
            return ServerConst.Player_Not_Login;
        }
        return this.handlePlayerCmd((P)session.getPlayer(),request,response);
    }

    /**
     * 處理玩家指令
     * @param player
     * @param request
     * @param response
     * @return
     */
    public abstract short handlePlayerCmd(P player,REQ request, RES response) throws Throwable;
}

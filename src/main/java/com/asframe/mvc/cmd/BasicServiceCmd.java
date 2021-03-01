/**
 * @IModule.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:TribeGameServer
 * <br>Date:2018-10-1
 */
package com.asframe.mvc.cmd;

import com.asframe.mvc.protocol.read.IHttpRequest;
import com.asframe.mvc.protocol.write.IHttpResponse;
import com.asframe.mvc.session.IMvcSession;
import com.asframe.server.cmd.BasicCmd;
import com.asframe.server.module.IModule;

/**
 * 基于Spring boot service服务器的cmd处理对象
 *
 * @author sodaChen
 * @date 2018-10-1
 */
public abstract class BasicServiceCmd<M extends IModule, S extends IMvcSession, REQ extends IHttpRequest, RES extends IHttpResponse> extends BasicCmd<M, S, REQ, RES> implements IServicesCmd<M, S, REQ, RES> {
}

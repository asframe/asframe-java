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
import com.asframe.server.cmd.ICmd;
import com.asframe.server.module.IModule;

/**
 * 在spring web中承担services的cmd指令处理对象接口
 *
 * @author sodaChen
 * @date 2018-10-1
 */
public interface IServicesCmd<M extends IModule, S extends IMvcSession, REQ extends IHttpRequest, RES extends IHttpResponse> extends ICmd<M, S, REQ, RES> {
}

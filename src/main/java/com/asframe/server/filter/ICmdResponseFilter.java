/**
 * @IServerFilter.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/25
 */
package com.asframe.server.filter;

import com.asframe.server.protocol.IProtocol;

/**
 * cmd的结果过滤器，在cmd发送Response结果之前之前，会调用相关的过滤器。这个是每个cmd单独享有的
 * 也可以多个cmd享有同样的过滤器
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/2/25
 */
public interface ICmdResponseFilter<T extends IProtocol,S> {
    /**
     * 请求过滤器
     * @param session session对象
     * @param resCmd 服务器返回指令
     * @param response 请求对象
     * @return
     * @throws Throwable 抛出的异常
     */
    short responseFilter(S session,short resCmd,T response) throws Throwable;
}

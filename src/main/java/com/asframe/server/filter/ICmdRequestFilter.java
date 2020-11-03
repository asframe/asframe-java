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
 * cmd的过滤器，在调用cmd之前，会调用相关的过滤器。这个是每个cmd单独享有的
 * 也可以多个cmd享有同样的过滤器
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/2/25
 */
public interface ICmdRequestFilter<T extends IProtocol,S> {
    /**
     * 请求过滤器
     * @param request 请求对象
     * @return
     * @throws Throwable 抛出的异常
     */
    short requestFilter(S session,short resCmd,T request) throws Throwable;
}

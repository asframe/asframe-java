/**
 * @IServerVerify.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/11
 */
package com.asframe.server.verify;

/**
 * 服务端验证接口
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/2/11
 */
public interface IServerVerify<REQ> {
    /**
     * 验证方法
     * @param request 需要验证的数据结构对象
     * @return
     */
    short isVerify(REQ request);
}

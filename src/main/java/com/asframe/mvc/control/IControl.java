/**
 * @IControl.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2019/11/23
 */
package com.asframe.mvc.control;

import com.asframe.mvc.session.IMvcSession;
import com.asframe.server.cmd.ICmdProcessor;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器接口
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2019/10/23
 */
public interface IControl<S extends IMvcSession> {
    /**
     * 初始化session对象
     *
     * @param httpRequest
     * @return
     */
    S initSession(HttpServletRequest httpRequest);

    /**
     * 对控制器设置指令控制器和Session的Class对象
     *
     * @param cmdProcessor
     * @param sessionClass
     */
    void initControl(ICmdProcessor cmdProcessor, Class<?> sessionClass);

    /**
     * 通用执行前端请求
     *
     * @param servletRequest servlet请求对象
     * @param request        前端请求数据结构对象
     * @return
     */
    String execute(HttpServletRequest servletRequest, IHttpRequest request);
}

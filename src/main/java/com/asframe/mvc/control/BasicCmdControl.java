/**
 * @BasicCmdControl.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/25
 */
package com.asframe.mvc.control;

import com.asframe.mvc.MvcGlobal;
import com.asframe.mvc.protocol.read.IHttpRequest;
import com.asframe.mvc.session.IMvcSession;
import com.asframe.server.cmd.ICmdProcessor;
import com.asframe.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础的cmd控制器对象
 *
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/2/25
 */
public class BasicCmdControl<S extends IMvcSession> implements IControl<S> {
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(BasicCmdControl.class);
    /**
     * 生成session的class对象
     */
    protected Class<?> sessionClass;
    /**
     * 命令处理器
     */
    protected ICmdProcessor<String, IMvcSession> cmdProcessor;

    public BasicCmdControl()
    {
        MvcGlobal.addControl(this);
    }

    /**
     * 初始化一次业务处理的session对象，对HttpRequest和HTTPSession进行封装
     gfgg @param httpRequest
     */
    @Override
    public S initSession(HttpServletRequest httpRequest) {
        S session = (S) ClassUtils.createObject(this.sessionClass);
        session.init(httpRequest);
        return session;
    }

    /**
     * 对控制器设置指令控制器和Session的Class对象
     *
     * @param cmdProcessor
     * @param sessionClass
     */
    @Override
    public void initControl(ICmdProcessor cmdProcessor, Class<?> sessionClass) {
        this.cmdProcessor = cmdProcessor;
        this.sessionClass = sessionClass;
    }

    /**
     * 通用执行前端请求
     *
     * @param servletRequest servlet请求对象
     * @param request        前端请求数据结构对象
     * @return
     */
    @Override
    public String execute(HttpServletRequest servletRequest, IHttpRequest request) {
        logger.info("前端请求的地址为["+servletRequest.getRequestURI()+"]");
        return this.cmdProcessor.dispatch(request.getCmd(), this.initSession(servletRequest), request);
    }
}

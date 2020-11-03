/**
 * @IMvcSession.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2019/11/23
 */
package com.asframe.mvc.session;

import com.asframe.mvc.protocol.read.BaseHttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 封装了Spring mvc框架的session。从过这个session。通过这个接口在代码层中流通
 * 回头再研究下spring mvc原理。如果是每次都是生成新的对象。那么可以考虑这个来发送数据了
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/10/7
 */
public interface IMvcSession
{
    /**
     * 暂时是封装这个，其实是更加复杂的数据模型才对
     * @param httpRequest
     */
    void init(HttpServletRequest httpRequest);

    /**
     *
     * @return
     */
    HttpServletRequest getHttpRequest();
    HttpSession getHttpSession();

    /**
     * 绑定token和uuid
     * @param token
     * @param uuid
     */
    void setUuid(String token,String uuid);

    /**
     * 获取token
     * @return
     */
    String getToken();
    String getToken(BaseHttpRequest baseRequest);
    /**
     * 返回账号的唯一id
     * @return
     */
    String getUuid();
    String getUuid(BaseHttpRequest baseRequest);
    /**
     * 返回账号的唯一id
     * @return
     */
    String getUuid(String token);

    /**
     * 推出登录
     */
    void loginOut();
}

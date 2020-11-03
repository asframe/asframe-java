package com.asframe.mvc.client;

import com.asframe.mvc.protocol.write.IHttpResponse;

/**
 * 服务器返回Response的处理指令
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/29
 */
public interface IHttpResponseCmd<P, T extends IHttpResponse>
{
    Class getResponseClass();
    /**
     * 设置IResponseCmd对应的IResponse class
     * @param clazz
     */
    void setResponseClass(Class<T> clazz);

    /**
     * 创建一个新的Respone实例
     * @return
     */
    T createResponse();

    /**
     * 操作服务器返回的Respone
     * @param player
     * @param response
     */
    void handleResponse(P player, T response);
}

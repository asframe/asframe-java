package com.asframe.game.client;

import com.asframe.game.protocol.write.ITcpResponse;
import com.asframe.game.role.IPlayer;

/**
 * 服务器返回Response的处理指令
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/10
 */
public interface IResponseCmd<T extends ITcpResponse>
{
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
    void handleResponse(IPlayer player, T response);
}

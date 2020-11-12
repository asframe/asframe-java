package com.asframe.game.client;

import com.asframe.game.protocol.write.ITcpResponse;
import com.asframe.game.role.IPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础的返回处理指令
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/30
 */
public class BasicResponseCmd<T extends ITcpResponse> implements IResponseCmd<T>
{
    private static Logger logger = LoggerFactory.getLogger(BasicResponseCmd.class);
    private Class<T> responseClass;

    @Override
    public void setResponseClass(Class<T> clazz)
    {
        this.responseClass = clazz;
    }

    @Override
    public T createResponse()
    {
        try {
            return responseClass.newInstance();
        }
        catch (Exception e)
        {
            logger.error("createResponse抛出异常",e);
        }
        return null;
    }

    @Override
    public void handleResponse(IPlayer player, T response)
    {

    }
}

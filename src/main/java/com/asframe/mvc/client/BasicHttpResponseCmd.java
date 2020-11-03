package com.asframe.mvc.client;

import com.asframe.mvc.protocol.write.IHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础的返回处理指令
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/30
 */
public class BasicHttpResponseCmd<P,T extends IHttpResponse> implements IHttpResponseCmd<P,T>
{
    private static Logger logger = LoggerFactory.getLogger(BasicHttpResponseCmd.class);
    /**
     * 当前处理指令cmd同时绑定的服务器返回对象
     */
    private Class<T> responseClass;


    public BasicHttpResponseCmd()
    {

    }

    @Override
    public Class getResponseClass()
    {
        return this.responseClass;
    }

    @Override
    public void setResponseClass(Class<T> clazz)
    {
        this.responseClass = clazz;
    }

    @Override
    public synchronized T createResponse()
    {
        try {
            return responseClass.newInstance();
        }
        catch (Exception e)
        {
            logger.error("create Http Response抛出异常",e);
        }
        return null;
    }

    @Override
    public void handleResponse(P player, T response)
    {

    }
}

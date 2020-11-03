package com.asframe.server.protocol;

/**
 * 协议的基础接口，定义了请求和发送的基础结构
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public interface IRequestProtocol extends IProtocol
{
    /**
     * 设置协议对象唯一的通讯指令
     * @param cmd
     */
    void setCmd(short cmd);
    /**
     * 此次发送指令的编码，根据这个编码来决定如何处理发送数据或者发送去向
     * @return
     */
    short getCmd();
}


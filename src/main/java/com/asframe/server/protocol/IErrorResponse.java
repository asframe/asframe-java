/**
 * @IErrorResponse.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/4/4
 */
package com.asframe.server.protocol;

/**
 *
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/10/3
 */
public interface IErrorResponse extends IProtocol {
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
    /**
     * 返回错误的消息字符串
     *
     * @return
     */
    String getError();

    /**
     * 设置错误消息文本内容
     * @param errorMsg
     */
    void setError(String errorMsg);

}

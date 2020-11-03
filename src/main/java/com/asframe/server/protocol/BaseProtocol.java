/**
 * @BasicProtocol.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFrame
 * <br>Date:2018-10-1
 */
package com.asframe.server.protocol;


/**
 * 协议的基础接口，定义了请求和发送的基础结构.主要是包含了一个标识：code属性
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public abstract class BaseProtocol implements IProtocol
{
    /**
     * 标识码
     * request对象时。标识这次请求的唯一指令（cmd）
     * response实现时，0是成功，其他的是错误码
     */
    protected short code;
    /**
     * 此次通讯对象的编码，告诉前端通讯时成功还是失败
     *
     * @param code
     */
    @Override
    public void setCode(short code) {
        this.code = code;
    }

    /**
     * 获取编码结果
     *
     * @return
     */
    @Override
    public short getCode() {
        return this.code;
    }
}

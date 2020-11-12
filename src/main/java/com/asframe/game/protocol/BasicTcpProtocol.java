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
package com.asframe.game.protocol;

import com.asframe.server.protocol.BaseProtocol;

/**
 * 协议的基础接口，定义了请求和发送的基础结构
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public abstract class BasicTcpProtocol extends BaseProtocol implements ITcpProtocol
{
    /**
     * 编码类型
     */
    protected byte codeType;
    protected short code;

    public void setCodeType(byte codeType) {
        this.codeType = codeType;
    }

    /**
     * 获取到协议的编码类型
     * @return
     */
    @Override
    public byte getCodeType()
    {
        return this.codeType;
    }
    /**
     * 恢复，重用协议对象
     */
    @Override
    public abstract void reset();

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

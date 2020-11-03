/**
 * @JsonErrorVo.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2019/11/23
 */
package com.asframe.server.protocol;

/**
 * 统一错误对象
 *
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/10/3
 */
public class ErrorResponse extends BaseProtocol implements IErrorResponse
{
    /**
     * 错误消息
     */
    private String error;

    /**
     * 返回错误的消息字符串
     *
     * @return
     */
    @Override
    public String getError() {
        return error;
    }
    @Override
    public void setError(String errorMsg) {
        this.error = error;
    }

    /**
     * 协议结果编码
     */
    protected short code;

    @Override
    public short getCmd() {
        return cmd;
    }
    @Override
    public void setCmd(short cmd) {
    this.cmd = cmd;
    }
    protected short cmd;
}

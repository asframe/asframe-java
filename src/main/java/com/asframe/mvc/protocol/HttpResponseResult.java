/**
 * @HttpResponseResult.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/4/2
 */
package com.asframe.mvc.protocol;

/**
 * Http请求返回的结果对象
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/10/7
 */
public class HttpResponseResult<T> {

    /**
     * 返回消息的编码  0是成功
     */
    private short code;
    /**
     * 服务器返回的消息指令
     */
    protected short cmd;

    /**
     * 返回前端的具体内容，根据使用环境有不同的变化
     */
    private T data;

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }
}

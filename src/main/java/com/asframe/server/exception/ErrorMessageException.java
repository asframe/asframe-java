/**
 * @ErrorMessageException.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.server.exception;

import com.asframe.server.ServerConst;

/**
 * 给前端的错误消息异常
 * 所以需要传递消息给前端显示的异常都应该集成这个异常
 * 底层机制会捕捉这个异常，然后处理成前端需要的格式
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/12
 */
public class ErrorMessageException extends Exception{
    /**
     * 错误编码
     */
    private short code;
    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 只传输错误码的错误小
     * @param code
     */
    public ErrorMessageException(short code)
    {
        this.code = code;
    }

    /**
     * 只有错误消息的异常，使用默认的错误码
     * @param errorMsg
     */
    public ErrorMessageException(String errorMsg)
    {
        this.code = ServerConst.Message_Exception_Error;
        this.errorMsg =errorMsg;
    }

    /**
     *
     * @param code
     * @param errorMsg
     */
    public ErrorMessageException(short code,String errorMsg)
    {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

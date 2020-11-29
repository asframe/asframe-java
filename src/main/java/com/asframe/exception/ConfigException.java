/**
 * @ConfigException.java
 * @author sodaChen mail:asframe#qq.com
 * @version 1.0
 * <br>Copyright (C), 2012-present ASFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFame
 * <br>Date:2020-10-17
 */
package com.asframe.exception;

/**
 * 服务器配置相关的异常，为了后面的识别和扩展用
 * @author sodaChen
 * Date:2020/11/29
 */
public class ConfigException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ConfigException(String msg) {
        super(msg);
    }

    public ConfigException(Exception e) {
        super(e);
    }

    public ConfigException(String msg, Exception e) {
        super(msg, e);
    }
}

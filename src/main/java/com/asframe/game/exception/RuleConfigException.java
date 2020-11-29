/**
 * @RuleConfigException.java
 * @author sodaChen mail:asframe#qq.com
 * @version 1.0
 * <br>Copyright (C), 2012-present ASFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFame
 * <br>Date:2020-10-17
 */
package com.asframe.game.exception;

import com.asframe.exception.ConfigException;

/**
 * 策划规则配置数据的
 * @author sodaChen
 * Date:2020/11/29
 */
public class RuleConfigException extends ConfigException
{
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * 只包含基础配置数据异常
     * @param tableName 出现错误的标签页名称
     * @param tableId 出现错误的那一条数据的模板id
     * @param errorInfo 具体出错信息，用中文，策划也需要看这个异常信息
     */
    public RuleConfigException(String tableName, int tableId, String errorInfo) {
        super(String.format("[%s][id=%d]%s", tableName, tableId, errorInfo));
    }

    /**
     * 附带报错对象的异常信息
     * @param tableName 出现错误的标签页名称
     * @param tableId 出现错误的那一条数据的模板id
     * @param errorInfo 具体出错信息，用中文，策划也需要看这个异常信息
     * @param e 异常
     */
    public RuleConfigException(String tableName, int tableId, String errorInfo, Exception e) {
        super(String.format("[%s][id=%d]%s", tableName, tableId, errorInfo), e);
    }
}

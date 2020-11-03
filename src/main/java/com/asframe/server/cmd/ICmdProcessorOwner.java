/**
 * @ICmdProcessorOwner.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/3/3
 */
package com.asframe.server.cmd;

/**
 * Cmd指令处理器拥有者
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/3/3
 */
public interface ICmdProcessorOwner
{
    /**
     * 设置cmd指令处理器
     * @param cmdProcessor
     */
    void setCmdProcessor(ICmdProcessor cmdProcessor);
}

/**
 * @BaseCmdProcessorOwner.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/3/3
 */
package com.asframe.server.cmd;

/**
 *
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/3/3
 */
public class AbstractCmdProcessorOwner {
    /**
     * 命令处理器
     */
    protected ICmdProcessor  cmdProcessor;

    /**
     * 设置cmd指令处理器
     * @param cmdProcessor
     */
    public void setCmdProcessor(ICmdProcessor cmdProcessor)
    {
        this.cmdProcessor = cmdProcessor;
    }
}

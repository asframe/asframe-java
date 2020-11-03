/**
 * @ServerConfig.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/24
 */
package com.asframe.server;

/**
 * 服务框架会使用到的相关属性，这个是根据外面配置更新，并非常量
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/2/13
 */
public class ServerConfig
{
    /**
     * Cmd指令处理处理一条指令允许的最大毫秒数
     */
    public static int CmdProcessorMaxTime = 10;
    /**
     * Cmd指令处理处理一条指令的超时警告时间
     */
    public static int CmdProcessorWarnTime = 100;
}

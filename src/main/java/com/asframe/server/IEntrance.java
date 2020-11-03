/**
 * @IModule.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:TribeGameServer
 * <br>Date:2018-10-1
 */
package com.asframe.server;
/**
 * 入口状态,主要作用是用作控制一个开关是否开启（功能、模块、指令）
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public interface IEntrance
{
    /**
     * 是否开启
     * @return
     */
    boolean isOpenFlag();

    /**
     * 设置状态
     * @param openFlag
     */
    void setOpenFlag(boolean openFlag);
}

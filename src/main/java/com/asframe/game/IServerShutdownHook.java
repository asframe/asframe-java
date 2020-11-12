/**
 * @IServerShoutDown.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/12
 */
package com.asframe.game;

/**
 * 服务器关闭回调接口
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/10/12
 */
public interface IServerShutdownHook {
    /**
     * 关闭回调
     */
    void shutdownHook();
}

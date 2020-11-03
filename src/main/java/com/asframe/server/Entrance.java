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

import com.asframe.mode.observer.Subjects;

/**
 * 入口状态
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public class Entrance implements IEntrance
{
    private boolean openFlag;

    /**
     * 全局事件机制
     */
    protected Subjects events;

    public Entrance() {
        this.openFlag = true;
        this.events = ServerSession.events;
    }
    @Override
    public boolean isOpenFlag() {
        return openFlag;
    }
    @Override
    public void setOpenFlag(boolean openFlag) {
        this.openFlag = openFlag;
    }

}

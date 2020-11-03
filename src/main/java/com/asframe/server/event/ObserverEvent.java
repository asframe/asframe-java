/**
 * @ObserverEvent.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.server.event;

import com.asframe.server.ServerSession;

/**
 * 普通的观察者事件，用于通讯的数据，实现了基础的发送机制
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/5
 */
public class ObserverEvent
{
    /**
     * 发送自己，封装了派发事件的底层细节
     * 注意，使用这个方法，表示是使用框架自带的全局事件机制
     * @param notice
     */
    public void sendSelf(String notice)
    {
        ServerSession.events.send(notice,this);
    }
}

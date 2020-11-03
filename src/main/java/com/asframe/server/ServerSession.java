/**
 * @ServerSession.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:Keep
 * <br>Date:2019/11/23
 */
package com.asframe.server;

import com.asframe.mode.observer.Subjects;

/**
 * 服务端框架的内存架构，用于存放内存数据和对象，提升访问性能
 * 一旦服务重启，则数据就会丢失
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/3
 */
public class ServerSession {

    /**
     * 基于服务端的全局主题，非线程安全的事件机制
     */
    public final static Subjects events = new Subjects();

}

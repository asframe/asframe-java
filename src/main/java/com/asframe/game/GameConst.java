/**
 * @GameConst.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:TribeGameServer
 * <br>Date:2018-10-1
 */
package com.asframe.game;
/**
 * 游戏框架里使用到的一些常量类
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public class GameConst {

    /**
     * 服务器是否处于网关的链接模式
     */
    public static final boolean IS_AC_Mode = true;
    /**
     * 客户端和服务端指令之间的差距（0就表示客户端和服务器使用相同的指令）
     * 框架默认是10000开始是客户端发送给服务器的
     * 20000开始是服务器返回客户端的，两者一一对应的，比如登录协议是 10001，那么服务器返回是20001
     */
    public static final short C_S_Cmd_Value = 10000;
    /**
     * 本地IP
     */
    public static final String Local_Ip = "127.0.0.1";
}

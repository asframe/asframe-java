/**
 * @MVCConst.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2019/11/23
 */
package com.asframe.server;


import com.asframe.server.annotation.ErrorAnnotation;

/**
 * mvc相关的所有常量
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2019/2/13
 */
public final class ServerConst
{
    /**
     * 不需要处理框架自动处理协议了
     */
    public static short No_Handle = -1;
    /**
     * 成功，框架自动处理返回数据
     */
    public static short Success = 0;

    /**
     * 底层验证错误
     */
    @ErrorAnnotation("底层验证错误")
    public static short Verify_Error = 1;
    /**
     * 底层处理指令错误
     */
    @ErrorAnnotation("底层Cmd处理逻辑报错")
    public static short Handle_Cmd_Error = 2;
    /**
     * 底层Cmd过滤器指令错误
     */
    @ErrorAnnotation("底层Cmd过滤器指令错误")
    public static short Cmd_Filter_Error = 3;
    /**
     * 底层抛出的消息异常
     */
    @ErrorAnnotation("底层抛出的消息异常")
    public static short Message_Exception_Error = 4;
    /**
     * Dubbo的no provider的问题特别处理
     */
    @ErrorAnnotation("网络异常，服务调用失败，请重新请求")
    public static short Dubbo_No_Provider = 5;
    /**
     * 特别处理
     */
    @ErrorAnnotation("你的操作有误，联系管理员看看吧")
    public static short Roll_Back_Expection = 6;

    public static String Mvc_Session_Key = "Mvc_Session_Key";
    @ErrorAnnotation("用户没有登陆")
    public static short Player_Not_Login = 10;
}

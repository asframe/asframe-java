/**
 * @MVCConst.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2019/11/23
 */
package com.asframe.mvc;

/**
 * mvc相关的所有常量
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Date:2020/10/7
 */
public final class MVCConst
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
    public static short Verify_Error = 1;
    /**
     * 底层处理指令错误
     */
    public static short Handle_Cmd_Error = 2;


    /**
     *  token 验证成功
     */
    public static short Toke_Verify_Success = 4;

    /**
     * token 验证失败
     */
    public static short Toke_Verify_Fail = 5;

    public static String Mvc_Session_Key = "Mvc_Session_Key";
}

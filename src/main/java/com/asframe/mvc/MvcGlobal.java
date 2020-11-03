/**
 * @MvcGlobal.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2019/11/24
 */
package com.asframe.mvc;

import com.asframe.server.IServerContext;
import com.asframe.server.ServerConst;
import com.asframe.server.ServerContext;
import com.asframe.server.ServerInit;
import com.asframe.utils.AnnotationUtil;
import com.asframe.mvc.cmd.HttpJsonCmdProcessor;
import com.asframe.mvc.control.IControl;
import com.asframe.server.cmd.ICmdProcessor;
import com.asframe.server.filter.ICmdRequestFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * mvc全局对象
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/10/7
 */
public class MvcGlobal
{
    private static Map<Short,String> errorMsgMap;
    /**
     * 服务器的命令解释器，关键东西，不能乱动
     */
    public static ICmdProcessor cmdProcessor = new HttpJsonCmdProcessor();

    private static List<IControl> controlList = new ArrayList<>();

    public static Map<Short, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    /**
     * 进行初始化，一起服就初始化，防止多线程调用出现错误
     * @param sessionClass Session对象类
     * @param errorClass 错误码对象类
     */
    public static IServerContext init(Class<?> sessionClass, Class<?> errorClass)
    {
        //初始化服务端框架
        IServerContext serverContext = new ServerContext();
        new ServerInit().init(serverContext);
        MvcGlobal.cmdProcessor.setServerContext(serverContext);
        //默认使用ServerConst
        Map<Short,String> tempMsgMap = AnnotationUtil.getAnnotationMap(ServerConst.class);
        MvcGlobal.errorMsgMap = AnnotationUtil.getAnnotationMap(errorClass);
        if(tempMsgMap.size() > 0)
        {
            tempMsgMap.forEach((key,value) ->
            {
                //如果为空才需要放进去
                if(!MvcGlobal.errorMsgMap.containsKey(key))
                {
                    MvcGlobal.errorMsgMap.put(key,value);
                }
            });
        }
        MvcGlobal.cmdProcessor.setErrorMap(MvcGlobal.errorMsgMap);
        //对控制器进行遍历初始化相关对象
        controlList.forEach(control -> control.initControl(MvcGlobal.cmdProcessor,sessionClass));
        //初始化模块
        serverContext.start();
        return serverContext;
    }

    /**
     * 设置Cmd过滤器
     * @param cmdFilter
     */
    public static void setCmdFilter(ICmdRequestFilter cmdFilter)
    {
        MvcGlobal.cmdProcessor.setCmdFilter(cmdFilter);
    }

    /**
     * 添加mvc特有的控制器对象
     * @param control
     */
    public static void addControl(IControl control)
    {
        controlList.add(control);
    }

    /**
     * 注册服务端发送的 Response Class，工具生成注册信息
     * @param resCmd
     * @param resClass
     */
    public static void regResponse(short resCmd,Class resClass)
    {
        MvcGlobal.cmdProcessor.regResponse(resCmd,resClass);
    }
    public static boolean hasErrorMsg(short code)
    {
        if(errorMsgMap == null)
        {
            return false;
        }
        if(errorMsgMap.containsKey(code))
        {
            return true;
        }
        return false;
    }
    /**
     * 通过错误码返回对应的提示信息
     * @param code
     * @return
     */
    public static String getErrorMsg(short code)
    {
        if(errorMsgMap == null)
        {
            return null;
        }
        if(errorMsgMap.containsKey(code))
        {
            return errorMsgMap.get(code);
        }
        return null;
    }


}

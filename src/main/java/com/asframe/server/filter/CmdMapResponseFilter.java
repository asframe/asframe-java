package com.asframe.server.filter;

import com.asframe.server.ServerConst;
import com.asframe.server.protocol.IProtocol;

import java.util.HashMap;
import java.util.Map;

/**
 * cmd的过滤器，在调用cmd之前，会调用相关的过滤器。这个是每个cmd单独享有的
 * 也可以多个cmd享有同样的过滤器
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/10/11
 */
public class CmdMapResponseFilter<T extends IProtocol,S> implements ICmdResponseFilter<T,S>
{
    private Map<Short,ICmdResponseFilter> cmdResponseFilterMap;

    public CmdMapResponseFilter()
    {
        this.cmdResponseFilterMap = new HashMap<>();
    }

    /**
     * 注册一个cmd对应的解析
     * @param cmd
     * @param cmdResponseFilter
     */
    public void registerCmdFilter(short cmd,ICmdResponseFilter cmdResponseFilter)
    {
        if(this.cmdResponseFilterMap.containsKey(cmd))
        {
            throw new Error(cmd + "不能重复注册" + cmdResponseFilter);
        }
        this.cmdResponseFilterMap.put(cmd,cmdResponseFilter);
    }
    /**
     * 请求过滤器
     * @param response 请求对象
     * @return
     * @throws Throwable 抛出的异常
     */
    public short responseFilter(S session,short resCmd,T response) throws Throwable
    {
        ICmdResponseFilter cmdResponseFilter = this.cmdResponseFilterMap.get(resCmd);
        if(cmdResponseFilter != null)
        {
            return cmdResponseFilter.responseFilter(session,resCmd,response);
        }
        return ServerConst.Success;
    }
}

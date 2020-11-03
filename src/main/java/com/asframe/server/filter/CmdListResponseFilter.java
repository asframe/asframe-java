package com.asframe.server.filter;

import com.asframe.server.ServerConst;
import com.asframe.server.protocol.IProtocol;
import com.asframe.utils.ErrorLogUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * cmd的过滤器，在调用cmd之前，会调用相关的过滤器。这个是每个cmd单独享有的
 * 也可以多个cmd享有同样的过滤器
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/10/11
 */
public class CmdListResponseFilter<T extends IProtocol,S> implements ICmdResponseFilter<T,S>
{
    private static Logger logger = LogManager.getLogger(CmdListResponseFilter.class);
    private List<ICmdResponseFilter> cmdResponseFilterList;

    public CmdListResponseFilter()
    {
        this.cmdResponseFilterList = new ArrayList<>();
    }

    /**
     * 注册一个cmd对应的解析
     * @param cmdResponseFilter
     */
    public void registerCmdFilter(ICmdResponseFilter cmdResponseFilter)
    {
        if(this.cmdResponseFilterList.contains(cmdResponseFilter))
        {
            throw new Error("不能重复注册" + cmdResponseFilter);
        }
        this.cmdResponseFilterList.add(cmdResponseFilter);
    }
    /**
     * 请求过滤器
     * @param response 请求对象
     * @return
     * @throws Throwable 抛出的异常
     */
    public short responseFilter(S session,short resCmd,T response) throws Throwable
    {
        int length = this.cmdResponseFilterList.size();
        ICmdResponseFilter cmdResponseFilter;
        short result = ServerConst.Success;
        for (int i = 0; i < length; i++)
        {
            try
            {
                result = this.cmdResponseFilterList.get(i).responseFilter(session,resCmd,response);
                //只要有一个过滤器不成功，则整体返回不成功
                if(result != ServerConst.Success)
                {
                    return result;
                }
            }
            catch (Exception e)
            {
                logger.error(ErrorLogUtil.errorTrackSpaceString(e));
            }
        }
        return result;
    }
}

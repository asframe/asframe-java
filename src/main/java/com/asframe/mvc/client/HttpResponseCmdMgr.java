package com.asframe.mvc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Http服务器指令处理管理器
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/29
 */
public class HttpResponseCmdMgr
{
    private final static Logger log = LoggerFactory.getLogger(HttpResponseCmdMgr.class);
    /**
     * 存放responseCmd集合
     */
    private static HashMap<Short,IHttpResponseCmd> cmdMap = new HashMap<>();

    /**
     * 注册指令
     * @param cmd
     * @param responseCmd
//     * @param cmdClass
     */
    public static void regCmd(short cmd,IHttpResponseCmd responseCmd,Class cmdClass)
    {
        responseCmd.setResponseClass(cmdClass);
        cmdMap.put(cmd,responseCmd);
    }

    /**
     * 根据服务器返回的指令获取到对应的指令处理对象
     * @param cmd
     * @return
     */
    public static IHttpResponseCmd getResponseCmd(short cmd)
    {
        IHttpResponseCmd responseCmd = cmdMap.get(cmd);
        if(responseCmd == null)
        {
            log.error(cmd + "没有注册对应的Http ResponseCmd");
            return null;
        }
        return responseCmd;
    }
}

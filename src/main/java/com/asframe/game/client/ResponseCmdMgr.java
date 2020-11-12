package com.asframe.game.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 服务器指令处理管理器
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/10
 */
public class ResponseCmdMgr
{
    private final static Logger log = LoggerFactory.getLogger(ResponseCmdMgr.class);
    /**
     * 存放responseCmd集合
     */
    private static HashMap<Short,IResponseCmd> cmdMap = new HashMap<>();

    /**
     * 注册指令
     * @param cmd
     * @param responseCmd
     * @param cmdClass
     */
    public static void regCmd(short cmd,IResponseCmd responseCmd,Class<?> cmdClass)
    {
        responseCmd.setResponseClass(cmdClass);
        cmdMap.put(cmd,responseCmd);
    }

    /**
     * 根据服务器返回的指令获取到对应的指令处理对象
     * @param cmd
     * @return
     */
    public static IResponseCmd getResponseCmd(short cmd)
    {
        IResponseCmd responseCmd = cmdMap.get(cmd);
        if(responseCmd == null)
        {
            log.error(cmd + "没有注册对应的ResponseCmd");
            return null;
        }
        return responseCmd;
    }
}

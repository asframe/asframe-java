package com.asframe.server;

import com.asframe.server.cmd.ICmd;
import com.asframe.server.module.IModule;
import com.asframe.server.module.db.IModuleDB;
import com.asframe.server.module.helper.IModuleHelper;
import com.asframe.utils.ErrorLogUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 模块中心，专门处理和管理容器对象
 * @author sodaChen
 * @Date:2019/2/13
 */
public class ServerContext implements IServerContext
{
    private static Logger logger = LogManager.getLogger(ServerContext.class);
    /** 模块集合 **/
    private Map<Short, IModule>             moduleMap;
    /** cmd集合 **/
    private Map<Short, ICmd>                cmdMap;

    public ServerContext()
    {
        this.moduleMap = new HashMap<>();
        this.cmdMap = new HashMap<>();
    }

    @Override
    public void registerModule(IModule module)
    {
        if(this.moduleMap.containsKey(module.getId()))
        {
            throw new Error(module.getId() + " 模块重复添加");
        }
        this.moduleMap.put(module.getId(),module);
    }
    @Override
    public void registerModuleDB(IModuleDB moduleDB)
    {
        this.getModule(moduleDB.getModuleId()).setModuleDB(moduleDB);
    }
    /**
     * 注册指令处理对象
     * @param cmd
     */
    @Override
    public void registerCmd(ICmd cmd)
    {
        if(this.cmdMap.containsKey(cmd.getReqCmd()))
        {
            throw new Error(cmd + " cmd重复添加");
        }
        cmd.setModule(this.getModule(cmd.getModuleId()));
        this.cmdMap.put(cmd.getReqCmd(),cmd);
    }

    /**
     * 根据请求id获取到对应的cmd对象
     * @param reqCmd
     * @return
     */
    @Override
    public ICmd getCmd(short reqCmd)
    {
        if(this.cmdMap.containsKey(reqCmd))
        {
            return this.cmdMap.get(reqCmd);
        }
        return null;
    }
    /**
     * 注册指令处理对象
     * @param helpers
     */
    @Override
    public void registerHelpers(short moduleId,IModuleHelper[] helpers)
    {
        this.getModule(moduleId).addHelpers(helpers);
    }
    /**
     * 获取到模块对象
     * @param moduleId
     * @return
     */
    @Override
    public IModule getModule(short moduleId)
    {
        IModule module = this.moduleMap.get(moduleId);
        if(module == null)
        {
            throw new Error(moduleId + " 没有对应的模块对象");
        }
        return module;
    }

    /**
     * 开始初始化模块信息
     */
    @Override
    public void start()
    {
        //所有模块初始化init方法
        for(IModule module : moduleMap.values()){
           try {
               module.init();
           }
           catch (Exception e)
           {
               logger.error(module + "init初始化异常");
               logger.error(ErrorLogUtil.errorTrackSpaceString(e));
           }
        }
        //所有模块初始化start方法
        for(IModule module : moduleMap.values()){
            try {
                module.start();
            }
            catch (Exception e)
            {
                logger.error(module + "start初始化异常");
                logger.error(ErrorLogUtil.errorTrackSpaceString(e));
            }
        }
        //遍历所有的cmd，调用cmd的init方法
        for(ICmd cmd : cmdMap.values()){
            try {
                cmd.init();
            }
            catch (Exception e)
            {
                logger.error(cmd + "init初始化异常");
                logger.error(ErrorLogUtil.errorTrackSpaceString(e));
            }
        }
    }
}

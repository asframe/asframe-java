package com.asframe.server.module.db;

/**
 * 模块数据基类对象
 * @author sodaChen
 * @date 2020/2/8
 */
public interface IModuleDB {

    /**
     * 返回模块Id
     * @return
     */
    short getModuleId();
    /**
     * 初始化方法
     */
    void init();
    /**
     * 所有的模块和ModuleDB初始化完成之后，会调用这个启动方法
     */
    void start();
}

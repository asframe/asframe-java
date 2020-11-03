package com.asframe.server.module.helper;

import com.asframe.server.module.IModule;
import com.asframe.server.module.db.IModuleDB;

/**
 * 辅助对象，跟module绑定。实现模块的小功能
 * 当一个模块比较大和复杂时，需要进行分割小功能的时候，采用helper进行分割
 * @author sodaChen
 * @date 2020.02.03
 */
public interface IModuleHelper<M extends IModule,D extends IModuleDB>
{
    /**
     * 返回模块Id
     * @return
     */
    short getModuleId();
    /**
     * 设置模块绑定的数据模型对象
     * @param moduleDB
     */
    void setModuleDB(D moduleDB);
    /**
     * 设置绑定的模块
     * @param module
     */
    void setModule(M module);

    /**
     * 初始化方法
     */
    void init();
    /**
     * 所有的模块和helper初始化完成之后，会调用这个启动方法
     */
    void start();
}

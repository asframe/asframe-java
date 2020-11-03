package com.asframe.server.module;

import com.asframe.server.module.db.IModuleDB;

/**
 * 模块容器接口，用来设置模块对象
 * @author sodaChen
 * @date 2018-10-1
 */
public interface IModuleContainer<M extends IModule,D extends IModuleDB> {

    void setModuleDB(D moduleDB);
    /**
     * 设置绑定的模块
     * @param module
     */
    void setModule(M module);
}

package com.asframe.server.module;

import com.asframe.server.module.db.IModuleDB;

/**
 * 模块容器对象
 * @author sodaChen
 * @date 2020年2月3日
 */
public class ModuleContainer<M extends IModule, D extends IModuleDB> implements IModuleContainer<M,D> {

    protected M module;
    protected D moduleDB;

    @Override
    public void setModuleDB(D moduleDB)
    {
       this.moduleDB = moduleDB;
    }
    /**
     * 设置绑定的模块
     * @param module
     */
    @Override
    public void setModule(M module)
    {
        this.module = module;
    }
}

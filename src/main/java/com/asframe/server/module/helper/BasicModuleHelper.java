package com.asframe.server.module.helper;

import com.asframe.server.module.IModule;
import com.asframe.server.module.db.IModuleDB;

/**
 * 基础的模块Helper辅助对象
 * @author sodaChen
 * @date 2020/2/8
 */
public class BasicModuleHelper<M extends IModule,D extends IModuleDB>  implements IModuleHelper<M,D> {

    /**
     * 唯一的模块id
     */
    protected short moduleId;
    /**
     * 绑定的模块对象
     */
    protected M module;
    /**
     * 绑定的模块数据对象
     */
    protected D moduleDB;

    /**
     * 返回模块Id
     * @return
     */
    @Override
    public short getModuleId()
    {
        return this.moduleId;
    }

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

    @Override
    public void init()
    {

    }

    @Override
    public void start()
    {

    }
}

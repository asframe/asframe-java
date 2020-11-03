/**
 * @ModuleBean.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2020/2/10
 */
package com.asframe.server.module.bean;

import com.asframe.server.module.db.IModuleDB;
import com.asframe.server.module.helper.IModuleHelper;
import com.asframe.server.module.IModule;

/**
 * 封装了模块的相关数据对象
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/2/10
 */
public class ModuleBean
{
    private IModule module;
    private IModuleDB moduleDB;
    private IModuleHelper[] helperList;

    public ModuleBean(IModule module,IModuleDB moduleDB,IModuleHelper[] helperList)
    {
        this.module = module;
        this.moduleDB = moduleDB;
        this.helperList = helperList;
    }

    public IModule getModule() {
        return module;
    }

    public IModuleDB getModuleDB() {
        return moduleDB;
    }

    public IModuleHelper[] getHelperList() {
        return helperList;
    }
}

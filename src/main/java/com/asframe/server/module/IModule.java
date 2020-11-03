package com.asframe.server.module;

import com.asframe.server.IEntrance;
import com.asframe.server.module.db.IModuleDB;
import com.asframe.server.module.helper.IModuleHelper;

/**
 * 模块接口，所有基于模块的机制都必须实现这个接口。
 * 这个接口是每个模块对外访问的对象
 * @author sodaChen
 * @date 2018-10-1
 */
public interface IModule<D extends IModuleDB> extends IEntrance {
    /**
     * 设置模块数据模型
     * @param moduleDB
     */
    void setModuleDB(D moduleDB);
    /**
     * 模块的唯一ID
     * @return
     */
    short getId();

    /**
     * 模块的唯一名字
     * @return
     */
    String getName();
    /**
     * 添加模块的辅助对象数组
     * @param helpers
     */
    void addHelpers(IModuleHelper[] helpers);

    /**
     * 模块的初始化方法
     */
    void init();

    /**
     * 主要子类重写，用于init的方法
     */
    void onInit();

    /**
     * 模块的开始方法，当所有的模块init完成之后，才会调用start方法
     */
    void start();

    /**
     * start之后的执行方法
     */
    void onStart();
}


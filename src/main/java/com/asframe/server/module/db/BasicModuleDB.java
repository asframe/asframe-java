package com.asframe.server.module.db;

import com.asframe.server.ServerInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础模块数据对象
 * @author sodaChen
 * @date 2020/2/8
 */
public class BasicModuleDB implements IModuleDB {
    protected Logger logger = LoggerFactory.getLogger(BasicModuleDB.class);
//    /**
//     * 绑定的模块对象
//     */
//    protected M module;

//    /**
//     * 设置绑定的模块
//     * @param module
//     */
//    @Override
//    public void setModule(M module)
//    {
//        this.module = module;
//    }
    /**
     * 唯一的模块id
     */
    protected short moduleId;


    public BasicModuleDB()
    {
        ServerInit.addModule(this);
    }

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
    public void init()
    {

    }

    @Override
    public void start()
    {

    }
}

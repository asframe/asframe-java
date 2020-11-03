package com.asframe.server.module;

import com.asframe.server.Entrance;
import com.asframe.server.ServerInit;
import com.asframe.server.module.db.IModuleDB;
import com.asframe.server.module.helper.IModuleHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 基础模块对象，实现了基础的功能方法
 * @author sodaChen
 * @date 2020年2月3日
 */
public class BasicModule<D extends IModuleDB> extends Entrance implements IModule<D> {

    public Logger logger = LogManager.getLogger(BasicModule.class);
    /**
     * 唯一id
     */
    protected short id;
    /**
     * 唯一名字
     */
    protected String name;
    /**
     * 模块的数据对象
     */
    protected D moduleDB;

    protected IModuleHelper[] helpers;


    public BasicModule()
    {
        ServerInit.addModule(this);
    }




    public void setId(short id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setModuleDB(D moduleDB)
    {
        this.moduleDB = moduleDB;
    }
    /**
     * 添加模块的辅助对象数组
     * @param helpers
     */
    @Override
    public void addHelpers(IModuleHelper[] helpers)
    {
        this.helpers = helpers;
    }

    /**
     * 模块的唯一ID
     * @return
     */
    @Override
    public short getId()
    {
        return this.id;
    }

    /**
     * 模块的唯一名字
     * @return
     */
    @Override
    public String getName()
    {
        return this.name;
    }

    /**
     * 模块的初始化方法
     */
    @Override
    public void init()
    {
        logger.info(this + "执行init方法");
        if(this.moduleDB != null)
        {
            try {
                this.moduleDB.init();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                logger.error(this.name + "初始化moduleDB报错");
            }
        }
        if(this.helpers != null) {
            for (IModuleHelper helper : this.helpers) {
                try {
                    //进行赋值
                    helper.setModule(this);
                    helper.setModuleDB(this.moduleDB);
                    helper.init();
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("init方法报错");
                }
            }
        }
        this.onInit();
        logger.info(this + "完成init");
    }
    @Override
    public void onInit()
    {

    }

    /**
     * 模块的开始方法，当所有的模块init完成之后，才会调用start方法
     */
    @Override
    public void start()
    {
        logger.info("执行start方法");
        if(this.moduleDB != null)
        {
            try {
                this.moduleDB.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                logger.error(this.name + "初始化moduleDB报错");
            }
        }
        if(this.helpers != null)
        {
            for (IModuleHelper helper : this.helpers) {
                try {
                    helper.start();
                } catch (Exception e) {
                    logger.error("start方法报错");
                }
            }
        }
        this.onStart();
        logger.info("完成start");
    }
    @Override
    public void onStart()
    {

    }
}

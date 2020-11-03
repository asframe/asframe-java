/**
 * @IModule.java
 *
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:TribeGameServer
 * <br>Date:2018-10-1
 */
package com.asframe.server.cmd;

import com.asframe.mvc.MvcGlobal;
import com.asframe.server.Entrance;
import com.asframe.server.ServerConst;
import com.asframe.server.ServerInit;
import com.asframe.server.module.IModule;
import com.asframe.server.verify.IServerVerify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 游戏中指令处理对象，专门处理客户端提交过来的指令信息
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public abstract class BasicCmd<M extends IModule,S,REQ,RES>  extends Entrance implements ICmd<M,S,REQ,RES>
{
    /**
     * 日志对象
     */
    public static Logger                logger = LoggerFactory.getLogger(BasicCmd.class);
    /**
     * 模块唯一id
     */
    protected short                     moduleId;
    /**
     * 绑定的模块对象
     */
    protected M                         module;
    /**
     * 请求指令
     */
    protected short                     reqCmd;
    /**
     * 返回指令
     */
    protected short                     resCmd;
    /**
     * 错误码指令
     */
    protected short                     errorCode = ServerConst.Handle_Cmd_Error;
    /**
     * 机器生成代码的验证机制
     */
    protected IServerVerify             robotAutoVerify;


    public BasicCmd()
    {
        ServerInit.addCmd(this);
    }


    /**
     * cmd初始化方法
     * 服务器启动好之后，框架会自动调用这个方法
     */
    @Override
    public void init()
    {

    }

    public void initResponse(short resCmd, Class<?> clazz)
    {
        this.resCmd = resCmd;
        MvcGlobal.regResponse(resCmd,clazz);
    }
    /**
     * 返回错误码
     * @return
     */
    @Override
    public short getErrorCode()
    {
        return this.errorCode;
    }

    /**
     * 校验请求对象是否满足业务处理的需求,父类处理通用的验证需求（验证码，时间等）
     * @param request
     * @return
     */
    @Override
    public short isVerify(REQ request)
    {
        //先调用子类重写的验证机制，因为机器码是做最基础的验证
        short code = ServerConst.Success;
        if(this.robotAutoVerify != null)
        {
            code =  this.robotAutoVerify.isVerify(request);
        }
        //验证成功则继续验证用户编写的逻辑
        if(code == ServerConst.Success)
        {
            return this.onVerify(request);
        }
        return code;
    }

    /**
     * 主要给子类重写，默认是返回成功
     * @param request
     * @return
     */
    @Override
    public short onVerify(REQ request)
    {
        return ServerConst.Success;
    }

    @Override
    public void setModule(M module)
    {
        this.module = module;
    }
    /**
     * 获取到这个指令的模块对象
     * @return
     */
    @Override
    public M getModule()
    {
        return this.module;
    }

    /**
     * 具体处理前端请求命令
     * @param session
     * @param request
     * @param response 服务器返回数据结构对象
     * @return
     * @throws Throwable 业务抛出的异常
     */
    @Override
    public abstract short handleCmd(S session, REQ request, RES response) throws Throwable;
    /**
     * 返回所属的模块ID
     * @return
     */
    @Override
    public short getModuleId()
    {
        return this.moduleId;
    }

    @Override
    public short getReqCmd() {
        return reqCmd;
    }
    @Override
    public short getResCmd() {
        return resCmd;
    }
}

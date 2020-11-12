package com.asframe.game.role;

import com.asframe.game.protocol.write.ITcpResponse;
import com.asframe.game.session.IPlayerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家基类，实现了一些玩家的基础功能
 * @author sodaChen
 * @version 1.0
 * @date 2018/10/3
 */
public abstract class BasicPlayer extends Character implements IPlayer
{
    private static Logger logger = LoggerFactory.getLogger(BasicPlayer.class);
    /**
     * 玩家session
     */
    protected IPlayerSession session;
    /**
     * 玩家id
     */
    protected long userId;
    /**
     * 是否掉线了
     */
    protected boolean isLogout;
    /**
     * 注意这个对象非线程安全，一般在玩家初始化的时候就进行添加，后面不再进行添加和操作
     */
    protected List<IPlayerHelper> playerHelperList;


    public BasicPlayer()
    {
        this.playerHelperList = new ArrayList<>();
    }

    @Override
    public void init()
    {
        int length = this.playerHelperList.size();
        IPlayerHelper playerHelper = null;
        for (int i = 0; i < length; i++) {
            playerHelper = this.playerHelperList.get(i);
            try {
                playerHelper.init(this);
            }
            catch (Exception e)
            {
                logger.error(this + "初始化" + playerHelper + "报错");
                logger.error("错误:",e);
            }
        }
    }

    /**
     * 注册一个玩家助手进来.先手工助手，后面可以加注解自动引用进来
     * @param playerHelper
     */
    protected void registerHelper(IPlayerHelper playerHelper)
    {
        if(this.playerHelperList.indexOf(playerHelper) != -1)
        {
            logger.warn(playerHelper + "进行重复添加");
            return ;
        }
        this.playerHelperList.add(playerHelper);
    }

    @Override
    public long getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    @Override
    public void setSession(IPlayerSession session) {
        this.session = session;
        session.setPlayer(this);
    }

    @Override
    public IPlayerSession getSession() {
        return this.session;
    }

    @Override
    public void login()
    {
        this.isLogout = false;
        int length = this.playerHelperList.size();
        IPlayerHelper playerHelper = null;
        for (int i = 0; i < length; i++) {
            playerHelper = this.playerHelperList.get(i);
            try {
                playerHelper.login();
            }
            catch (Exception e)
            {
                logger.error(this + "登录" + playerHelper + "报错");
            }
        }
        this.onLogin();
    }
    /**
     * 是否为机器人
     * @return
     */
    @Override
    public boolean isRobot()
    {
        return false;
    }

    protected void onLogin()
    {

    }

    @Override
    public boolean isLogout() {
        return this.isLogout;
    }



    @Override
    public void logout()
    {

        this.isLogout = true;
        int length = this.playerHelperList.size();
        IPlayerHelper playerHelper = null;
        for (int i = 0; i < length; i++) {
            playerHelper = this.playerHelperList.get(i);
            try {
                playerHelper.logout();
            }
            catch (Exception e)
            {
                logger.error(this + "初始化" + playerHelper + "报错");
            }
        }
        this.onLogout();
        if(this.session != null) {
            this.session.close();
        }
    }

    /**
     * 子类重写的logout的方法
     */
    protected void onLogout()
    {

    }

    @Override
    public void saveAll() {

    }



    @Override
    public void reset() {

    }

    @Override
    public void sendMessage(Object object)
    {
        if(this.isLogout)
        {
            logger.info(getName() + "掉线了，发不出数据:" + object);
            return ;
        }
        this.session.sendMessage(object);
    }

    @Override
    public void sendMessage(ITcpResponse response) {
        if(this.isLogout) {
            logger.info(getName() + "掉线了:" + response);
            return;
        }
        if(this.isRobot()){
            return;
        }
        this.session.sendMessage(response);
    }

    @Override
    public void sendMessage(short resCmd,ITcpResponse response) {
        if(this.isLogout) {
            logger.info(getName() + "掉线了:" + response);
            return;
        }
        if(this.isRobot()){
            return;
        }
        this.session.sendMessage(resCmd,response);
    }

    @Override
    public void sendErrorCode(short errorCode, ITcpResponse response) {
        if(this.isLogout) {
            return;
        }
        this.session.sendErrorCode(errorCode,response);
    }

    @Override
    public void sendErrorCode(short resCmd,short errorCode)
    {
        if(this.isLogout) {
            return;
        }
        this.session.sendErrorCode(resCmd,errorCode);
    }
}

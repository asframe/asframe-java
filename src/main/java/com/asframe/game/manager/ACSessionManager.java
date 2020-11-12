package com.asframe.game.manager;

import com.asframe.game.session.IPlayerSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 网关连接上来的
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/10
 */
public class ACSessionManager
{
    /////////////////单例的统一规范///////////////////
    private static ACSessionManager instance = new ACSessionManager();
    public static ACSessionManager getInstance()
    {
        return instance;
    }

    private Map<Long, IPlayerSession> sessionMap;

    /**
     * 游戏业务线程池
     */
    private ExecutorService executorService;

    private ACSessionManager()
    {
        this.sessionMap = new ConcurrentHashMap<>();
        this.executorService = Executors.newFixedThreadPool(8);
    }

    /**
     * 执行一个新的业务到线程池里面去
     * @param runnable
     */
    public void runWork(Runnable runnable)
    {
        this.executorService.execute(runnable);
    }

    public ExecutorService getExecutorService()
    {
        return this.executorService;
    }

    public void addSession(IPlayerSession session)
    {
        this.sessionMap.put(session.getId(),session);
    }

    public IPlayerSession getSession(long id)
    {
        return this.sessionMap.get(id);
    }
    public IPlayerSession removeSession(long id)
    {
        return this.sessionMap.remove(id);
    }
}

package com.asframe.game;

import com.asframe.game.db.IDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏框架中使用到一些内存数据结构
 * 主要用于共享数据
 *
 * @author sodaChen
 */
public class GameSession
{
    /**
     * 是否启动wss
     */
    public static boolean isWss;
    /**
     * jks密码
     */
    public static String Jks_Password;
    /**
     * jks的证书路径
     */
    public static String Jks_Path;

    /**
     * Dao列表，每次dao会自动放到这个容器中来，用来做一些数据的初始化
     */
    public static List<IDao> daoList = new ArrayList<>();

    /**
     * 初始化dao
     * @param template
     */
    public static void initDao(Object template)
    {
        int length = daoList.size();
        for (int i = 0; i < length; i++) {
            daoList.get(i).setTemplate(template);
        }
    }
}

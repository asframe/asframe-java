package com.asframe.game.utils;

import com.asframe.game.protocol.write.ITcpResponse;
import com.asframe.game.role.IPlayer;
import com.asframe.game.thread.GameExecutorService;

import java.util.Map;

/**
 * 广播工具，采用后台线程广播
 * @Auther: soda
 * @Date: 2018/11/29 21:00
 * @Description:
 */
public class BroadcastUtils
{
    /**
     * 使用后台广播线程广播当前的返回对象
     * @param playerMap
     * @param response
     */
    public  static <T extends IPlayer> void broadcastByPlayerMap(Map<String, T> playerMap, ITcpResponse response)
    {
        if(playerMap == null || playerMap.size() == 0){
            return;
        }
        GameExecutorService.getInstance().executeReportTask(new Runnable()
        {
            @Override
            public void run()
            {
                for (T player : playerMap.values())
                {
                    if(!player.isLogout() && !player.isRobot())
                    {
                        player.sendMessage(response);
                    }
                }
            }
        });
    }
}

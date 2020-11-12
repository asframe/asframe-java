/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.asframe.game.client;

import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

/**
 * 实现的客户端对象
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/10
 */
public class WebSocketClient extends BasicWebSocketClient
{
    private static final Logger logger = LoggerFactory.getLogger(WebSocketClient.class);

    protected void connectEnd()
    {
        //开始进行处理他自身的各种逻辑了，比如登录，启动自己的计时器之类的，使用线程池
        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                logger.info(player.getName() + "线程池执行player.login");
                //启动机器人的逻辑程序,开始一个机器人的正常流程啦
                player.login();
            }
        });
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        new NettyClient().connect(NettyConstant.PORT, NettyConstant.REMOTEIP);
        WebSocketClient client = new WebSocketClient();
        client.init(null,new NioEventLoopGroup(),Executors.newScheduledThreadPool(5));
        client.connect(WebSocketClientHandler.class,19050, "127.0.0.1");
    }

}

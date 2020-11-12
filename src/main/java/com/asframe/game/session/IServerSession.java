package com.asframe.game.session;

import com.asframe.game.protocol.write.ITcpResponse;

public interface IServerSession extends ISession{

//    public IServer getServer();

//    public void setServer(IServer iServer);

    /**
     * 发送object。一般是用于服务器内部序列化数据
     * @param object
     */
    public void sendMessage(Object object);

    /**
     * 发送数据回客户端
     * @param response
     */
    public void sendMessage(ITcpResponse response);
}

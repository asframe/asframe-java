/**
 * @IRead.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C), 2018 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFrame
 * <br>Date:2018-10-1
 */
package com.asframe.mvc.protocol.read;

import com.asframe.mvc.protocol.IHttpProtocol;
import com.asframe.server.protocol.IRequestProtocol;

/**
 * 游戏中的功能模块接口，把游戏分成一个个模块，每个模块都有不同的指令
 * @author sodaChen
 * @date 2018-10-1
 *
 */
public interface IHttpRequest extends IRequestProtocol {
    /**
     * 此次http通讯的协议版本号
     * @return
     */
    String getVer();

    /**
     * 设置版本信息
     * @param ver
     */
    void setVer(String ver);
    /**
     * 此次通讯协议的token。http特有
     * @return
     */
    String getToken();

    /**
     * 设置token
     * @param token
     */
    void setToken(String token);
}

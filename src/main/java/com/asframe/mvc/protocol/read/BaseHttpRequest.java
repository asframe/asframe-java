package com.asframe.mvc.protocol.read;

import com.asframe.server.protocol.BaseProtocol;

/**
 * 游戏中的功能模块接口，把游戏分成一个个模块，每个模块都有不同的指令
 * @author sodaChen
 * @date 2018-10-1
 */
public abstract class BaseHttpRequest extends BaseProtocol implements IHttpRequest {
    /**
     * 版本号
     */
    private String ver;
    /**
     * 唯一token
     */
    private String token;


    @Override
    public String getVer() {
        return ver;
    }

    @Override
    public void setVer(String ver) {
        this.ver = ver;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }
}

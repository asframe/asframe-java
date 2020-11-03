package com.asframe.server.role;

/**
 * 角色的登录接口
 * @author sodaChen
 * @date 2020/2/9
 */
public interface IRoleLogin<R extends IServerRole> {

    void setServerRole(R serverRole);
    void init();
    void login();
    void logout();
}

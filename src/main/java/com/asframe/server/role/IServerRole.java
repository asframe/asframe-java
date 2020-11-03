package com.asframe.server.role;

/**
 * @author sodaChen
 * @date 2020/2/9
 */
public interface IServerRole {

    String getKey();
    String getName();
    long getId();
    void init();
    void login();
    void logout();
}

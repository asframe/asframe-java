package com.asframe.game.rule;

/**
 * 热更配置表回调接口
 * @Author: sodaChen
 * @Date: 2020-10-09
 */
public interface IHotUpdateTable
{
    /**
     * 当前热更的表
     * @param tableName
     */
    void hotUpdateTable(String tableName);
}

/**
 * @BasicRuleBean.java
 * @author sodaChen mail:asframe#qq.com
 * @version 1.0
 * <br>Copyright (C), 2012-present ASFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFame
 * <br>Date:2020-10-17
 */
package com.asframe.game.rule;

/**
 * 单条策划配置规则数据基类
 * @author sodaChen
 * Date:2020/11/29
 */
public class BasicRuleBean implements IRuleBean
{
    private int id;
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }
}

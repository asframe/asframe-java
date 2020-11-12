package com.asframe.game.enums;

/**
 * 实体操作状态
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/10
 */
public enum  EntityStatusEnum {
    STATUS_ADD(1), STATUS_DEL(1), STATUS_MOD(2), STATUS_NON(3);

    private int index;

    // 构造方法
    EntityStatusEnum(int index) {
        this.index = index;
    }

    public int getIndex()
    {
        return this.index;
    }
}

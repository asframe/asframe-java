package com.asframe.game.db.entity;

import com.asframe.game.db.IPlayerEntity;

/**
 * 基础的和玩家相关的实体对象
 * @author sodaChen
 */
public class BasePlayerEntity extends BaseEntity implements IPlayerEntity
{
    /**
     * 玩家Id
     */
    protected Long playerId;

    @Override
    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}

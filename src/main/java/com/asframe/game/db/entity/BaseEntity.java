package com.asframe.game.db.entity;

import com.asframe.game.db.IEntity;

/**
 * @author sodaChen
 */
public class BaseEntity implements IEntity
{
    protected Long id;
    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}

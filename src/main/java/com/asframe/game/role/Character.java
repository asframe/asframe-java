package com.asframe.game.role;

/**
 * 游戏角色基础对象
 * @author sodaChen
 * @version 1.0
 * @date 2020/10/19
 */
public class Character implements ICharacter
{
    protected String name;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    protected long id;

    @Override
    public void setName(String name) {

        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

package com.asframe.game.fight.manager;

import com.asframe.game.fight.role.IFightRole;

/**
 * buff管理器。每个战斗角色都有一个buff管理器
 * @author sodaChen
 * Date:2020/10/20
 */
public class BufferManager
{
    /**
     * buff的作用对象
     */
    private IFightRole fightRole;
    public BufferManager(IFightRole fightRole)
    {
        this.fightRole = fightRole;
    }
}

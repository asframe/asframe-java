package com.asframe.game.fight.model;

import xgame.game.fight.role.RpgRole;

/**
 * 战斗过程记录的数据，包含了战斗结果，以及其他的临时计算数据
 * @author sodaChen
 */
public class FightProcessModel
{
    public RpgRole[] atkRoleList;
    /**
     * 防守方角色列表
     */
    public RpgRole[] dcRoleList;
}

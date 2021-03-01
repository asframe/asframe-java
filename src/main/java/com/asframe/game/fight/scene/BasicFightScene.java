package com.asframe.game.fight.scene;

import xgame.game.fight.role.RpgRole;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础的战斗场景
 * @author sodaChen
 * Date:2020/10/20
 */
public class BasicFightScene
{
    protected Map<Long, RpgRole> roleMap;

    public BasicFightScene()
    {
        this.roleMap = new HashMap<>();
    }
}

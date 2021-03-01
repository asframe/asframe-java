/**
 * @IBattleScene.java
 * @author sodaChen mail:asframe#qq.com
 * @version 1.0
 * <br>Copyright (C), 2012-present ASFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFame
 * <br>Date:2020-10-20
 */
package com.asframe.game.fight.scene;

import com.asframe.game.fight.role.IFightRole;

/**
 * 通用战斗场景接口
 * @author sodaChen
 * Date:2020/10/20
 */
public interface IFightScene {

    /**
     * 开始战斗
     */
    void startFight();
    /**
     * 添加一个战斗角色
     * @param fightRole
     * @return
     */
    boolean addFightRole(IFightRole fightRole);

    /**
     * 删除一个战斗角色
     * @param fightRole
     * @return
     */
    boolean removeFightRole(IFightRole fightRole);
}

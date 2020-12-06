/**
 * @ISkillEffect.java
 * @author sodaChen mail:asframe#qq.com
 * @version 1.0
 * <br>Copyright (C), 2012-present ASFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFame
 * <br>Date:2020-10-20
 */
package com.asframe.game.fight.effect;

import com.asframe.game.fight.result.ISkillEffectResult;
import com.asframe.game.fight.result.ISkillResult;
import com.asframe.game.fight.role.IFightRole;

/**
 * 技能效果处理接口
 * @author sodaChen
 * Date:2020/10/20
 */
public interface ISkillEffect
{
    /**
     * 执行的处理逻辑
     * @param ownRole
     * @param targetRole 技能攻击目标，或者利用目标的位置
     * @return
     */
    ISkillEffectResult execute(IFightRole ownRole, IFightRole targetRole);
}

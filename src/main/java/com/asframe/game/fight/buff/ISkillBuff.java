/**
 * @ISkillBuff.java
 * @author sodaChen mail:asframe#qq.com
 * @version 1.0
 * <br>Copyright (C), 2012-present ASFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFame
 * <br>Date:2020-10-20
 */
package com.asframe.game.fight.buff;

import com.asframe.game.fight.result.ISkillBuffResult;
import com.asframe.game.fight.result.ISkillResult;
import com.asframe.game.fight.role.IFightRole;

/**
 * 技能buff接口，每次产生一个buff。都会产生一个buff实例
 * 因为buff具备一些特殊的属性，以及需要保存接口，所以需要单独实例化
 * @author sodaChen
 * Date:2020/10/20
 */
public interface ISkillBuff {
    /**
     * 设置buff的策划配置数据
     * @param skillBuffModel
     */
    void setSkillBuffModel(ISkillBuffResult skillBuffModel);
    /**
     * 设置绑定的buff的战斗角色对象
     * @param fightRole
     */
    void setFightRole(IFightRole fightRole);

    /**
     * 执行一次buff效果，并产生结果
     * @return
     */
    ISkillBuffResult execute();
}

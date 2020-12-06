/**
 * @ISkill.java
 * @author sodaChen mail:asframe#qq.com
 * @version 1.0
 * <br>Copyright (C), 2012-present ASFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFame
 * <br>Date:2020-10-17
 */
package com.asframe.game.fight.skill;

import com.asframe.game.fight.result.ISkillResult;
import com.asframe.game.fight.role.IFightRole;

/**
 * 技能接口，定义了基础属性和接口
 * @author sodaChen
 * Date:2020/11/29
 */
public interface ISkill {
    /**
     * 执行的处理逻辑
     * @param ownRole
     * @param targetRole 技能攻击目标，或者利用目标的位置
     * @return
     */
    ISkillResult execute(IFightRole ownRole, IFightRole targetRole);
//    /**
//     * 执行的处理逻辑，这里主要是把目标查找功能放在客户端。一般是arpg是这样
//     * @param ownRole
//     * @param targetRoles 多名目标
//     * @return
//     */
//    ISkillResult execute(IFightRole ownRole,IFightRole[] targetRoles);
}

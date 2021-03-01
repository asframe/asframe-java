/**
 * @IFightRole.java
 * @author sodaChen mail:asframe#qq.com
 * @version 1.0
 * <br>Copyright (C), 2012-present ASFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFame
 * <br>Date:2020-10-20
 */
package com.asframe.game.fight.model;

/**
 * 战斗角色的数据模型，这是内存数据，动态计算的
 * 基类记录的是通用或者常用的属性值，其他的由子类具体扩展和实际使用
 * odds是机率，表示触发百分比
 * rate表示比率，表示增幅多少
 * 这2者都是万分比来统计的，所以最终结果必须除于10000
 * @author sodaChen
 * Date:2020/10/20
 */
public class FightRoleModel {
    /** 位置 **/
    public int index;
    /** 唯一id **/
    public long roleId;
    /** 名字 **/
    public String name;
    /** 当前血量 **/
    public int hp;
    /** 最大血量 **/
    public int maxHp;
    /** 当前能量 **/
    public int mp;
    /** 最大能量 **/
    public int maxMp;
    /** 攻击力 **/
    public int atk;
    /** 防御力 **/
    public int dc;
    /** 魔法攻击力 **/
    public int matk;
    /** 魔法防御力 **/
    public int mdc;

    /** 反击 **/
    public int counter;
    /** 反击伤害值 **/
    public int counterHarm;
    /** 反击增幅 **/
    public int counterRate;
    /** 反击几率 **/
    public int counterOdds;

    /** 连击 **/
    public int combo;
    /** 连击几率 **/
    public int comboOdds;

    /** 暴击值 **/
    public int critical;
    /** 暴击比例 **/
    public int criticalRate;
    /** 抵抗暴击几率 **/
    public int resistCriticalOdds;

    /** 命中值 **/
    public int hit;
    /** 闪避值 **/
    public int dodge;
    /** 命中率 **/
    public int hitRate;

    /** 格挡 **/
    public int parry;
    /** 格挡伤害 **/
    public int parryHarm;
    /** 格挡减伤 **/
    public int parryDc;

    /** 固定减伤 **/
    public int fixedLessHarm;
    /** 减伤百分比 **/
    public int lessHarmRate;

    /** 固定增伤 **/
    public int fixedAddHarm;
    /** 增伤百分比 **/
    public int addHarmRate;

    /** buff攻击力 **/
    public int buffAtk;
    /** buff攻击百分比 **/
    public int buffAtkRate;
    /** buff防御力 **/
    public int buffDc;
    /** buff防御力百分比 **/
    public int buffDcRate;


}

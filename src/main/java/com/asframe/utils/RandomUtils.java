/**
 * @RandomUtils.as
 * 
 * @author sodaChen mail:asframe#163.com
 * @version 1.0
 * <br>Copyright (C), 2012 ASFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:ASFrame
 * <br>Date:2012-1-19
 */
package com.asframe.utils;
/**
 * 随机数工具类
 * @author sodaChen
 * Date:2012-1-19
 */
public class RandomUtils
{
	/**
	 * 返回一个
	 * @param minNum
	 * @param maxNum
	 * @returns {number}
	 */
	public static double random(int minNum, int maxNum)
	{
		return minNum + Math.random() * (maxNum - minNum);
	}
	
	/**
	 * 随机获得int整数
	 * @param minNum:最小范围(0开始)
	 * @param maxNum:最大范围
	 * @param stepLen:增加范围（整数，默认为1）
	 * @return
	 *
	 */
	public static int randomInt(int minNum, int maxNum, int stepLen)
	{
		if (minNum > maxNum)
		{
			int nTemp = minNum;
			minNum = maxNum;
			maxNum = nTemp;
		}
		int nDeltaRange = (maxNum - minNum) + (1 * stepLen);
		double nRandomNumber = Math.random() * nDeltaRange;
		nRandomNumber += minNum;
		return (int)(Math.floor(nRandomNumber / stepLen) * stepLen);
	}
	/**
	 * 随机获得int整数  增加范围（整数，默认为1）
	 * @param minNum:最小范围(0开始)
	 * @param maxNum:最大范围
	 * @return
	 *
	 */
	public static int randomInt(int minNum, int maxNum)
	{
		return randomInt(minNum,maxNum,1);
	}
	/**
	 * 随机布尔值
	 * @return
	 *
	 */
	public static boolean randomBoolean()
	{
		return randomInt(1, 2,1) == 1;
	}
	/**
	 * 取得随机正负波动值(1 / -1)
	 * @return
	 *
	 */
	static int randomWave()
	{
		return randomBoolean() ? 1 : -1;
	}
}

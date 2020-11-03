/**
 * @ITimeEvent.java
 * 
 * @author sodaChen  E-mail:sujun10@21cn.com
 * @version 1.0
 * <br>Copyright (C), 2010 asFrame.com
 * <br>This program is protected by copyright laws.
 * <br>Program Name:TribeGameServer
 * <br>Date:2012-1-9
 */
package com.asframe.thread.event;
/**
 * 定时器接口
 * @author sodaChen
 * @date 2012-1-9
 *
 */
public interface ITimeEvent extends Runnable
{
	/**
	 * 是否已经取消了
	 * @return
	 */
	boolean isCanceled();

	/**
	 * 强行停止这个时间管理器
	 */
	void stop();

	/**
	 * 设置触发时间
	 * @param time
	 */
	void setTime(long time);

	/**
	 * 获取时间
	 * @return
	 */
	long getTime();
}

package com.asframe.thread.event;



import com.asframe.utils.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 基础定时器事件
 * @author sodaChen
 * @date 2012-1-9
 *
 */
public abstract class BaseTimeEvent implements ITimeEvent
{
	public static Log logger	= LogFactory.getLog(BaseTimeEvent.class);
	/** 单位毫秒 **/
	 long time;
	/** 是否取消 **/
	private boolean isCanceled = false;

	@Override
	public boolean isCanceled() 
	{
		return isCanceled;
	}

	@Override
	public void stop() {
		this.isCanceled = true;
	}
	public BaseTimeEvent()
	{

	}
	public BaseTimeEvent(long time)
	{
		super();
		this.time = time;
	}

	@Override
	public void setTime(long time) 
	{
		this.time = time;
	}

	@Override
	public long getTime() 
	{
		return time;
	}
	
	@Override
	public String toString() 
	{
		return this.getClass().getSimpleName() + DateUtil.toDate(time);
	}
}

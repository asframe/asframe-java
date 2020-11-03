package com.asframe.thread;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.asframe.thread.event.ITimeEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;



/**
 * 时间计划
 * @author sodaChen
 * @Date 2020/10/9
 */
@ManagedResource(description = "任务调度")
public class EventScheduler
{
	/**
	 * 用于存储单个时间点所有任务的对象
	 */
	private class ShedulerEntity {
		private final List<ITimeEvent> eventList = new LinkedList<ITimeEvent>();

		public ShedulerEntity(ITimeEvent event) {
			eventList.add(event);
		}

		public void addEvent(ITimeEvent event) {
			eventList.add(event);
		}
	}

	/**
	 * 用于对更新计数器的包装
	 * 
	 * @author sodaChen
	 * 
	 */
	class MyTask implements Runnable {
		private final ITimeEvent event;

		public MyTask(ITimeEvent task) {
			this.event = task;
			waitingJobCount.incrementAndGet();
		}

		@Override
		public void run() {
			long start = System.currentTimeMillis();

			if (!event.isCanceled()) {
				event.stop();// 防止再次进入
				try {
					event.run();
				} catch (Exception e) {
					log.error("执行event错误:",e);
				}
			}

			long end = System.currentTimeMillis();

			if (end - start > 1000) {
				log.warn("scheduler执行时间过长：" + event + "  " + (end - start)
						+ " ms");
			}
			waitingJobCount.decrementAndGet();
		}
	}

	/** log */
	private final static Log log = LogFactory.getLog(EventScheduler.class);

	/** 事件树 */
	private final NavigableMap<Long, ShedulerEntity> eventNavigableMap = new TreeMap<Long, ShedulerEntity>();

	private final Lock eventQueueLock = new ReentrantLock();

	/** 线程池 */
	private ExecutorService executorService;

	/** 调度计时器 */
	private final Timer timer = new Timer("Scheduler.Timer");

	private int executorLimit;

	/** 等待执行的事件的数量 */
	private final AtomicInteger waitingJobCount = new AtomicInteger();

	private final static EventScheduler instance = new EventScheduler();

	public static EventScheduler getInstance() {
		return instance;
	}

	private EventScheduler() 
	{

	}

	/**
	 * 增加事件
	 * 
	 * @param event
	 */
	public void addEvent(ITimeEvent event) {
		if (event.isCanceled()) {
			// ExceptionManager.getInstance().recordTrace("准备增加的事件是已经被去取消的 " +
			// event.getClass().getSimpleName());
			return ;
		}

		if (event.getTime() < 0) {
			log.warn("加入了时间小于0的时间  event=" + event.getClass().getSimpleName());
			event.setTime(System.currentTimeMillis());
		}

		long key = event.getTime() / 1000;

		try {
			this.eventQueueLock.lock();
			ShedulerEntity en = this.eventNavigableMap.get(key);
			if (en == null) {
				en = new ShedulerEntity(event);
				eventNavigableMap.put(key, en);
			} else {
				// 如果有同时间的事件则加到该时间点的list中
				en.addEvent(event);
			}

			if (log.isDebugEnabled()) {
				log.debug("addEvent " + event.toString() + " queue size=" + eventNavigableMap.size());
			}

		} finally {
			this.eventQueueLock.unlock();
		}
	}

	public ITimeEvent removeEvent()
	{
		return null;
	}

	@ManagedAttribute(description = "未到时间的事件数")
	public int getEventQueueSize() {
		int res = 0;
		try {
			eventQueueLock.lock();
			for (ShedulerEntity en : this.eventNavigableMap.values()) {
				res += en.eventList.size();
			}
		} finally {
			eventQueueLock.unlock();
		}
		return res;
	}

	@ManagedAttribute(description = "已到时间，等待执行的事件数")
	public int getExecuteQueueSize() {
		return this.waitingJobCount.get();
	}

	@ManagedAttribute(description = "获得第一个任务")
	public String getFirstEvent() {
		try {
			eventQueueLock.lock();
			if (this.eventNavigableMap.isEmpty()) {
				return "没有事件";
			} else {
				StringBuffer sb = new StringBuffer();

				Map.Entry<Long, ShedulerEntity> en = eventNavigableMap
						.firstEntry();
				SimpleDateFormat ft = new SimpleDateFormat("MM-dd HH:mm:ss ");
				sb.append(ft.format(new Date(en.getKey() * 1000)));

				Runnable[] arr = en.getValue().eventList
						.toArray(new Runnable[0]);
				for (int i = 0; i < arr.length; i++) {
					sb.append(arr[i].toString()).append(" ");
				}
				return sb.toString();
			}
		} finally {
			eventQueueLock.unlock();
		}
	}

	/**
	 * 初始化
	 */
	public void init() 
	{
		if (log.isDebugEnabled()) 
		{
			log.info("Scheduler inited, executorLimit=" + this.executorLimit);
		}
		this.executorService = Executors.newFixedThreadPool(this.executorLimit);

	}

	public void setExecutorLimit(int executorLimit) {
		this.executorLimit = executorLimit;
	}

	@ManagedOperation(description = "shutdown")
	public void shutdownNow(String pwd) {
		if ("2".equals(pwd)) {
			this.shutdown();
		}
	}

	public void shutdown() {
		log.info("EventScheduler begin shutdown, 剩余待执行事件数:"
				+ this.waitingJobCount.get());

		this.timer.cancel();

		this.executorService.shutdown();
		try {
			// 5分钟如果不能停止就强行停止
			if (!this.executorService.awaitTermination(10, TimeUnit.MINUTES)) {
				this.executorService.shutdownNow();
				if (!this.executorService.awaitTermination(1, TimeUnit.MINUTES)) {
					log.error("EventScheduler shutdown 失败");
				}
			}
		} catch (InterruptedException e) {
//			LogUtils.error(e);
			this.executorService.shutdownNow();
			Thread.currentThread().interrupt();
		}
		log.info("EventScheduler shutdown complete");
	}

	/**
	 * 开始执行
	 */
	public void start() {
		if (log.isDebugEnabled()) {
			log.debug("start");
		}

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				try {
					checkEventFromMap();
				} catch (Exception e) {
//					LogUtils.error(e);
				}
			}
		};

		this.timer.schedule(task, 0, 1000);
	}

	/**
	 * 检查是否有到点的任务，如果有，就执行
	 */
	private void checkEventFromMap() {
		// 取出第一个
		eventQueueLock.lock();
		try {
			if (!eventNavigableMap.isEmpty()) {
				Map.Entry<Long, ShedulerEntity> en = eventNavigableMap
						.firstEntry();
				while (en != null
						&& en.getKey() <= (System.currentTimeMillis() / 1000)) {
					// 如果到了该触发的时间，则把该时间点的事件都加入到事件执行队列中
					Iterator<ITimeEvent> it = en.getValue().eventList.iterator();
					while (it.hasNext()) {
						this.execEvent(it.next());
					}
					eventNavigableMap.pollFirstEntry();
					en = eventNavigableMap.firstEntry();
				}
			}
		} finally {
			eventQueueLock.unlock();
		}
	}

	/**
	 * 执行事件
	 * 
	 * @param event
	 */
	private void execEvent(ITimeEvent event) {
		this.executorService.execute(new MyTask(event));
	}

}

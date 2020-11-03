package com.asframe.thread;

import com.asframe.utils.ErrorLogUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池对象，封装了通用简单的线程池对象
 * @author  sodaChen
 * @Date 2020/10/9
 */
@ManagedResource(description = "后台线程池")
public class BackExecutorService
{
	private final static Logger logger = LogManager.getLogger(BackExecutorService.class);



	private String threadName;
	private int executorLimit;
	/**
	 * 计数器
	 */
	private final AtomicInteger counter = new AtomicInteger();
	/**
	 * 线程池，专门处理一些后台业务线程
	 */
	private ExecutorService executorService;



	public BackExecutorService(String threadName)
	{
		this.executorLimit = 1;
		this.threadName = threadName;
	}
	public BackExecutorService(String threadName,int executorLimit)
	{
		this.threadName = threadName;
		this.executorLimit = executorLimit;
	}

	/**
	 * 初始化线程池
	 */
	public void init()
	{
		this.executorService = new ThreadPoolExecutor(executorLimit, executorLimit,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(),new NameThreadFactory(this.threadName));
	}

	@ManagedAttribute(description = "正在执行的任务数量")
	public int getTaskCount() 
	{
		return counter.get();
	}
	class TheadTask implements Runnable {
		private final Runnable task;

		public TheadTask(Runnable task) {
			this.task = task;
			counter.incrementAndGet();
		}

		@Override
		public void run()
		{
			long t = System.currentTimeMillis();
			try
			{
				this.task.run();
			} catch (Exception e)
			{
				logger.error(ErrorLogUtil.errorTrackSpaceString(e));
			}
			counter.decrementAndGet();
			long tt = System.currentTimeMillis() - t;
			if (tt > 2000)
			{
				logger.warn("BackExecutorService: 任务执行时间过长:" + tt + "ms " + task.toString());
			}
		}
	}

	/**
	 * 关闭线程池
	 */
	public void shutdown() {

		logger.info("BackExecutorService begin shutdown, 剩余任务数量:" + this.counter.get());

		this.executorService.shutdown();
		try
		{
			// 1分钟如果不能停止就强行停止
			if (!this.executorService.awaitTermination(6000, TimeUnit.MINUTES))
			{
				this.executorService.shutdownNow();
				if (!this.executorService.awaitTermination(1, TimeUnit.MINUTES))
				{
					logger.error("BackExecutorService shutdown 失败");
				}
			}
		} catch (Exception e)
		{
			logger.error(ErrorLogUtil.errorTrackSpaceString(e));
			this.executorService.shutdownNow();
			Thread.currentThread().interrupt();
		}
		logger.info("BackExecutorService shutdown complete");
	}

	/**
	 * 执行业务到后台线程
	 * @param task
	 */
	public void executeTask(Runnable task) {
		if (logger.isDebugEnabled()) {
			logger.debug("executeTask(). task=" + task.toString());
		}
		this.executorService.execute(new TheadTask(task));
	}
}
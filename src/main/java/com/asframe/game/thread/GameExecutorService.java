package com.asframe.game.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * 线程池对象，里面封装了广播线程和业务处理线程
 * @author  sodaChen
 * @date 2020.10.10
 */
@ManagedResource(description = "线程池")
public class GameExecutorService 
{
	private final static Log log = LogFactory.getLog(GameExecutorService.class);

	private final static GameExecutorService instance = new GameExecutorService();

	private int executorLimit;
	private int reportExecutorLimit;

	/**
	 * 计数器
	 */
	private final AtomicInteger counter = new AtomicInteger();
	/**
	 * 游戏业务线程池，专门处理一些后台业务线程，非及时性的业务逻辑处理
	 */
	private ExecutorService executorService;
	/**
	 * 广播线程池
	 */
	private ExecutorService reportExecutorService;


	private GameExecutorService() 
	{
	}

	public void init() {
		this.executorService = Executors.newFixedThreadPool(executorLimit);
		this.reportExecutorService = Executors.newFixedThreadPool(reportExecutorLimit);
	}

	@ManagedAttribute(description = "正在执行的任务数量")
	public int getTaskCount() 
	{
		return counter.get();
	}
	class MyTask implements Runnable {
		private final Runnable task;

		public MyTask(Runnable task) {
			this.task = task;
			counter.incrementAndGet();
		}

		@Override
		public void run() {
			long t1 = System.currentTimeMillis();
			try {
				this.task.run();
			} catch (RuntimeException e) 
			{
//				LogUtils.error(e);
			}
			counter.decrementAndGet();
			long t2 = System.currentTimeMillis() - t1;
			if (t2 > 4000) {
				log.warn("GameExecutorService: 任务执行时间过长:" + t2 + "ms " + task.toString());
			}
		}
	}

	private void shutdownReport() {
		this.reportExecutorService.shutdown();
		try {
			// 5分钟如果不能停止就强行停止
			if (!this.reportExecutorService.awaitTermination(10, TimeUnit.MINUTES)) {
				this.reportExecutorService.shutdownNow();
				if (!this.reportExecutorService.awaitTermination(1, TimeUnit.MINUTES)) {
					log.error("reportExecutorService shutdown 失败");
				}
			}
		} catch (InterruptedException e) {
//			LogUtils.error(e);
			this.reportExecutorService.shutdownNow();
			Thread.currentThread().interrupt();
		}
		log.info("reportExecutorService shutdown complete");
	}

	public void shutdown() {
		log.info("GameExecutorService begin shutdown, 剩余任务数量:"
				+ this.counter.get());
		
		this.shutdownReport();
		
		this.executorService.shutdown();
		try {
			// 5分钟如果不能停止就强行停止
			if (!this.executorService.awaitTermination(10, TimeUnit.MINUTES)) {
				this.executorService.shutdownNow();
				if (!this.executorService.awaitTermination(1, TimeUnit.MINUTES)) {
					log.error("GameExecutorService shutdown 失败");
				}
			}
		} catch (InterruptedException e) {
//			LogUtils.error(e);
			this.executorService.shutdownNow();
			Thread.currentThread().interrupt();
		}
		log.info("GameExecutorService shutdown complete");
	}

	/**
	 * 执行游戏业务的后台线程
	 * @param task
	 */
	public void executeTask(Runnable task) {
		if (log.isDebugEnabled()) {
			log.debug("executeTask(). task=" + task.toString());
		}
		this.executorService.execute(new MyTask(task));
	}

	/**
	 * 专门处理大量广播的线程（比如对全服的人广播信息，聊天之类的）
	 * @param task
	 */
	public void executeReportTask(Runnable task) {
		this.reportExecutorService.execute(task);
	}

	public void setExecutorLimit(int executorLimit) {
		this.executorLimit = executorLimit;
	}
	

	public void setReportExecutorLimit(int reportExecutorLimit)
	{
		this.reportExecutorLimit = reportExecutorLimit;
	}

	public static GameExecutorService getInstance() {
		return instance;
	}
}
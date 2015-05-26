package com.edu.crawler.slit.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.crawler.slit.resource.pool.FetchedNodePoolManager;
import com.edu.crawler.slit.resource.pool.FetchedPagePoolManager;
import com.edu.crawler.slit.resource.pool.UrlWaitingPoolManager;
import com.edu.crawler.slit.thread.pool.SlitMultiThreadPool;

/**
 * @author qifeifei
 * 
 */
@SuppressWarnings("static-access")
public class SlitFinishMonitor implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(SlitFinishMonitor.class);

	public static volatile boolean isFinish = false;

	/** 每30s检查一次所有队列 */
	private final long monitorPerTime = 30000;
	private Integer sumZeroTimes = 0;

	@Override
	public void run() {
		while (!isFinish) {
			try {
				Thread.currentThread().sleep(monitorPerTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (FetchedPagePoolManager.size() <= 0 && FetchedNodePoolManager.size() <= 0 && UrlWaitingPoolManager.size() <= 0) {
				sumZeroTimes++;
			} else {
				sumZeroTimes = 0;
				LOG.debug("FetchedPagePoolManager 大小：[{}]", FetchedPagePoolManager.size());
				LOG.debug("FetchedNodePoolManager 大小:[{}]", FetchedNodePoolManager.size());
				LOG.debug("UrlWaitingPoolManager 大小：[{}]", UrlWaitingPoolManager.size());
			}

			if (sumZeroTimes == 3) {
				isFinish = true;
				SlitMultiThreadPool.shutDown();
				LOG.debug("[{}] 设置程序结束", Thread.currentThread().getName());
			}
		}
	}

}

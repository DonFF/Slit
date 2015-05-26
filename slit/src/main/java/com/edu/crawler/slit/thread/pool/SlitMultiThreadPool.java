package com.edu.crawler.slit.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author qifeifei
 *
 */
public class SlitMultiThreadPool {
	private static final ExecutorService pool = new ThreadPoolExecutor(30, 100, 3000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(2000));

	public static Future<?> addToPool(Runnable command) {
		return pool.submit(command);
	}

	public static void shutDown() {
		pool.shutdown();
	}
}

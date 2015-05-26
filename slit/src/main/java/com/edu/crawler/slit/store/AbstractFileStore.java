package com.edu.crawler.slit.store;

import com.edu.crawler.slit.monitor.SlitFinishMonitor;

/**
 * @author qifeifei
 *
 */
public abstract class AbstractFileStore implements FileStore, Runnable {

	private final long storePerTime = 5000l;

	@Override
	public void run() {
		while (!SlitFinishMonitor.isFinish) {
			this.processResultNode();
			try {
				Thread.sleep(storePerTime);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName());
				e.printStackTrace();
			}
		}
	}

}

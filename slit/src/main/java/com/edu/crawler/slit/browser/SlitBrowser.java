package com.edu.crawler.slit.browser;

import java.io.File;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.crawler.slit.analyze.Analyzer;
import com.edu.crawler.slit.config.Config;
import com.edu.crawler.slit.fetch.browser.AbstractBrowserAction;
import com.edu.crawler.slit.fetch.browser.BrowserFactory;
import com.edu.crawler.slit.monitor.SlitFinishMonitor;
import com.edu.crawler.slit.store.AbstractFileStore;
import com.edu.crawler.slit.thread.pool.SlitMultiThreadPool;

/**
 * @author qifeifei
 *
 */
public class SlitBrowser {

	private static final Logger LOG = LoggerFactory.getLogger(SlitBrowser.class);
	
	private Config config;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public SlitBrowser() {

	}

	public SlitBrowser(Config config) {
		this.config = config;
	}

	public void start() {
		if (BooleanUtils.isFalse(this.checkBeforeStart(config))) {
			LOG.debug("config 配置不正确，不能启动");
			return;
		}
		// ready for browser driver
		BrowserFactory.browserDriverPath = config.browserDriverPath;
		// ready for analyzer
		Analyzer analyzer = new Analyzer(this.config);
		for (int i = 0; i < config.getAnalyzeThreads(); i++) {
			SlitMultiThreadPool.addToPool(new Thread(analyzer));
		}
		// ready for store
		AbstractFileStore store = null;
		try {
			store = config.getFileStoreClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < config.getStoreThreads(); i++) {
			SlitMultiThreadPool.addToPool(new Thread(store));
		}
		// ready for user action
		AbstractBrowserAction action = null;
		try {
			action = config.getBrowserAction().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != action) {
			action.userBrowserAction();
		}
		// ready for monitor
		SlitFinishMonitor monitor = new SlitFinishMonitor();
		SlitMultiThreadPool.addToPool(new Thread(monitor));
	}

	// 私有, 启动前检查配置
	private boolean checkBeforeStart(Config config) {
		if (null == config) {
			return false;
		}
		if (!config.isInBrowser()) {
			LOG.debug("SlitBrowser初始化失败，请设重新设置参数inBrowser");
			return false;
		}
		if (null == config.getBrowserAction()) {
			LOG.debug("SlitBrowser初始化失败，Config中请定义用户浏览器行为类");
		}
		if (StringUtils.isBlank(config.getEncodeSetting())) {
			LOG.debug("页面编码不能为空");
			return false;
		}
		if (null == config.getFetchThreads() || config.getFetchThreads() < 1) {
			LOG.debug("下载线程数不正确");
			return false;
		}
		if (null == config.getAnalyzeThreads() || config.getAnalyzeThreads() < 1) {
			LOG.debug("分析线程数不正确");
			return false;
		}
		if (null == config.getStoreThreads() || config.getStoreThreads() < 1) {
			LOG.debug("存储线程数不正确");
			return false;
		}
		if (null == config.getMaxDepth() || config.getMaxDepth() < 1) {
			LOG.debug("爬虫深度不正确");
			return false;
		}
		if (null == config.getNodeFilterClass() || null == config.getFileStoreClass()) {
			LOG.debug("爬虫过滤器和存储器不正确");
			return false;
		}
		if (StringUtils.isBlank(config.getBrowserDriverPath())) {
			LOG.debug("没有设置浏览器对应的驱动路径");
			return false;
		} else {
			File driverFile = new File(config.getBrowserDriverPath());
			if (!driverFile.exists()) {
				LOG.debug("浏览器驱动没有找动！");
				return false;
			}
		}
		return true;
	}
}

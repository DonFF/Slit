package com.edu.crawler.slit;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.crawler.slit.analyze.Analyzer;
import com.edu.crawler.slit.config.Config;
import com.edu.crawler.slit.fetch.Fetcher;
import com.edu.crawler.slit.monitor.SlitFinishMonitor;
import com.edu.crawler.slit.resource.pool.UrlWaitingPoolManager;
import com.edu.crawler.slit.store.AbstractFileStore;
import com.edu.crawler.slit.thread.pool.SlitMultiThreadPool;
import com.edu.crawler.slit.url.SlitUrl;

/**
 * @author qifeifei
 *
 */
public class Slit {
	private static final Logger LOG = LoggerFactory.getLogger(Slit.class);
	
	private Config config;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Slit() {
	}

	public Slit(Config config) {
		this.config = config;
	}

	public void start() {
		if (BooleanUtils.isFalse(this.checkBeforeStart(config))) {
			LOG.debug("config 配置不正确，不能启动");
			return;
		}
		List<String> startUrlList = config.getStartUrlList();
		// ready for start url list
		for (String webUrl : startUrlList) {
			if (StringUtils.isBlank(webUrl)) {
				continue;
			}
			SlitUrl slitUrl = new SlitUrl();
			slitUrl.setDepth(1);
			slitUrl.setWebUrl(webUrl);
			UrlWaitingPoolManager.add(slitUrl);
		}
		// ready for fetcher
		Fetcher fetcher = new Fetcher(this.config);
		for (int i = 0; i < config.getFetchThreads(); i++) {
			SlitMultiThreadPool.addToPool(new Thread(fetcher));
		}
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
		if (null != store) {
			for (int i = 0; i < config.getStoreThreads(); i++) {
				SlitMultiThreadPool.addToPool(new Thread(store));
			}
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
		if (config.isInBrowser()) {
			LOG.debug("Slit初始化失败，不能使用浏览器，请设重新设置参数inBrowser");
			return false;
		}
		if (StringUtils.isBlank(config.getUserAgent())) {
			LOG.debug("userAgent 不能为空");
			return false;
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
		if (CollectionUtils.isEmpty(config.getStartUrlList())) {
			LOG.debug("爬虫页面深度不正确");
			return false;
		}
		if (null == config.getNodeFilterClass() || null == config.getFileStoreClass()) {
			LOG.debug("爬虫过滤器和存储器不正确");
			return false;
		}
		return true;
	}
}

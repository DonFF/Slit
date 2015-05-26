package com.edu.crawler.slit.fetch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.crawler.slit.config.Config;
import com.edu.crawler.slit.monitor.SlitFinishMonitor;
import com.edu.crawler.slit.resource.pool.FetchedPagePoolManager;
import com.edu.crawler.slit.resource.pool.UrlWaitingPoolManager;
import com.edu.crawler.slit.url.SlitUrl;

/**
 * @author qifeifei
 * 
 */
public class Fetcher implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(Fetcher.class);
	//private final static Pattern domainPt = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)");

	private Config config;

	public Fetcher() {
	}

	public Fetcher(Config config) {
		this.config = config;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	// 私有, fetch
	private void fetch() {
		SlitUrl slitUrl = UrlWaitingPoolManager.poll();
		if (null == slitUrl || StringUtils.isBlank(slitUrl.getWebUrl()) || slitUrl.getDepth() > config.maxDepth) {
			return;
		}
		if (!StringUtils.contains(slitUrl.getWebUrl(), "http")) {
			LOG.debug("{}: is not a complete url", slitUrl.getWebUrl());
			return;
		}
		LOG.debug("fetcher 准备下载页面 [{}] | depth 是 [{}]", slitUrl.getWebUrl(), slitUrl.getDepth());
		try {
			// dns cache could tackle here
			URL url = new URL(slitUrl.getWebUrl());
			URLConnection urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setReadTimeout(7000);
			urlConn.setRequestProperty("User-agent", config.getUserAgent());
			InputStreamReader isr = new InputStreamReader(urlConn.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String s = null;
			StringBuffer htmlContent = new StringBuffer();
			while ((s = br.readLine()) != null) {
				htmlContent = htmlContent.append(s);
			}
			if (StringUtils.isBlank(htmlContent.toString())) {
				return;
			}
			FetchedPage fp = new FetchedPage(); // 保存页面信息
			fp.setWebUrl(slitUrl.getWebUrl());
			fp.setPageHtml(htmlContent.toString());
			fp.setCurrentDepth((slitUrl.getDepth() == null || slitUrl.getDepth() < 1) ? 1 : slitUrl.getDepth());
			FetchedPagePoolManager.add(fp);
			br.close();
			isr.close();
		} catch (Exception e) {
			LOG.debug("Fetcher 下载指定url失败 :[{}]", slitUrl.getWebUrl());
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (!SlitFinishMonitor.isFinish) {
			this.fetch();
		}
	}

}

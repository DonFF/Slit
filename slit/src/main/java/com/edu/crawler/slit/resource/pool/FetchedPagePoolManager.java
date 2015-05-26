package com.edu.crawler.slit.resource.pool;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.crawler.slit.fetch.FetchedPage;

/**
 * @author qifeifei
 * 
 */
public class FetchedPagePoolManager {
	private static final Logger LOG = LoggerFactory.getLogger(FetchedPagePoolManager.class);

	private static ConcurrentLinkedQueue<FetchedPage> fetchedPage = new ConcurrentLinkedQueue<>();

	public static void add(FetchedPage fetchPage) {
		if (null == fetchPage || StringUtils.isBlank(fetchPage.getPageHtml())) {
			return;
		}
		fetchedPage.add(fetchPage);
		LOG.debug("FetchedPagePoolManager 当前队列中页面数：[{}]", fetchedPage.size());
	}

	public static FetchedPage poll() {
		return fetchedPage.poll();
	}

	public static Integer size() {
		return fetchedPage.size();
	}
}

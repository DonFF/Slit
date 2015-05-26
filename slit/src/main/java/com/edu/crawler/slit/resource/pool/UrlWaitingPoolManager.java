package com.edu.crawler.slit.resource.pool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.crawler.slit.url.SlitUrl;

/**
 * @author qifeifei
 * 
 */
public class UrlWaitingPoolManager {
	private static final Logger LOG = LoggerFactory.getLogger(UrlWaitingPoolManager.class);

	private static ConcurrentLinkedQueue<SlitUrl> waiting = new ConcurrentLinkedQueue<>();
	private static Set<String> visited = new HashSet<>();

	public static void add(SlitUrl slitUrl) {
		if (null == slitUrl || StringUtils.isBlank(slitUrl.getWebUrl())) {
			return;
		}
		if (visited.contains(slitUrl.getWebUrl())) {
			return;
		}
		waiting.add(slitUrl);
		visited.add(slitUrl.getWebUrl());
		LOG.debug("UrlWaitingPoolManager add link==>[{}] | depth ==> [{}] | 队列中还有等待url数:[{}]", slitUrl.getWebUrl(), slitUrl.getDepth(), waiting.size());
	}

	public static SlitUrl poll() {
		return waiting.poll();
	}

	public static Integer size() {
		return waiting.size();
	}

}

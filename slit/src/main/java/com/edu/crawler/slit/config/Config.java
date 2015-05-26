package com.edu.crawler.slit.config;

import java.util.List;

import com.edu.crawler.slit.fetch.browser.AbstractBrowserAction;
import com.edu.crawler.slit.node.filter.AbstractNodeFilter;
import com.edu.crawler.slit.store.AbstractFileStore;

/**
 * @author qifeifei
 *
 */
public class Config {

	public String userAgent = "Mozilla/4.0";
	// 页面编码
	public String encodeSetting = "utf-8";
	// 下载模块线程数
	public Integer fetchThreads = 1;
	// 分析模块线程数
	public Integer analyzeThreads = 1;
	// 存储模块线程数
	public Integer storeThreads = 1;
	// 深度数量
	public Integer maxDepth = 1;
	// 开始url列表
	public List<String> startUrlList;
	// 提取页面元素class
	public Class<? extends AbstractNodeFilter> nodeFilterClass;
	// 存储结果的class
	public Class<? extends AbstractFileStore> fileStoreClass;
	// 是否在浏览器中打开
	public boolean inBrowser = false;
	// 用户定义浏览器行为class
	public Class<? extends AbstractBrowserAction> browserAction;
	// 当前系统和浏览器对应的驱动文件路径,chrom下载地址：http://chromedriver.storage.googleapis.com/index.html
	public String browserDriverPath;

	public Config() {
	}

	public Config(Integer depth, List<String> startUrlList, Class<? extends AbstractNodeFilter> nodeFilterClass,
			Class<? extends AbstractFileStore> fileStoreClass) {
		this.maxDepth = depth;
		this.startUrlList = startUrlList;
		this.nodeFilterClass = nodeFilterClass;
		this.fileStoreClass = fileStoreClass;
	}

	public Config(String userAgent, String encodeSetting, Integer fetchThreads, Integer analyzeThreads, Integer storeThreads, Integer depth,
			Class<? extends AbstractNodeFilter> nodeFilterClass, List<String> startUrlList, Class<? extends AbstractFileStore> fileStoreClass,
			boolean inBrowser, Class<? extends AbstractBrowserAction> browserAction, String browserDriverPath) {
		this.userAgent = userAgent;
		this.encodeSetting = encodeSetting;
		this.fetchThreads = fetchThreads;
		this.analyzeThreads = analyzeThreads;
		this.storeThreads = storeThreads;
		this.maxDepth = depth;
		this.nodeFilterClass = nodeFilterClass;
		this.startUrlList = startUrlList;
		this.fileStoreClass = fileStoreClass;
		this.inBrowser = inBrowser;
		this.browserAction = browserAction;
		this.browserDriverPath = browserDriverPath;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getEncodeSetting() {
		return encodeSetting;
	}

	public void setEncodeSetting(String encodeSetting) {
		this.encodeSetting = encodeSetting;
	}

	public Integer getFetchThreads() {
		return fetchThreads;
	}

	public void setFetchThreads(Integer fetchThreads) {
		this.fetchThreads = fetchThreads;
	}

	public Integer getAnalyzeThreads() {
		return analyzeThreads;
	}

	public void setAnalyzeThreads(Integer analyzeThreads) {
		this.analyzeThreads = analyzeThreads;
	}

	public Integer getStoreThreads() {
		return storeThreads;
	}

	public void setStoreThreads(Integer storeThreads) {
		this.storeThreads = storeThreads;
	}

	public Integer getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(Integer maxDepth) {
		this.maxDepth = maxDepth;
	}

	public Class<? extends AbstractNodeFilter> getNodeFilterClass() {
		return nodeFilterClass;
	}

	public void setNodeFilterClass(Class<? extends AbstractNodeFilter> nodeFilterClass) {
		this.nodeFilterClass = nodeFilterClass;
	}

	public List<String> getStartUrlList() {
		return startUrlList;
	}

	public void setStartUrlList(List<String> startUrlList) {
		this.startUrlList = startUrlList;
	}

	public Class<? extends AbstractFileStore> getFileStoreClass() {
		return fileStoreClass;
	}

	public void setFileStoreClass(Class<? extends AbstractFileStore> fileStoreClass) {
		this.fileStoreClass = fileStoreClass;
	}

	public boolean isInBrowser() {
		return inBrowser;
	}

	public void setInBrowser(boolean inBrowser) {
		this.inBrowser = inBrowser;
	}

	public Class<? extends AbstractBrowserAction> getBrowserAction() {
		return browserAction;
	}

	public void setBrowserAction(Class<? extends AbstractBrowserAction> browserAction) {
		this.browserAction = browserAction;
	}

	public String getBrowserDriverPath() {
		return browserDriverPath;
	}

	public void setBrowserDriverPath(String browserDriverPath) {
		this.browserDriverPath = browserDriverPath;
	}

}

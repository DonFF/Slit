package com.edu.crawler.slit.fetch;

/**
 * @author qifeifei
 *
 */
public class FetchedPage {

	private String webUrl;
	private String pageHtml;
	private Integer currentDepth;

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getPageHtml() {
		return pageHtml;
	}

	public void setPageHtml(String pageHtml) {
		this.pageHtml = pageHtml;
	}

	public Integer getCurrentDepth() {
		return currentDepth;
	}

	public void setCurrentDepth(Integer currentDepth) {
		this.currentDepth = currentDepth;
	}

}

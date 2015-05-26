package com.edu.crawler.slit.browser.test;

import com.edu.crawler.slit.config.Config;
import com.edu.crawler.slit.util.InitSlit;

public class MyBrowserTest {

	/**
	 * 使用浏览器时候，fetcher只能是1，初始访问url也没有
	 * */
	public static void main(String[] args) {
		Config config = new Config();
		config.setNodeFilterClass(BrowserNodeFilter.class);
		config.setFileStoreClass(BrowserFileStore.class);
		config.setInBrowser(true);
		config.setBrowserAction(BrowserAction.class);
		config.setBrowserDriverPath("/home/qifeifei/work/git/tz-jn/visa/tops-visa/tops-visa-crawler/src/main/resources/properties/chromedriver");

		InitSlit init = new InitSlit(config);
		init.start();

	}

}

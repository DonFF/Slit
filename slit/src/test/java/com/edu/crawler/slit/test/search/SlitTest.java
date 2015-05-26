package com.edu.crawler.slit.test.search;

import java.util.Arrays;

import org.junit.Test;

import com.edu.crawler.slit.config.Config;
import com.edu.crawler.slit.util.InitSlit;

public class SlitTest {

	@Test
	public void testSlit() {
	}

	public static void main(String[] args) throws InterruptedException {
		Config config = new Config();
		config.setFetchThreads(4);
		config.setMaxDepth(5);
		config.setStartUrlList(Arrays.asList("https://git.oschina.net/explore/recommend"));
		config.setNodeFilterClass(MyNodeFilter.class);
		config.setFileStoreClass(MyFileStore.class);

		InitSlit slit = new InitSlit(config);
		slit.start();
	}
}

// 测试 ：http://www.ivsky.com/tupian/geguo_guoqi_v1773/index_2.html
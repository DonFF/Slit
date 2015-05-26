package com.edu.crawler.slit.util;

import com.edu.crawler.slit.Slit;
import com.edu.crawler.slit.browser.SlitBrowser;
import com.edu.crawler.slit.config.Config;

/**
 * @author qifeifei
 * 
 */
public class InitSlit {

	private Config config;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public InitSlit() {
	}

	public InitSlit(Config config) {
		this.config = config;
	}

	public void start() {
		if (null != this.config && !this.config.inBrowser) {
			Slit slit = new Slit(this.config);
			slit.start();
		} else if (null != this.config && this.config.inBrowser) {
			SlitBrowser slitB = new SlitBrowser(this.config);
			slitB.start();
		}
	}

}

package com.edu.crawler.slit.fetch.browser;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import com.edu.crawler.slit.fetch.FetchedPage;
import com.edu.crawler.slit.resource.pool.FetchedPagePoolManager;
/**
 * @author qifeifei
 *
 */
public abstract class AbstractBrowserAction implements BrowserAction {

	protected WebDriver webDriver = BrowserFactory.getWebDriver();

	@Override
	public void savePageSource(String pageSource) {
		if (StringUtils.isBlank(pageSource)) {
			return;
		}
		FetchedPage fpage = new FetchedPage();
		fpage.setPageHtml(pageSource);
		FetchedPagePoolManager.add(fpage);
	}

	/** 关闭当前浏览器 */
	public void closeAll() {
		webDriver.quit();
//		Set<String> allWinHandle = webDriver.getWindowHandles();
//		for (String h : allWinHandle) {
//			try {
//				webDriver.switchTo().window(h);
//				webDriver.close();
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

}

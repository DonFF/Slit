package com.edu.crawler.slit.browser.test;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.edu.crawler.slit.fetch.browser.AbstractBrowserAction;

public class BrowserAction extends AbstractBrowserAction {

	@Override
	public void userBrowserAction() {
		WebDriver wd = this.webDriver;
		wd.get("http://www.iteye.com/");
		WebElement loginElement = wd.findElement(By.className("welcome"));
		loginElement.click();
		this.sleepTime(1000);
		wd.findElement(By.id("user_name")).sendKeys("qifeifei@live.cn");
		this.sleepTime(3000);
		//wd.findElement(By.id("password")).sendKeys("");
		//TODO, 验证码
		wd.findElement(By.id("button")).click();
		this.sleepTime(1000);
		wd.findElement(By.linkText("博客")).click();
		this.sleepTime(1000);
		// 找200页博客
		for (int pageIndex = 0; pageIndex < 1; pageIndex++) {
			String fatherHandle = wd.getWindowHandle();

			List<WebElement> elements = wd.findElements(By.className("content"));
			int pageContentSize = elements.size();
			
			for(int contentIndex=0;contentIndex<pageContentSize;contentIndex++){
				WebElement hElement = wd.findElements(By.className("content")).get(contentIndex).findElement(By.tagName("h3"));
				
				if(hElement == null){
					continue;
				}
				List<WebElement> aLinkElent = hElement.findElements(By.tagName("a"));
				for(WebElement ae : aLinkElent){
					if(StringUtils.isBlank(ae.getAttribute("target"))){
						continue;
					}
					ae.click();
					/////////////////////////////change window
					// 已经打开所有
					Set<String> allWinHandle = wd.getWindowHandles();
					for (String h : allWinHandle) {
						try {
							if (StringUtils.equals(h, fatherHandle)) {
								continue;
							}
							wd.switchTo().window(h);
							this.sleepTime(500);
							this.savePageSource(wd.getPageSource());
							wd.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					/////////////////////////////save and go back
					wd.switchTo().window(fatherHandle);
					break;
				}
			}
			wd.switchTo().window(fatherHandle);
			wd.findElement(By.className("next_page")).click();
			this.sleepTime(1000);
		}
		wd.quit();
	}

	// sleep
	@SuppressWarnings("static-access")
	private void sleepTime(long time) {
		if (time < 0) {
			return;
		}
		try {
			Thread.currentThread().sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

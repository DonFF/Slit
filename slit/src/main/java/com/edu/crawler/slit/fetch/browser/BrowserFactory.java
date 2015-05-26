package com.edu.crawler.slit.fetch.browser;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author qifeifei
 *
 */
public class BrowserFactory {

	// singleton
	private static WebDriver webDriver = null;
	private static Object lockObj = new Object();
	public static String browserDriverPath;

	public static WebDriver getWebDriver() {
		if (null == webDriver) {
			try {
				initWebDriver();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return webDriver;
	}

	// 私有，初始化
	private static void initWebDriver() throws IOException {
		if (null == webDriver) {
			synchronized (lockObj) {
				String filePath = browserDriverPath;
				File file = new File(filePath);
				if (file.exists()) {
					file.setExecutable(true);
					System.getProperties().setProperty("webdriver.chrome.driver", filePath);
					webDriver = new ChromeDriver();
				}
			}
		}
	}

}

package com.edu.crawler.slit.analyze;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.edu.crawler.slit.config.Config;
import com.edu.crawler.slit.fetch.FetchedPage;
import com.edu.crawler.slit.monitor.SlitFinishMonitor;
import com.edu.crawler.slit.node.filter.AbstractNodeFilter;
import com.edu.crawler.slit.resource.pool.FetchedNodePoolManager;
import com.edu.crawler.slit.resource.pool.FetchedPagePoolManager;
import com.edu.crawler.slit.resource.pool.UrlWaitingPoolManager;
import com.edu.crawler.slit.url.SlitUrl;

/**
 * @author qifeifei
 *
 */
public class Analyzer implements Runnable {

	private final Pattern prefixPattern = Pattern.compile("(http://|https://){1}(\\w|\\.)+");
	private Config config;
	private AbstractNodeFilter slitFilter;

	public Analyzer() {
	}

	public Analyzer(Config config) {
		this.config = config;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	// 私有 , 提取结果
	private void analyze() {
		FetchedPage fp = FetchedPagePoolManager.poll();
		if (null == fp || StringUtils.isBlank(fp.getPageHtml())) {
			return;
		}
		try {
			Parser myParser = Parser.createParser(fp.getPageHtml(), config.getEncodeSetting());
			NodeList nodeList = myParser.extractAllNodesThatMatch(config.getNodeFilterClass().newInstance());
			if (null != nodeList && nodeList.size() > 0) {
				FetchedNodePoolManager.add(nodeList);
			}
			if (fp.getCurrentDepth() != null && fp.getCurrentDepth() < config.getMaxDepth()) {
				this.extractALink(fp.getPageHtml(), fp.getWebUrl(), fp.getCurrentDepth());
			}
		} catch (Exception e) {
			System.out.println(Thread.currentThread().getName());
			e.printStackTrace();
		}
	}

	// 私有 , 找出a标签
	private void extractALink(String pageHtml, String fatherUrl, Integer fatherDepth) throws ParserException {
		// 如果用浏览器就不需要
		if (BooleanUtils.isTrue(config.isInBrowser()) || StringUtils.isBlank(pageHtml) || StringUtils.isBlank(fatherUrl)) {
			return;
		}
		if (null == fatherDepth || fatherDepth < 1) {
			fatherDepth = 1;
		}
		Parser myParser = Parser.createParser(pageHtml, config.getEncodeSetting());
		NodeList linkNode = myParser.extractAllNodesThatMatch(new TagNameFilter("A"));
		if (null == linkNode || linkNode.size() < 1) {
			return;
		}
		// 用户链接种类过滤
		if(null == slitFilter){
			try {
				slitFilter = config.getNodeFilterClass().newInstance();
				linkNode = slitFilter.acceptLinkNode(linkNode);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		// regx domain
		String urlPrefix = null;
		Matcher matcher = prefixPattern.matcher(fatherUrl);
		if (BooleanUtils.isTrue(matcher.find())) {
			urlPrefix = matcher.group();
		}
		// save waiting url
		for (int i = 0; i < linkNode.size(); i++) {
			Node node = linkNode.elementAt(i);
			if (node instanceof LinkTag) {
				String linkUrl = ((LinkTag) node).getLink();
				if (StringUtils.isBlank(linkUrl) || (!linkUrl.contains("http") && StringUtils.isBlank(urlPrefix))) {
					continue;
				}
				if (!linkUrl.contains("http")) {
					linkUrl = urlPrefix + linkUrl;
				}
				SlitUrl slitUrl = new SlitUrl();
				slitUrl.setDepth(fatherDepth + 1);
				slitUrl.setWebUrl(linkUrl);
				UrlWaitingPoolManager.add(slitUrl);
			}
		}
	}

	@Override
	public void run() {
		while (!SlitFinishMonitor.isFinish) {
			this.analyze();
		}
	}

}

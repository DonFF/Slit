package com.edu.crawler.slit.browser.test;

import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.util.NodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.crawler.slit.resource.pool.FetchedNodePoolManager;
import com.edu.crawler.slit.store.AbstractFileStore;

public class BrowserFileStore extends AbstractFileStore {

	private static final Logger LOG = LoggerFactory.getLogger(BrowserFileStore.class);

	@Override
	public void processResultNode() {
		NodeList nodeList = FetchedNodePoolManager.extractAll();
		if (null == nodeList || nodeList.size() < 1) {
			return;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.elementAt(i);
			String divClass = ((Div) node).getAttribute("class");
			if (StringUtils.isNotBlank(divClass) && StringUtils.equals(divClass, "blog_title")) {
				// 获取博客标题
				NodeList aNodeList = new NodeList();
				NodeFilter h3 = new TagNameFilter("h3");
				NodeFilter h3a = new HasParentFilter(h3);
				NodeFilter filter = new AndFilter(h3a, new TagNameFilter("A"));
				node.collectInto(aNodeList, filter);
				Node[] nodearr = aNodeList.toNodeArray();
				LOG.debug(nodearr[0].toPlainTextString());
			}
		}
	}

}

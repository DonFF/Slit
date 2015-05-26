package com.edu.crawler.slit.browser.test;

import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.tags.Div;

import com.edu.crawler.slit.node.filter.AbstractNodeFilter;

public class BrowserNodeFilter extends AbstractNodeFilter {
	private static final long serialVersionUID = -5538337750077367281L;

	@Override
	public boolean accept(Node node) {
		if (node instanceof Div) {
			String divClass = ((Div) node).getAttribute("class");
			if (StringUtils.isNotBlank(divClass) && StringUtils.equals(divClass, "blog_title")) {
				return true;
			}
		}
		return false;
	}

}

package com.edu.crawler.slit.test.search;

import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.tags.Div;

import com.edu.crawler.slit.node.filter.AbstractNodeFilter;

public class MyNodeFilter extends AbstractNodeFilter {
	private static final long serialVersionUID = -8693841581669807880L;

	@Override
	public boolean accept(Node node) {
		if (node instanceof Div) {
			String nodeClass = ((Div) node).getAttribute("class");
			if (StringUtils.equals(nodeClass, "content")) {
				return true;
			}
		}
		return false;
	}

}

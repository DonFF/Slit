package com.edu.crawler.slit.node.filter;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

/**
 * @author qifeifei
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractNodeFilter implements NodeFilter, INodeFilter {

	@Override
	public NodeList acceptLinkNode(NodeList nodeList) {
		if (null == nodeList || nodeList.size() <= 0) {
			return nodeList;
		}
		NodeList result = new NodeList();
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.elementAt(i);
			if (!(node instanceof LinkTag)) {
				continue;
			}
			result.add(node);
		}
		return result;
	}

}

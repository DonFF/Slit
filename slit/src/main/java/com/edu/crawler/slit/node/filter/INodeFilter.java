package com.edu.crawler.slit.node.filter;

import org.htmlparser.util.NodeList;

/**
 * @author qifeifei
 *
 */
public interface INodeFilter {

	public NodeList acceptLinkNode(NodeList node);
	
}

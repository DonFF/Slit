package com.edu.crawler.slit.resource.pool;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.htmlparser.util.NodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qifeifei
 *
 */
public class FetchedNodePoolManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(FetchedNodePoolManager.class);
	
	private static ConcurrentLinkedQueue<NodeList> nodeListQ = new ConcurrentLinkedQueue<>();

	public static void add(NodeList nodelist) {
		if (null == nodelist) {
			return;
		}
		nodeListQ.add(nodelist);
		LOG.debug("FetchedNodePoolManager 当前队列中节点数:[{}]",nodeListQ.size());
	}

	public static NodeList poll() {
		return nodeListQ.poll();
	}

	public static NodeList extractAll() {
		NodeList list = new NodeList();
		synchronized (nodeListQ) {
			while (!nodeListQ.isEmpty()) {
				list.add(nodeListQ.poll());
			}
		}
		return list;
	}

	public static Integer size(){
		return nodeListQ.size();
	}
	
}

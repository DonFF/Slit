package com.edu.crawler.slit.test.search;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.htmlparser.Node;
import org.htmlparser.util.NodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.crawler.slit.resource.pool.FetchedNodePoolManager;
import com.edu.crawler.slit.store.AbstractFileStore;

public class MyFileStore extends AbstractFileStore {

	private static final Logger LOG = LoggerFactory.getLogger(MyFileStore.class);

	@Override
	public void processResultNode() {
		NodeList nodeList = FetchedNodePoolManager.extractAll();
		if (null == nodeList || nodeList.size() < 1) {
			return;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.elementAt(i);
			NodeList childList = node.getChildren();
			if (childList.size() > 0) {
				String content = childList.elementAt(0).toPlainTextString();
				LOG.debug(content);
				this.saveBlog(content);
			}
		}
	}

	private void saveBlog(String blogName) {
		File sf = new File("/data/slit/blog.txt");
		if (!sf.exists()) {
			LOG.debug("文件不存在");
			try {
				sf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fw = new FileWriter("/data/slit/blog.txt", true);
			fw.write(blogName);
			fw.write('\n');
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 保存图片
	// private void savePic(String imageUrl, String name) throws Exception {
	// URL url = new URL(imageUrl);
	// URLConnection con = url.openConnection();
	// con.setConnectTimeout(5 * 1000);
	// InputStream is = con.getInputStream();
	// byte[] bs = new byte[1024];
	// int len;
	// File sf = new File("/data/slit/");
	// if (!sf.exists()) {
	// sf.mkdirs();
	// }
	// OutputStream os = new FileOutputStream(sf.getPath() + "/" + name);
	// while ((len = is.read(bs)) != -1) {
	// os.write(bs, 0, len);
	// }
	// os.close();
	// is.close();
	// }

	// if (node instanceof Div) {
	//
	// NodeList aNodeList = new NodeList();
	// NodeFilter h3 = new TagNameFilter("h3");
	// NodeFilter h3a = new HasParentFilter(h3);
	// NodeFilter filter = new AndFilter(h3a, new TagNameFilter("A"));
	// node.collectInto(aNodeList, filter);
	//
	// if (aNodeList.size() > 0) {
	// Node[] aNode = aNodeList.toNodeArray();
	// String blogName = aNode[0].toPlainTextString();
	// System.out.println(blogName);
	// this.saveBlog(blogName);
	// }
	// }
}

package com.edu.crawler.slit.dns.cache;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author qifeifei
 *
 */
public class SlitDnsCacheManager {

	private static Map<String, String> dnsCacheMap = new HashMap<>();

	public static String getIpByDomain(String domainName) {
		if (StringUtils.isBlank(domainName)) {
			return null;
		}
		String Ip = dnsCacheMap.get(domainName);
		if (StringUtils.isNotBlank(Ip)) {
			return Ip;
		}
		try {
			InetAddress addr = InetAddress.getByName(domainName);
			if (null != addr) {
				Ip = addr.getHostAddress();
			}
		} catch (UnknownHostException e) {
			System.out.println(Thread.currentThread().getName());
			e.printStackTrace();
		}
		if (StringUtils.isNotBlank(Ip)) {
			dnsCacheMap.put(domainName, Ip);
		}
		return Ip;
	}

	public static String getIpByDomainWithoutCache(String domainName) {
		String ip = null;
		try {
			InetAddress addr = InetAddress.getByName(domainName);
			if (null != addr) {
				ip = addr.getHostAddress();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}
}

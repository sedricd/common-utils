package com.sedric.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostUtils {

	private static Logger logger = LoggerFactory.getLogger(HostUtils.class);

	public static String getIp() {
		InetAddress localAddress = null;
		try {
			localAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			logger.error("get local host fail :", e);
		}
		return null != localAddress ? localAddress.getHostAddress() : null;
	}

	public static String getHostName() {
		InetAddress localAddress = null;
		try {
			localAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			logger.error("get local host fail :", e);
		}

		return null != localAddress ? localAddress.getHostName() : null;
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").indexOf("Window") != -1;
	}

	public static void main(String[] args) {
		System.out.println(getIp());

		System.out.println(getHostName());

		System.out.println(isWindows());

		System.out.println(System.getProperty("os.name"));
	}
}

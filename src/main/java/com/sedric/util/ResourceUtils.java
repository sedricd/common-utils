package com.sedric.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceUtils {

	private static Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

	/**
	 * 获取指定class文件相同目录下的资源完整路径。<br/>
	 * resourcePath必须为相对路径，即路径不能以"/"或者"\\"开头。
	 * 
	 * @param clazz
	 *            参照资源
	 * @param resourcePath
	 *            资源相对路径
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getRealPathReferClazz(Class clazz, String resourcePath) {
		URL url = clazz.getResource(resourcePath);
		if (null == url) {
			throw new RuntimeErrorException(new Error("resouce is not under this appointed path"));
		}
		return url.getPath();
	}

	/**
	 * 根据指定class文件获取classpath目录下的资源完整路径。<br/>
	 * resourcePath是相对classpath的相对路径。
	 * 
	 * @param clazz
	 * @param resourcePath
	 *            资源相对路径
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getRealPathReferClassPath(Class clazz, String resourcePath) {
		URL url = clazz.getClassLoader().getResource(resourcePath);
		if (null == url) {
			throw new RuntimeErrorException(new Error("resouce is not under this appointed path"));
		}
		return url.getPath();
	}

	/**
	 * 获取指定class所在jar包完整路径
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getRealJarFilePath(Class clazz) {
		// 获取当前jvm classpath

		// Thread.currentThread().getContextClassLoader().getResource("").getPath();

		// DicReader.class.getClassLoader().getResource("").getPath()

		return clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
	}

	/**
	 * 获取当前classPath完整路径
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getRealClassPath(Class clazz) {
		return clazz.getClassLoader().getResource("").getPath();
	}

	/**
	 * 根据clazz路径加载指定资源
	 * 
	 * @param properties
	 * @param clazz
	 * @param resourcePath
	 */
	@SuppressWarnings("rawtypes")
	public static void loadResourceReferClazz(Properties properties, Class clazz, String resourcePath) {
		InputStream in = null;
		in = clazz.getResourceAsStream(resourcePath);
		if (null == in) {
			throw new RuntimeErrorException(new Error("resouce is not under this appointed path"));
		}
		try {
			properties.load(in);
		}
		catch (IOException e) {
			logger.error("load resource fail !", e);
		}
	}

	/**
	 * 根据classPath路径加载指定资源
	 * 
	 * @param properties
	 * @param clazz
	 * @param resourcePath
	 */
	@SuppressWarnings("rawtypes")
	public static void loadResourceReferClassPath(Properties properties, Class clazz, String resourcePath) {
		InputStream in = null;
		in = clazz.getClassLoader().getResourceAsStream(resourcePath);
		if (null == in) {
			throw new RuntimeErrorException(new Error("resouce is not under this appointed path"));
		}
		try {
			properties.load(in);
		}
		catch (IOException e) {
			logger.error("load resource fail !", e);
		}
	}

}

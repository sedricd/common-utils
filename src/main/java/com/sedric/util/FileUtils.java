package com.sedric.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FileUtils {

	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 读取文件内容为字符串
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readFile2String(String fileName) {

		File file = new File(fileName);

		if (!file.exists()) {
			return null;
		}

		FileInputStream fi = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			fi = new FileInputStream(file);
			isr = new InputStreamReader(fi, "UTF-8");
			br = new BufferedReader(isr);

			String result;
			while ((result = br.readLine()) != null) {
				sb.append(result);
			}
		}
		catch (FileNotFoundException e) {
			logger.error(e.toString());
		}
		catch (UnsupportedEncodingException e) {
			logger.error(e.toString());
		}
		catch (IOException e) {
			logger.error(e.toString());
		}
		finally {
			try {
				br.close();
				isr.close();
				fi.close();
			}
			catch (IOException e) {
				logger.error(e.toString());
			}
		}

		return sb.toString();
	}

	public static File writeStringList2File(List<String> strList, String fileName) {

		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		File file = new File(fileName);

		FileOutputStream fo = null;
		OutputStreamWriter ow = null;
		BufferedWriter bw = null;
		try {
			fo = new FileOutputStream(file);
			ow = new OutputStreamWriter(fo, "UTF-8");
			bw = new BufferedWriter(ow);
			for(String str : strList) {
				bw.write(str + "\r\n");
			}

		}
		catch (FileNotFoundException e) {
			logger.error(e.toString());
		}
		catch (UnsupportedEncodingException e) {
			logger.error(e.toString());
		}
		catch (IOException e) {
			logger.error(e.toString());
		}
		finally {
			try {
				bw.close();
				ow.close();
				fo.close();
			}
			catch (IOException e) {
				logger.error(e.toString());
			}

		}
		return file;
	}

	public static File writeString2File(String str, String fileName) {

		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		File file = new File(fileName);

		FileOutputStream fo = null;
		OutputStreamWriter ow = null;
		BufferedWriter bw = null;
		try {
			fo = new FileOutputStream(file);
			ow = new OutputStreamWriter(fo, "UTF-8");
			bw = new BufferedWriter(ow);
			bw.write(str + "\r\n");
		}
		catch (FileNotFoundException e) {
			logger.error(e.toString());
		}
		catch (UnsupportedEncodingException e) {
			logger.error(e.toString());
		}
		catch (IOException e) {
			logger.error(e.toString());
		}
		finally {
			try {
				bw.close();
				ow.close();
				fo.close();
			}
			catch (IOException e) {
				logger.error(e.toString());
			}

		}
		return file;
	}

	public static List<String> readFile2StringList(String fileName) {

		List<String> list = new ArrayList<String>();

		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		File file = new File(fileName);

		FileInputStream fi = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fi = new FileInputStream(file);
			isr = new InputStreamReader(fi, "UTF-8");
			br = new BufferedReader(isr);

			String result;
			while ((result = br.readLine()) != null) {
				list.add(result);
			}
		}
		catch (FileNotFoundException e) {
			logger.error(e.toString());
		}
		catch (UnsupportedEncodingException e) {
			logger.error(e.toString());
		}
		catch (IOException e) {
			logger.error(e.toString());
		}
		finally {
			try {
				br.close();
				isr.close();
				fi.close();
			}
			catch (IOException e) {
				logger.error(e.toString());
			}
		}
		return list;
	}

	public static void deleteFile(String fileName) throws IOException {
		File file = new File(fileName);
		deleteFile(file);
	}

	public static void deleteFile(File file) throws IOException {

		if (file.exists() && file.isDirectory()) {
			File[] insideFiles = file.listFiles();
			for(File inside : insideFiles) {
				deleteFile(inside);
			}
			org.apache.commons.io.FileUtils.forceDelete(file);
		} else {
			org.apache.commons.io.FileUtils.forceDelete(file);
		}
	}
}
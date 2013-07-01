package com.sedric.util;

import java.math.BigDecimal;

import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtils {

	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

	/**
	 * 替换Oracle like搜索的特殊字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceSpecialLikeCharacter(String str) {
		if (null == str) {
			return null;
		}
		str = str.replaceAll("%", "\\\\%");
		str = str.replaceAll("_", "\\\\_");

		return str;
	}

	public static String formatFentoYuan(Long fen) {
		Long abFen = Math.abs(fen);
		String str = abFen.toString();
		if (abFen < 10) {
			str = "00" + str;
		} else if (abFen < 100) {
			str = "0" + str;
		}
		return (fen < 0 ? "-" : "") + str.substring(0, str.length() - 2) + "." + str.substring(str.length() - 2);
	}

	/**
	 * 判断字符串数组中是否包含指定的字符串
	 * 
	 * @param strArr
	 * @param str
	 * @return
	 */
	public static boolean contains(String[] strArr, String str) {
		if (null == strArr) {
			return false;
		}
		for(String temp : strArr) {
			if (temp.equals(str)) {
				return true;
			}
		}
		return false;
	}

	public static boolean strIsEmpty(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		}
		return false;
	}

	public static String deleteSpace(String str) {
		return str.replaceAll("\\s", org.apache.commons.lang.StringUtils.EMPTY);
	}

	/**
	 * 重量舍入。<br/>
	 * 小于等于1kg的为1kg,1.1 ~ 1.5kg为1.5kg,1.6 ~ 2kg为2kg,2.1 ~ 2.5kg 为2.5kg。 以此类推。
	 * 
	 * @param num
	 */
	public static String rangeWeight(String weight) {

		if (org.apache.commons.lang.StringUtils.isBlank(weight)) {
			return weight;
		}

		String rangeWeight = org.apache.commons.lang.StringUtils.EMPTY;

		BigDecimal decimalWeight = null;
		try {
			decimalWeight = new BigDecimal(weight);
		}
		catch (Exception e) {
			logger.error("处理重量失败，非法的重量参数：" + weight, e);
			return weight;
		}

		double doubleWeight = decimalWeight.doubleValue();

		double startWeight = 1.0d;

		BigDecimal step = new BigDecimal(0.5);

		if (decimalWeight.doubleValue() < startWeight) {
			rangeWeight = String.valueOf(startWeight);
		} else {
			if (doubleWeight == Math.ceil(doubleWeight)) {
				rangeWeight = weight;
			} else if (Math.ceil(doubleWeight) < decimalWeight.add(step).doubleValue()) {
				rangeWeight = String.valueOf(Math.ceil(decimalWeight.doubleValue()));
			} else {
				rangeWeight = new BigDecimal(Math.floor(doubleWeight)).add(step).toString();
			}
		}

		return rangeWeight;
	}

}

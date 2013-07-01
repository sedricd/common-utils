package com.sedric.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class ReflectUtils {

	private static Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

	private static String COLON = ":";
	private static String SEMICOLON = ";";
	private static String LEFT_BRACKET = "[";
	private static String RIGHT_BRACKET = "]";

	/**
	 * 反射执行方法
	 * 
	 * @param obj
	 * @param method
	 * @return
	 */
	public static Object invoke(Object obj, String method) {
		try {
			Method m = obj.getClass().getMethod(method);

			m.setAccessible(true);

			return m.invoke(obj);
		}
		catch (Exception e) {
			throwBuildExcpetion(e);
			return null;
		}
	}

	/**
	 * 反射执行方法
	 * 
	 * @param obj
	 * @param method
	 * @param argType
	 * @param arg
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object invoke(Object obj, String method, Class argType, Object arg) {
		try {
			Method m = obj.getClass().getMethod(method, new Class[] { argType });

			m.setAccessible(true);
			return m.invoke(obj, new Object[] { arg });
		}
		catch (Exception e) {
			throwBuildExcpetion(e);
			return null;
		}
	}

	/**
	 * 获取实例对象的成员属性值
	 * 
	 * @param obj
	 * @param filedName
	 * @return
	 */
	public static Object getFiledValue(Object obj, String filedName) {
		try {
			Field f = obj.getClass().getDeclaredField(filedName);
			f.setAccessible(true);
			return f.get(obj);

		}
		catch (Exception e) {
			throwBuildExcpetion(e);
			return null;
		}
	}

	/**
	 * 将实例对象复制到制定的对象类型
	 * 
	 * @param obj
	 *            源对象
	 * @param clazz
	 *            制定的对象类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object cloneObjectByClass(Object obj, Class clazz) {

		try {
			Object newObj = clazz.newInstance();
			copyProperties(obj, newObj);
			return newObj;
		}
		catch (Exception e) {
			throwBuildExcpetion(e);
			return null;
		}
	}

	/**
	 * 实例对象属性复制
	 * 
	 * @param obj
	 *            源对象
	 * @param dest
	 *            目标对象
	 */
	@SuppressWarnings("rawtypes")
	public static void copyProperties(Object obj, Object dest) {
		Class destClass = dest.getClass();
		Class soureClass = obj.getClass();
		copyProperties(obj, dest, soureClass, destClass);
	}

	/**
	 * 
	 * @param obj
	 * @param dest
	 * @param soureClass
	 * @param destClass
	 */
	@SuppressWarnings("rawtypes")
	private static void copyProperties(Object obj, Object dest, Class soureClass, Class destClass) {
		Field[] destFields = destClass.getDeclaredFields();
		Method[] sourceMethods = soureClass.getMethods();
		for(Method m : sourceMethods) {
			try {
				if (m.getName().startsWith("set")) {
					for(Field destField : destFields) {
						String filedName = destField.getName();
						// 如果成员变量名与变量类型都一直才复制值
						if (m.getName().replaceFirst("set", StringUtils.EMPTY).equalsIgnoreCase(filedName)
								&& m.getParameterTypes()[0].equals(destField.getType())) {
							Method getMethod = fieldGetMethod(obj, filedName);
							m.setAccessible(true);
							invoke(dest, m.getName(), m.getParameterTypes()[0], getMethod.invoke(obj));
						}
					}
				}
			}
			catch (Exception e) {
				logger.error(e.toString());
				continue;
			}
		}
	}

	/**
	 * bean转字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String beanToString(Object obj) {
		if (null != obj) {
			StringBuilder sb = new StringBuilder();
			try {
				Field[] fields = obj.getClass().getDeclaredFields();
				for(Field field : fields) {
					sb.append(field.getName()).append(COLON);
					if (BeanUtils.isSimpleProperty(field.getType())) {
						sb.append(getFiledValue(obj, field.getName()));
					} else {
						sb.append(LEFT_BRACKET);
						sb.append(beanToString(getFiledValue(obj, field.getName())));
						sb.append(RIGHT_BRACKET);
					}
					sb.append(SEMICOLON);
				}
			}
			catch (Exception e) {
				logger.error("convert bean to string fail :", e);
			}

			return sb.toString();
		} else {
			return null;
		}
	}

	/**
	 * 获取实例对象指定成员变量的get方法
	 * 
	 * @param obj
	 * @param field
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method fieldGetMethod(Object obj, String field) {
		Class clazz = obj.getClass();

		String methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);

		Method method = null;
		try {
			method = clazz.getMethod(methodName);
		}
		catch (Exception e) {
			throwBuildExcpetion(e);
		}
		return method;
	}

	public static Object newInstance(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return ClassUtils.getClass(obj.getClass().getName()).newInstance();
		}
		catch (InstantiationException e) {
			throwBuildExcpetion(e);
		}
		catch (IllegalAccessException e) {
			throwBuildExcpetion(e);
		}
		catch (ClassNotFoundException e) {
			throwBuildExcpetion(e);
		}
		return null;
	}

	private static void throwBuildExcpetion(Exception e) {
		throw new RuntimeException(e);
	}

}
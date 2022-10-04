package com.PingShoping.utils;

/**
 * 字符串工具类 
 * company 源辰信息
 * @author 38929
 * @date 2022年3月9日
 * @version 1.0
 * Email 1198865589@qq.com
 */
public class StringUtil {

	/**
	 * 检查字符串集合 是否是空对象或者空字符串
	 * @param strs
	 * @return
	 */
	public static  boolean checkNull(String ... strs) {
		//空字符串数组
		if( null == strs || strs.length <= 0) {
			return true;
		}
		//数组中不能含有空的字符串
		for(String str : strs) {
			if( null == str || "".equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	// 检查不定参数对象数组 是否有 null 或  ""
	public static  boolean checkNull(Object ...objs) {
		if( objs == null || objs.length <= 0) {
			return true;
		}
		for (Object obj : objs) {
			if( obj == null || "".contentEquals( String.valueOf( obj ))) {
				return true;
			}
		}
		return false;
	}
}

package com.lailelaodi.util;

public class TextUtils {
/**
 * @description 判断一个字符串是否为空
 * * @param [s]
 * @return boolean
 * @author Euraxluo
 * @date 18-12-21
 */
	public static boolean isEmpty(CharSequence s){
		return s==null || s.length() == 0;
	}
}

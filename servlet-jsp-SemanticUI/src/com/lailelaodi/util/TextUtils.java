package com.lailelaodi.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

	/**
 * @description 和字符串有关系的操作
 * * @param [s]
 * @return boolean
 * @author Euraxluo
 * @date 18-12-21
 */

	public static final String EMAIL = "email";

	public static final String USERNAME = "username";

	public static final String PHONE = "phone";

	public static final int SECCESS = 1;

	public static final int FAIL = 0 ;

	public static boolean isEmpty(CharSequence s){
		return s==null || s.length() == 0;
	}
	public void getJson(HttpServletRequest request, HttpServletResponse response, Object object){
		response.setContentType("text/html;charset=UTF-8");
		//禁用缓存，确保网页信息是最新数据
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", -10);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String jsonStr=JSON.toJSONString(object);
			out.print(jsonStr);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	}

	public String getJson(Object object){
		String jsonStr=JSON.toJSONString(object);
		return jsonStr;
	}

//	public Map tomap(Object key,Object value){
//		Map map = new HashMap();
//		map.put(key,value);
//		if(map.isEmpty()){
//			map.put(0,"参数异常");
//			return map;
//		}
//		return map;
//	}
	public static boolean isNotBlank(CharSequence cs) {
		return !isBlank(cs);
	}

	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs != null && (strLen = cs.length()) != 0) {
			for(int i = 0; i < strLen; ++i) {
				if (!Character.isWhitespace(cs.charAt(i))) {
					return false;
				}
			}

			return true;
		} else {
			return true;
		}
	}

	public static Timestamp now(){
		long nowtime = new Date().getTime();
		return new Timestamp(nowtime);
	}
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7])| (17[0,1,3,5-8]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}

package com.wss.netty.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
/**
 * Wss_netty
 * com.wss.netty.utils
 * PropertiesUtil
 * 2019/10/15 15:11
 * author:Euraxluo
 * TODO
 */

public class PropertiesUtil {

    private static Logger logger = Logger.getLogger(PropertiesUtil.class);

    //静态代码块>普通代码块>构造代码块

    private static Properties props;//构造代码块

    static {//静态代码块
        String fileName = "application.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常",e);
        }
    }

    public static String getProperty(String key){//普通 代码块
        String value = props.getProperty(key.trim());//避免两边的空格
        if(StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key,String defaultValue){

        String value = props.getProperty(key.trim());//避免两边的空格
        if(StringUtils.isBlank(value)){
            value = defaultValue;
        }
        return value.trim();
    }



}

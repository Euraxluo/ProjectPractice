package cn.euraxluo.FSWFrame.core;
import java.util.ResourceBundle;

/**
 * fast-start-web-framework
 * cn.euraxluo.FSWFrame.core
 * ConfScanner
 * 2019/11/24 11:18
 * author:Euraxluo
 * TODO
 */
public class ConfScanner {
    public static ResourceBundle config = ResourceBundle.getBundle("config");
    public static String getConf(String key){
        if (config.containsKey(key)){
            return config.getString(key);
        }else {
            return key;
        }

    }
}

package cn.euraxluo.FSWFrame.log;

import cn.euraxluo.FSWFrame.core.ConfScanner;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.log
 * LogFactory
 * 2019/11/24 10:26
 * author:Euraxluo
 * TODO
 */
public class LogFactory {
    public static final String LOG_NAME = ConfScanner.getConf("log.name").equals("log.name")?"FSWF_Global":ConfScanner.getConf("log.name");
    public static final String LOG_FOLDER = ConfScanner.getConf("log.folder").equals("log.folder")?System.getProperty("java.io.tmpdir"):ConfScanner.getConf("log.folder");
    private static String log_file_path;
    private static Logger global_log;
    static {
        log_file_path = LOG_FOLDER+ File.separator+LOG_NAME+"_"+LogUtil.getCurrentDateStr(LogUtil.DATE_PATTERN_NOMARK)+".log";
        global_log = initGlobalLog();
    }


    /**
     * init Global log
     * @return
     */
    private static Logger initGlobalLog() {
        Logger logger = Logger.getLogger(LOG_NAME);
        logger.setLevel(Level.ALL);
        LogUtil.addConsoleHandler(logger,Level.INFO);
        LogUtil.addFileHandler(logger,Level.INFO,log_file_path);
        logger.setUseParentHandlers(false);
        return logger;
    }

    /**
     * return Global logger
     * @return
     */
    public static Logger getLogger(){
        return global_log;
    }

//    public void info(String msg,final Object... args) {
//        super.info(fs(msg,args));
//    }
//    public void warning(String msg,final Object... args) {
//        super.warning(fs(msg,args));
//    }


    /**
     * args: idv namev
     * key:"logString:{id},{name}"
     * return logString:[idv],[namev]
     * @param key
     * @param args
     * @return
     */
    public static String fs(String key, final Object... args){
        String regex = "\\{([^}]*)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(key);
        for (int i=0;matcher.find();i++)
        {
            key= key.replaceFirst(regex," <"+args[i]+"> ");
        }
        return key;
    }

}

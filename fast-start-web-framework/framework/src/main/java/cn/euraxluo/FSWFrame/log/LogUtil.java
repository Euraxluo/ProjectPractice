package cn.euraxluo.FSWFrame.log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.log
 * LogUtil
 * 2019/11/24 10:09
 * author:Euraxluo
 * TODO
 */
public class LogUtil {
    public static final String DATE_PATTERN_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN_NOMARK = "yyyyMMddHHmmss";

    /**
     * set log level
     * @param log
     * @param level
     */
    public static void setLogLevel(Logger log, Level level){
        log.setLevel(level);
    }

    /**
     * add console handler for log
     * @param log
     * @param level
     */
    public static void addConsoleHandler(Logger log,Level level){
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(level);
        log.addHandler(consoleHandler);
    }

    /**
     * add file handler for log
     * @param log
     * @param level
     * @param filePath
     */
    public static void addFileHandler(Logger log,Level level,String filePath){
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler(filePath);
            fileHandler.setLevel(level);
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return "[" + getCurrentDateStr(DATE_PATTERN_FULL) + "]-["
                            + record.getLevel().getName() + "]-[" +record.getSourceClassName()
                            + "]-[" + record.getSourceMethodName() + "]-[" + record.getMessage() + "]\n";
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileHandler != null){
            log.addHandler(fileHandler);
        }
    }

    /**
     * return dateString with format : pattern
     * @param pattern
     * @return
     */
    public static String getCurrentDateStr(String pattern){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}

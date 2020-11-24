package cn.euraxluo.FSWFrame.exception;

import cn.euraxluo.FSWFrame.log.LogFactory;

import java.util.logging.Logger;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.exception
 * FileNotFoundException
 * 2019/11/23 22:21
 * author:Euraxluo
 * TODO
 */
public class FileNotFoundException extends Exception {
    private static Logger logger = LogFactory.getLogger();
    public FileNotFoundException(String message) {
        super("404 Not Found "+message);
        logger.warning(super.getMessage());
    }
}
